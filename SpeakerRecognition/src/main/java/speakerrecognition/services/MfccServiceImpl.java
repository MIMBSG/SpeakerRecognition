package speakerrecognition.services;

import org.jtransforms.fft.DoubleFFT_1D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.MfccServiceException;
import speakerrecognition.pojos.MfccParameters;
import speakerrecognition.services.interfaces.MfccService;

@Service
public class MfccServiceImpl implements MfccService {

	@Autowired
	private MatrixesService matrixService;

	private static final String wrongLimitsError = "Low limit can't be higher than high limit!";
	private static final int MELFILTER_BANDS = 40;
	private static final double POWER_SPECTRUM_FLOOR = 0.0001;
	private static final int MFCC_NUM = 13;
	private static final double PRE_EMPH = 0.95;

	@Override
	public MfccParameters extractMfcc(double[] samplesVector, int fs)
			throws MfccServiceException, MatrixesServiceException {

		int frameLen = setFrameLen(fs);
		int frameShift = setFrameShift(fs);
		double[] windowVector = hamming(frameLen);
		double[][] melfbCoeffs = melFilterBank(MELFILTER_BANDS, frameLen, fs);
		double[][] mfccCoeffs;

		DoubleFFT_1D fftDo = new DoubleFFT_1D(frameLen);
		double[] fft1 = new double[frameLen * 2];
		double[] fftFinal = new double[frameLen / 2 + 1];
		int framesNum = (int) ((double) (samplesVector.length - frameLen) / (double) (frameShift)) + 1;
		mfccCoeffs = new double[framesNum][MFCC_NUM];
		double[] frame = new double[frameLen];

		for (int i = 0; i < framesNum; i++) {
			for (int j = 0; j < frameLen; j++) {
				frame[j] = (double) samplesVector[i * frameShift + j];
			}

			frame = matrixService.vectorMultiplyByVector(frame, windowVector);
			frame = preemphasis(frame, PRE_EMPH);
			System.arraycopy(frame, 0, fft1, 0, frameLen);
			fftDo.realForwardFull(fft1);

			for (int k = 0; k < (frameLen / 2 + 1); k++) {
				fftFinal[k] = Math.pow(Math.sqrt(Math.pow(fft1[k * 2], 2) + Math.pow(fft1[k * 2 + 1], 2)), 2);

				if (fftFinal[k] < POWER_SPECTRUM_FLOOR) {
					fftFinal[k] = POWER_SPECTRUM_FLOOR;
				}
			}

			double[] dotProd = matrixService.matrixMultiplyByVector(melfbCoeffs, fftFinal);
			for (int j = 0; j < dotProd.length; j++) {
				dotProd[j] = Math.log(dotProd[j]);
			}
			double[][] d1Matrix = dctMatrix(MELFILTER_BANDS, MFCC_NUM);
			dotProd = matrixService.matrixMultiplyByVector(d1Matrix, dotProd);
			mfccCoeffs[i] = dotProd;
		}

		MfccParameters result = new MfccParameters(mfccCoeffs);
		return result;
	}

	@Override
	public double[] arrange(int lowLimit, int highLimit) throws MfccServiceException {
		arrayLimitCheck(lowLimit, highLimit);
		double[] result = new double[highLimit - lowLimit];
		for (int i = 0; i < result.length; i++) {
			result[i] = lowLimit + i;
		}

		return result;
	}

	@Override
	public double energyOfVector(double[] vector) {
		double energy = 0;
		for (int i = 0; i < vector.length; i++) {
			energy = energy + Math.pow(vector[i], 2);
		}
		return energy;
	}

	@Override
	public double[] preemphasis(double[] vector, double preEmph) {
		double[] tempVector = new double[vector.length];
		tempVector[0] = vector[0];
		for (int i = 1; i < vector.length; i++) {
			tempVector[i] = vector[i] - preEmph * vector[i - 1];
		}
		return tempVector;
	}

	@Override
	public double[][] dctMatrix(int size, int mfccNum) throws MatrixesServiceException {

		double[][] d1Matrix = new double[size][size];
		double[][] xMeshgridMatrix = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				xMeshgridMatrix[j][i] = i;
			}
		}
		double[][] yMeshgridMatrix = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				yMeshgridMatrix[i][j] = i;
			}
		}

		d1Matrix = matrixService.multiplyMatrixesElementByElement(xMeshgridMatrix, yMeshgridMatrix);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				d1Matrix[i][j] = Math.sqrt(2 / (double) size) * Math.cos(d1Matrix[i][j]);
			}
		}
		for (int i = 0; i < size; i++) {
			d1Matrix[0][i] /= Math.sqrt(2);
		}
		double[][] resultMatrix = new double[mfccNum][size];
		for (int i = 1; i < mfccNum + 1; i++) {
			for (int j = 0; j < size; j++) {
				resultMatrix[i - 1][j] = d1Matrix[i][j];
			}
		}
		return d1Matrix;
	}

	@Override
	public double[][] melFilterBank(int numOfFilterBanks, int fftLength, int sampleRate) throws MfccServiceException {
		double f0 = 700 / (double) sampleRate;
		int fn2 = (int) Math.floor((double) fftLength / 2);
		double lr = Math.log((double) 1 + 0.5 / f0) / (numOfFilterBanks + 1);
		double[] cfVector = arrange(1, numOfFilterBanks + 1);

		for (int i = 0; i < cfVector.length; i++) {
			cfVector[i] = sampleRate * f0 * (Math.exp(cfVector[i] * lr) - 1);
		}

		double[] bl = { 0, 1, numOfFilterBanks, numOfFilterBanks + 1 };

		for (int i = 0; i < bl.length; i++) {
			bl[i] = fftLength * f0 * (Math.exp(bl[i] * lr) - 1);
		}

		int b1 = (int) Math.floor(bl[0]) + 1; // 1
		int b2 = (int) Math.ceil(bl[1]); // 1
		int b3 = (int) Math.floor(bl[2]); // fftLen
		int b4 = Math.min(fn2, (int) Math.ceil(bl[3])) - 1;
		double[] pf = arrange(b1, b4 + 1);

		for (int i = 0; i < pf.length; i++) {
			pf[i] = Math.log(1 + pf[i] / f0 / (double) fftLength) / lr;
		}

		double[] fp = new double[pf.length];
		double[] pm = new double[pf.length];

		for (int i = 0; i < fp.length; i++) {
			fp[i] = Math.floor(pf[i]);
			pm[i] = pf[i] - fp[i];
		}
		double[][] matrix = new double[numOfFilterBanks][1 + fn2];
		int r = 0;

		for (int i = b2 - 1; i < b4; i++) {
			r = (int) fp[i] - 1;
			matrix[r][i + 1] += 2 * (1 - pm[i]);
		}

		for (int i = 0; i < b3; i++) {
			r = (int) fp[i];
			matrix[r][i + 1] += 2 * pm[i];
		}

		double[] tempRow = null;
		double rowEnergy = 0;

		for (int i = 0; i < matrix.length; i++) {
			tempRow = matrix[i];
			rowEnergy = energyOfVector(tempRow);
			if (rowEnergy < 0.0001) {
				tempRow[i] = i;
			} else {
				while (rowEnergy > 1.01) {
					tempRow = matrixService.vectorMultiplyByScalar(tempRow, 0.99);
					rowEnergy = energyOfVector(tempRow);
				}
				while (rowEnergy < 0.99) {
					tempRow = matrixService.vectorMultiplyByScalar(tempRow, 1.01);
					rowEnergy = energyOfVector(tempRow);
				}
			}
			matrix[i] = tempRow;
		}
		return matrix;
	}

	private double[] hamming(int frameLen) {
		double[] windowTemp = new double[frameLen];
		for (int i = 0; i < windowTemp.length; i++) {
			windowTemp[i] = 0.54 - 0.46 * Math.cos(2 * Math.PI / (double) frameLen * ((double) i + 0.5));
		}
		return windowTemp;
	}

	private int setFrameLen(int sampleRate) {
		return (int) (0.025 * (double) (sampleRate));
	}

	private int setFrameShift(int sampleRate) {
		return (int) (0.0125 * (double) (sampleRate));
	}

	private void arrayLimitCheck(int low, int high) throws MfccServiceException {
		if (low > high) {
			throw new MfccServiceException(wrongLimitsError);
		}
	}

}
