import { Injectable } from '@angular/core';
import { MatrixesService } from './matrixes.service';
import { FftService } from './fft.service';

@Injectable()
export class MfccService {

  private static wrongLimitsError: string = "Low limit can't be higher than high limit!";
	private static MELFILTER_BANDS: number = 40;
	private static POWER_SPECTRUM_FLOOR: number = 0.0001;
	private static MFCC_NUM: number = 13;
	private static PRE_EMPH: number = 0.95;

  constructor(private matrixesService: MatrixesService, private fftService: FftService) {}
      public extractMfcc(samplesVector: number[], fs: number): number[][]{

        let frameLen: number = this.setFrameLen(fs);
    		let frameShift: number = this.setFrameShift(fs);
    		let windowVector: number[] = this.hamming(frameLen);
    		let melfbCoeffs: number[][] = this.melFilterBank(MfccService.MELFILTER_BANDS, frameLen, fs);
        let fftReal: number[] = this.matrixesService.initVector(frameLen);
        let fftFinal: number[] = this.matrixesService.initVector(frameLen / 2 + 1);
    		let framesNum: number = Math.floor((samplesVector.length - frameLen) / frameShift) + 1;
    		let frame: number[] = this.matrixesService.initVector(frameLen);
        let mfccCoeffs: number[][] = this.matrixesService.initMatrix(framesNum, MfccService.MFCC_NUM);

        for (let i: number = 0; i < framesNum; i++) {
          for (let j: number = 0; j < frameLen; j++) {
    				frame[j] = samplesVector[i * frameShift + j];
    			}

    			frame = this.matrixesService.vectorMultiplyByVector(frame, windowVector);
    			frame = this.preemphasis(frame, MfccService.PRE_EMPH);

          for (let k: number = 0; k< frameLen;k++){
            fftReal[k] = frame[k];
          }

          let fftImaginary: number[] = this.matrixesService.initVector(frameLen);
          this.fftService.transform(fftReal, fftImaginary);

          for (let k: number = 0; k < (frameLen / 2 + 1); k++) {
            fftFinal[k] = Math.pow(fftReal[k], 2) + Math.pow(fftImaginary[k], 2);

    				if (fftFinal[k] < MfccService.POWER_SPECTRUM_FLOOR) {
    					fftFinal[k] = MfccService.POWER_SPECTRUM_FLOOR;
    				}
    			}

    			let dotProd: number[] = this.matrixesService.matrixMultiplyByVector(melfbCoeffs, fftFinal);

        	for (let j: number = 0; j < dotProd.length; j++) {
    				if (dotProd[j] > 0)
    					dotProd[j] = Math.log(dotProd[j]);
    			}
        	let d1Matrix: number[][] = this.dctMatrix(MfccService.MELFILTER_BANDS, MfccService.MFCC_NUM);
          dotProd = this.matrixesService.matrixMultiplyByVector(d1Matrix, dotProd);
          mfccCoeffs[i] = dotProd;
    		}
    		return mfccCoeffs;
    	}

      private dctMatrix(size: number, mfccNum: number): number[][] {

      let d1Matrix: number[][] = this.matrixesService.initMatrix(size,size);
      let xMeshgridMatrix: number[][] = this.matrixesService.initMatrix(size,size);
      for (let i: number = 0; i < size; i++) {
  			for (let j: number = 0; j < size; j++) {
  				xMeshgridMatrix[j][i] = i;
  			}
  		}
  		let yMeshgridMatrix: number[][] = this.matrixesService.initMatrix(size,size);
      for (let i: number = 0; i < size; i++) {
  			for (let j: number = 0; j < size; j++) {
  				yMeshgridMatrix[i][j] = i;
  			}
  		}
      for (let i: number = 0; i < size; i++) {
  			for (let j: number = 0; j < size; j++) {
  				xMeshgridMatrix[i][j] = (xMeshgridMatrix[i][j] * 2 + 1) * Math.PI / (2 * size);
  			}
  		}

      d1Matrix = this.matrixesService.multiplyMatrixesElementByElement(xMeshgridMatrix, yMeshgridMatrix);

      for (let i: number = 0; i < size; i++) {
  			for (let j: number = 0; j < size; j++) {
  				d1Matrix[i][j] = Math.sqrt(2 / size) * Math.cos(d1Matrix[i][j]);
  			}
  		}
      for (let i: number = 0; i < size; i++) {
        if(d1Matrix[0][i]>=0)
  			d1Matrix[0][i] /= Math.sqrt(2);
  		}
  		let resultMatrix: number[][] = this.matrixesService.initMatrix(mfccNum,size);
  		for (let i: number = 1; i < mfccNum + 1; i++) {
  			for (let j: number = 0; j < size; j++) {
  				resultMatrix[i - 1][j] = d1Matrix[i][j];
  			}
  		}
  		return resultMatrix;
  	}

  	private melFilterBank(numOfFilterBanks: number, fftLength: number, sampleRate: number): number[][] {

      let f0: number = 700 / sampleRate;
  		let fn2: number = Math.floor(fftLength / 2);
  		let lr: number = Math.log(1 + 0.5 / f0) / (numOfFilterBanks + 1);
  		let cfVector: number[] = this.arrange(1, numOfFilterBanks + 1);

  		for (let i: number = 0; i < cfVector.length; i++) {
  			cfVector[i] = sampleRate * f0 * (Math.exp(cfVector[i] * lr) - 1);
  		}

  		let bl: number[] = [ 0, 1, numOfFilterBanks, numOfFilterBanks + 1 ];

  		for (let i: number = 0; i < bl.length; i++) {
  			bl[i] = fftLength * f0 * (Math.exp(bl[i] * lr) - 1);
  		}

  		let b1: number = Math.floor(bl[0]) + 1; // 1
  		let b2: number = Math.ceil(bl[1]); // 1
  		let b3: number = Math.floor(bl[2]); // fftLen
  		let b4: number = Math.min(fn2, Math.ceil(bl[3])) - 1;
  		let pf: number[] = this.arrange(b1, b4 + 1);

  		for (let i: number = 0; i < pf.length; i++) {
  			pf[i] = Math.log(1 + pf[i] / f0 / fftLength) / lr;
  		}

  		let fp: number[] = this.matrixesService.initVector(pf.length);
  		let pm: number[] = this.matrixesService.initVector(pf.length);

  		for (let i: number = 0; i < pf.length; i++) {
  			fp[i] = Math.floor(pf[i]);
  			pm[i] = pf[i] - fp[i];
  		}
  		let matrix: number[][] = this.matrixesService.initMatrix(numOfFilterBanks,1 + fn2);
      let r: number = 0;

  		for (let i: number = b2 - 1; i < b4; i++) {
  			r = Math.floor(fp[i] - 1);
  			matrix[r][i + 1] += 2 * (1 - pm[i]);
  		}

  		for (let i: number = 0; i < b3; i++) {
  			r = Math.floor(fp[i]);
  			matrix[r][i + 1] += 2 * pm[i];
  		}

  		let rowEnergy: number = 0;

  		for (let i: number = 0; i < matrix.length; i++) {
  			let tempRow: number[] = matrix[i];
  			rowEnergy = this.energyOfVector(tempRow);
  			if (rowEnergy < 0.0001) {
  				tempRow[i] = i;
  			} else {
  				while (rowEnergy > 1.01) {
  					tempRow = this.matrixesService.vectorMultiplyByScalar(tempRow, 0.99);
  					rowEnergy = this.energyOfVector(tempRow);
  				}
  				while (rowEnergy < 0.99) {
  					tempRow = this.matrixesService.vectorMultiplyByScalar(tempRow, 1.01);
  					rowEnergy = this.energyOfVector(tempRow);
  				}
  			}
  			matrix[i] = tempRow;
  		}
  		return matrix;
  	}

      private arrange(lowLimit: number, highLimit: number): number[] {
        this.arrayLimitCheck(lowLimit, highLimit);
        let result: number[] = this.matrixesService.initVector(highLimit - lowLimit);
        for (let i: number = 0; i < highLimit - lowLimit; i++) {
          result[i] = lowLimit + i;
        }
        return result;
      }

      private hamming(frameLen: number): number[] {
      	let windowTemp: number[] = this.matrixesService.initVector(frameLen);
      	for (let i: number = 0; i < frameLen; i++) {
      		windowTemp[i] = 0.54 - 0.46 * Math.cos(2 * Math.PI / frameLen * (i + 0.5));
      	}
      	return windowTemp;
      }

      private setFrameLen(sampleRate: number): number {
      	return Math.ceil(sampleRate * 0.025);
      }

      private setFrameShift(sampleRate: number): number {
      	return Math.ceil(sampleRate * 0.0125);
      }

      private arrayLimitCheck(low: number,high:number):void {
      		if (low > high) {
      			//todo
      		}
      }

    	private energyOfVector(vector: number[]): number {
    		let energy: number = 0;
    		for (let i: number = 0; i < vector.length; i++) {
    			energy = energy + Math.pow(vector[i], 2);
    		}
    		return energy;
    	}

    	private preemphasis(vector: number[], preEmph: number): number[]  {
    		let tempVector: number[] = this.matrixesService.initVector(vector.length);
    		tempVector[0] = vector[0];
    		for (let i: number = 1; i < vector.length; i++) {
    			tempVector[i] = vector[i] - preEmph * vector[i - 1];
    		}
    		return tempVector;
    	}


}
