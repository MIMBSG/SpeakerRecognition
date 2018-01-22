import { Injectable } from '@angular/core';

@Injectable()
export class MatrixesService {

  constructor() { }
    public vectorMultiplyByVector(primaryVector: number[], secondaryVector: number[]): number[] {
          this.validateVectors(primaryVector, secondaryVector);
      		let vectorToReturn: number[] = this.initVector(primaryVector.length);
      		for (let numOfCol: number = 0; numOfCol < primaryVector.length; numOfCol++) {
      			vectorToReturn[numOfCol] = primaryVector[numOfCol] * secondaryVector[numOfCol];
      		}
      		return vectorToReturn;
    }

	  private validateVectors(primaryVector: number[], secondaryVector: number[]): void {
		     if (primaryVector.length != secondaryVector.length) {
			        //throw new MatrixesServiceException(VECTORS_MISMATCHED);
		          }
	  }
    public matrixMultiplyByVector(matrix: number[][], vector: number[]): number[]{
      //validateMatrixVector(matrix, vector);
      let matrixNumOfRows: number = matrix.length;
      let matrixNumOfCols: number = matrix[0].length;
      let vectorToReturn: number[] = this.initVector(matrixNumOfRows);

      for (let numOfRow: number = 0; numOfRow < matrixNumOfRows; numOfRow++) {
        for (let numOfCol: number = 0; numOfCol < matrixNumOfCols; numOfCol++) {
          vectorToReturn[numOfRow] += matrix[numOfRow][numOfCol] * vector[numOfCol];
        }
      }
      return vectorToReturn;
    }

    public multiplyMatrixesElementByElement(primaryMatrix: number[][], secondaryMatrix: number[][]): number[][]{
      //validateMatrixesTheSameDimnesions(primaryMatrix, secondaryMatrix);
      let matrixToReturnNumOfRows: number = primaryMatrix.length;
      let matrixToReturnNumOfCols: number = primaryMatrix[0].length;
      let matrixToReturn: number[][] = this.initMatrix(matrixToReturnNumOfRows, matrixToReturnNumOfCols);

      for (let numOfRow: number = 0; numOfRow < matrixToReturnNumOfRows; numOfRow++) {
        for (let numOfCol: number = 0; numOfCol < matrixToReturnNumOfCols; numOfCol++) {
          matrixToReturn[numOfRow][numOfCol] = primaryMatrix[numOfRow][numOfCol]
              * secondaryMatrix[numOfRow][numOfCol];
        }
      }
      return matrixToReturn;
    }
    public vectorMultiplyByScalar(vector: number[], scalar: number): number[]{
      let columns: number = vector.length;
		    for (let numOfCol: number = 0; numOfCol < columns; numOfCol++) {
			       vector[numOfCol] = vector[numOfCol] * scalar;
		         }
		  return vector;
    }
    public initMatrix(rows: number, columns: number): number[][]{
        var defaultArray2d = [];
        for(let row: number = 0; row < rows ; row++){
          let defaultRow: number[] = new Array(columns);
          for(let column: number = 0; column < columns ; column++){
            defaultRow[column] = 0;
          }
          defaultArray2d[row] = defaultRow;
        }
        return defaultArray2d;
    }
    public initVector(elements: number): number[]{
        let defaultArray: number[] = new Array(elements);
          for(let element: number = 0; element < elements ; element++){
            defaultArray[element] = 0;
          }
        return defaultArray;
    }
}
