package speakerrecognition.services;

import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;

@Service
public class MatrixesService {

	final private static String vectorsDisMatched = "Vectors must have the same length!";
	final private static String matrixesColsNotEqualsRows = "Number of columns in primary matrix it is not the same as number of rows in secondary matrix!";
	final private static String matrixesWrongDimnesionsElByEl = "Matrixes do not have the same dimnesions!";
	final private static String matrixVectorWrongDimnesionsElByEl = "Operation element by elemen for matrix and vector need rows or columns the same as vector's number of elements";
	final private static String vectorDisMatchesToMatrix = "Cannot multiply matrix by vector. Number of elements in vector must be equal to number of columns in matrix";
	final private static String wrongDimnesion = "Dimnesion cannot be less or equal to 0.";
	final private static String numOfRowOutOfRange = "Selected row out of range.";
	final private static String wrongValueOfTimesDuplicating = "Can not duplicate less than once";
	
	
	/** vector * scalar = vector
	 * @param vector
	 * @param scalar
	 * @return
	 */
	public double[] vectorMultiplyByScalar(double[] vector, double scalar) {
		int columns = vector.length;
		for(int numOfCol=0;numOfCol<columns;numOfCol++){
			vector[numOfCol] = vector[numOfCol] * scalar;
		}
		return vector;
	}
	/** vector * vector = vector
	 * @param primaryVector
	 * @param secondaryVector
	 * @return
	 * @throws MatrixesServiceException
	 */
	public double[] vectorMultiplyByVector(double[] primaryVector, double[] secondaryVector) throws MatrixesServiceException{
		validateVectors(primaryVector, secondaryVector);
			double[] vectorToReturn = new double[primaryVector.length];
			for(int numOfCol=0;numOfCol<primaryVector.length;numOfCol++){
				vectorToReturn[numOfCol] = primaryVector[numOfCol] * secondaryVector[numOfCol];
			}
			return vectorToReturn;
	}

	private void validateVectors(double[] primaryVector, double[] secondaryVector) throws MatrixesServiceException {
		if(primaryVector.length!=secondaryVector.length) {
			throw new MatrixesServiceException(vectorsDisMatched);
		}
	}
	
	/** matrix * scalar = matrix
	 * @param matrix
	 * @param scalar
	 * @return
	 */
	public  double[][] matrixMultiplyByScalar(double[][] matrix, double scalar){
		for(int numOfRow=0;numOfRow<matrix.length;numOfRow++){
			for(int numOfCol=0;numOfCol<matrix[0].length;numOfCol++)
				matrix[numOfRow][numOfCol] = matrix[numOfRow][numOfCol] * scalar;
		}
		return matrix;
	}
	
	
	/** matrix * matrix = matrix
	 * @param primaryMatrix
	 * @param secondaryMatrix
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] matrixMultiplyByMatrix(double[][] primaryMatrix, double[][] secondaryMatrix) throws MatrixesServiceException{
        validateMatrixesToMultiply(primaryMatrix, secondaryMatrix);

		int primaryMatrixCols = primaryMatrix[0].length;
        int matrixToReturnRows = primaryMatrix.length;
        int matrixToReturnCols = secondaryMatrix[0].length;
        double[][] matrixToReturn = new double[matrixToReturnRows][matrixToReturnCols];
        for(int numOfRow = 0; numOfRow < matrixToReturnRows; numOfRow++) {
            for(int numOfCol = 0; numOfCol < matrixToReturnCols; numOfCol++) {
                for(int numOfEl = 0; numOfEl < primaryMatrixCols; numOfEl++) {
                    matrixToReturn[numOfRow][numOfCol] += primaryMatrix[numOfRow][numOfEl] * secondaryMatrix[numOfEl][numOfCol];
                }
            }
        }
        return matrixToReturn;
    }

	private void validateMatrixesToMultiply(double[][] primaryMatrix, double[][] secondaryMatrix)
			throws MatrixesServiceException {
		
		int primaryMatrixCols = primaryMatrix[0].length;
        int secondaryMatrixRows = secondaryMatrix.length;
        
        if(primaryMatrixCols != secondaryMatrixRows) {
        	throw new MatrixesServiceException(matrixesColsNotEqualsRows);
        }
	}

	/** elements * elements in matrixes the same places
	 * @param primaryMatrix
	 * @param secondaryMatrix
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] multiplyMatrixesElementByElement(double[][] primaryMatrix, double[][] secondaryMatrix) throws MatrixesServiceException {
		validateMatrixesTheSameDimnesions(primaryMatrix, secondaryMatrix);
		
		int matrixToReturnNumOfRows = primaryMatrix.length;
		int matrixToReturnNumOfCols = primaryMatrix[0].length;
		double[][] matrixToReturn = new double[matrixToReturnNumOfRows][matrixToReturnNumOfCols];
		for(int numOfRow=0;numOfRow<matrixToReturnNumOfRows;numOfRow++){
			for(int numOfCol=0;numOfCol<matrixToReturnNumOfCols;numOfCol++){
				matrixToReturn[numOfRow][numOfCol] = primaryMatrix[numOfRow][numOfCol]*secondaryMatrix[numOfRow][numOfCol];
			}
		}
		return matrixToReturn;
		
	}

	private void validateMatrixesTheSameDimnesions(double[][] primaryMatrix, double[][] secondaryMatrix)
			throws MatrixesServiceException {
		int primaryMatrixRows = primaryMatrix.length;
		int primaryMatrixCols = primaryMatrix[0].length;
		int secondaryMatrixRows = secondaryMatrix.length;
		int secondaryMatrixCols = secondaryMatrix[0].length;
		
		if(primaryMatrixCols!=secondaryMatrixCols || primaryMatrixRows!=secondaryMatrixRows){
			throw new MatrixesServiceException(matrixesWrongDimnesionsElByEl);
			}
	}
	
	/** elements of matrix elements multiplying by vector elements as rows or columns
	 * @param matrix
	 * @param vector
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] matrixMultiplyByVectorElByEl(double[][] matrix, double[] vector) throws MatrixesServiceException{
		
		validateRowsAndColsMatrixToVector(matrix, vector);

		int matrixNumOfRows = matrix.length;
		int matrixNumOfCols = matrix[0].length;
		int numOfEl = vector.length;
		double[][] matrixToReturn = new double[matrixNumOfRows][matrixNumOfCols];
		
		if(matrixNumOfRows==numOfEl){
			for(int numOfCol=0;numOfCol<matrixNumOfCols;numOfCol++){
				for(int numOfRow=0;numOfRow<matrixNumOfRows;numOfRow++)
					matrixToReturn[numOfRow][numOfCol] = matrix[numOfRow][numOfCol]* vector[numOfRow];
			}
		}
		else
		{
			for(int numOfRow=0;numOfRow<matrixNumOfRows;numOfRow++){
				for(int numOfCol=0;numOfCol<matrixNumOfCols;numOfCol++)
					matrixToReturn[numOfRow][numOfCol] = matrix[numOfRow][numOfCol]* vector[numOfCol];
			}
		}
		return matrixToReturn;
	}

	private void validateRowsAndColsMatrixToVector(double[][] matrix, double[] vector) throws MatrixesServiceException {
		int matrixNumOfRows = matrix.length;
		int matrixNumOfCols = matrix[0].length;
		int numOfEl = vector.length;
		if(matrixNumOfRows!=numOfEl && matrixNumOfCols!=numOfEl ) {
			throw new MatrixesServiceException(matrixVectorWrongDimnesionsElByEl);
		}
	}
	/** typical matrix multiplied by vector
	 * @param matrix
	 * @param vector
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[] matrixMultiplyByVector(double[][] matrix, double[] vector) throws MatrixesServiceException{
		
		
		validateMatrixVector(matrix,vector);

		int matrixNumOfRows = matrix.length;
		int matrixNumOfCols = matrix[0].length;

		double[] vectorToReturn = new double[matrixNumOfRows];
		for(int numOfRow=0;numOfRow<matrixNumOfRows;numOfRow++){
			for(int numOfCol=0;numOfCol<matrixNumOfCols;numOfCol++){
			vectorToReturn[numOfRow] += matrix[numOfRow][numOfCol]* vector[numOfCol];
			}
		}
			return vectorToReturn;
	}

	private void validateMatrixVector(double[][] matrix, double[] vector) throws MatrixesServiceException {
		int matrixNumOfCols = matrix[0].length;
		int numOfEl = vector.length;

		if(matrixNumOfCols!=numOfEl ) {
			throw new MatrixesServiceException(vectorDisMatchesToMatrix);
		}
	}
	/** energy of all elements in matrix
	 * @param matrix
	 * @return
	 */
	public  double squaredNormOfMatrix(double[][] matrix){
		double sumOfSquares = 0;
		for(int numOfRow=0;numOfRow<matrix.length;numOfRow++){
			for(int numOfCol=0;numOfCol<matrix[0].length;numOfCol++)
				sumOfSquares+=Math.pow(matrix[numOfRow][numOfCol], 2);
		}	
		return sumOfSquares;
	}
	
	public  double[] vectorOfSquaresNormOfRowsFromMatrix(double[][] matrix){
		int rows = matrix.length;
		double[] vecOfSquares = new double [rows];
		for(int numOfRow=0;numOfRow<rows;numOfRow++){
			for(int numOfCol=0;numOfCol<matrix[0].length;numOfCol++)
				vecOfSquares[numOfRow]+=Math.pow(matrix[numOfRow][numOfCol], 2);
		}	
		return vecOfSquares;
	}

	/** creating matrix dimnesionXdimnesion with number of row as value of elements
	 * @param dimnesion
	 * @return
	 */
	public  double[][] meshgridOx(int dimnesion){
		double[][] matrix = new double[dimnesion][dimnesion];
		for(int numOfCol=0;numOfCol<dimnesion;numOfCol++){
			for(int numOfRow=0;numOfRow<dimnesion;numOfRow++){
				matrix[numOfRow][numOfCol] = numOfRow;
			}
		}
		return matrix;
	}
	
	/** creating matrix dimnesionXdimnesion with number of column as value of elements
	 * @param dimnesion
	 * @return
	 */
	public  double[][] meshgridOy(int dimnesion){
		double[][] matrix = new double[dimnesion][dimnesion];
		for(int numOfRow=0;numOfRow<dimnesion;numOfRow++){
			for(int numOfCol=0;numOfCol<dimnesion;numOfCol++){
				matrix[numOfRow][numOfCol] = numOfCol;
			}
		}
		return matrix;
	}
	
	/** transponsed matrix rows as columns and columns as rows
	 * @param matrix
	 * @return
	 */
	public  double[][] transposeMatrix(double[][] matrix){
		int numOfRowOfMatrix = matrix.length;
		int numOfColOfMatrix = matrix[0].length;
		double[][] matrixToReturn = new double[numOfColOfMatrix][numOfRowOfMatrix];
		for(int numOfRow=0;numOfRow<numOfColOfMatrix;numOfRow++){
			for(int numOfCol=0;numOfCol<numOfRowOfMatrix;numOfCol++){
				matrixToReturn[numOfRow][numOfCol] = matrix[numOfCol][numOfRow];
			}
		}
		return matrixToReturn;
		
	}
	
	/** filling vector by value of scalar
	 * @param vector
	 * @param scalar
	 * @return
	 */
	public  double[] fillVectorWithScalar(double[] vector, double scalar){
		int columns = vector.length;
		double[] vectorToReturn = new double[columns];
		for(int numOfCol=0; numOfCol<columns;numOfCol++)
			vectorToReturn[numOfCol] = scalar;
		return vectorToReturn;
	}
	
	/** matrix elements substraced by vector elements in rows or columns
	 * @param matrix
	 * @param vector
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] matrixSubstractVector(double[][] matrix, double vector[]) throws MatrixesServiceException{
		int matrixNumOfRows = matrix.length;
		int matrixNumOfCols = matrix[0].length;
		int numOfEl = vector.length;
		
		validateRowsAndColsMatrixToVector(matrix, vector);
		
		double[][] matrixToReturn = new double[matrixNumOfRows][matrixNumOfCols];
		
		if(matrixNumOfRows == numOfEl){
			for(int numOfRow=0; numOfRow<matrixNumOfRows;numOfRow++){
				for(int numOfCol=0;numOfCol<matrixNumOfCols;numOfCol++)
					matrixToReturn[numOfRow][numOfCol] = matrix[numOfRow][numOfCol]-vector[numOfRow];
			}
		}
		else {
			for(int numOfRow=0; numOfRow<matrixNumOfRows;numOfRow++){
				for(int numOfCol=0;numOfCol<matrixNumOfCols;numOfCol++)
					matrixToReturn[numOfRow][numOfCol] = matrix[numOfRow][numOfCol]-vector[numOfCol];
			}
		}
		return matrixToReturn;
	}
	
	/** value of scalar added to every element in vector
	 * @param vector
	 * @param scalar
	 * @return
	 */
	public  double[] vectorAddScalar(double[] vector, double scalar){
		int numOfEl = vector.length;
		double[] vectorToReturn = new double[numOfEl];
		for(int numOfCol=0; numOfCol<numOfEl;numOfCol++)
			vectorToReturn[numOfCol] = vector[numOfCol]+scalar;
		return vectorToReturn;
	}
	
	/** value of scalar added to every matrix element
	 * @param matrix
	 * @param scalar
	 * @return
	 */
	public  double[][] matrixAddScalar(double[][] matrix, double scalar){
		int matrixNumOfRows = matrix.length;
		int matrixNumOfCols = matrix[0].length;
		double[][] matrixToReturn = new double[matrixNumOfRows][matrixNumOfCols];
		for(int numOfRow=0; numOfRow<matrixNumOfRows;numOfRow++){
			for(int numOfCol=0;numOfCol<matrixNumOfCols;numOfCol++)
				matrixToReturn[numOfRow][numOfCol] = matrix[numOfRow][numOfCol]+scalar;
		}
			
		return matrixToReturn;
	}
	
	/** matrix elements added by vector elements in rows or columns
	 * @param matrix
	 * @param vector
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] matrixAddVector(double[][] matrix, double vector[]) throws MatrixesServiceException{
		int matrixNumOfRows = matrix.length;
		int matrixNumOfCols = matrix[0].length;
		int numOfEl = vector.length;
		
		validateRowsAndColsMatrixToVector(matrix, vector);
		
		double[][] matrixToReturn = new double[matrixNumOfRows][matrixNumOfCols];
		
		if(matrixNumOfRows == numOfEl){
			for(int numOfRow=0; numOfRow<matrixNumOfRows;numOfRow++){
				for(int numOfCol=0;numOfCol<matrixNumOfCols;numOfCol++)
					matrixToReturn[numOfRow][numOfCol] = matrix[numOfRow][numOfCol]+vector[numOfRow];
			}
		}
		else {
			for(int numOfRow=0; numOfRow<matrixNumOfRows;numOfRow++){
				for(int numOfCol=0;numOfCol<matrixNumOfCols;numOfCol++)
					matrixToReturn[numOfRow][numOfCol] = matrix[numOfRow][numOfCol]+vector[numOfCol];
			}
		}
		return matrixToReturn;
	}
	
	/** adding vector to vector
	 * @param primaryVector
	 * @param secondaryVector
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[] vectorAddVector(double[] primaryVector, double[] secondaryVector)throws MatrixesServiceException{

		validateVectors(primaryVector, secondaryVector);
		int columns = primaryVector.length;
		double[] vectorToReturn = new double[columns];		
		
		for(int i=0;i<primaryVector.length;i++){
			vectorToReturn[i] = primaryVector[i] + secondaryVector[i];
		}
		return vectorToReturn;
	}
	
	/** adding matrix to matrix
	 * @param primaryMatrix
	 * @param secondaryMatrix
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] matrixAddMatrix(double[][] primaryMatrix, double[][] secondaryMatrix) throws MatrixesServiceException{
		
		
		validateMatrixesTheSameDimnesions(primaryMatrix, secondaryMatrix);

		int primaryMatrixRows = primaryMatrix.length;
		int primaryMatrixCols = primaryMatrix[0].length;
		double[][] matrixToReturn = new double[primaryMatrixRows][primaryMatrixCols];
		for(int numOfRow=0;numOfRow<primaryMatrixRows;numOfRow++){
			for(int numOfCol=0;numOfCol<primaryMatrixCols;numOfCol++)
				matrixToReturn[numOfRow][numOfCol] = primaryMatrix[numOfRow][numOfCol] + secondaryMatrix[numOfRow][numOfCol];
		}
		return matrixToReturn;
	}
	
/** substracting matrix from matrix
 * @param primaryMatrix
 * @param secondaryMatrix
 * @return
 * @throws MatrixesServiceException
 */
public  double[][] matrixSubstractMatrix(double[][] primaryMatrix, double[][] secondaryMatrix) throws MatrixesServiceException{
		
		validateMatrixesTheSameDimnesions(primaryMatrix, secondaryMatrix);
		
		int primaryMatrixRows = primaryMatrix.length;
		int primaryMatrixCols = primaryMatrix[0].length;
		double[][] matrixToReturn = new double[primaryMatrixRows][primaryMatrixCols];
		for(int numOfRow=0;numOfRow<primaryMatrixRows;numOfRow++){
			for(int numOfCol=0;numOfCol<primaryMatrixCols;numOfCol++)
				matrixToReturn[numOfRow][numOfCol] = primaryMatrix[numOfRow][numOfCol] - secondaryMatrix[numOfRow][numOfCol];
		}
		return matrixToReturn;
	}
	
	
	/** summary of vector elements
	 * @param vector
	 * @return
	 */
	public  double sumOfVectorElements(double[] vector){
		double sumOfEl = 0;
		int columns = vector.length;
		for(int numOfCol=0;numOfCol<columns;numOfCol++){
			sumOfEl += vector[numOfCol];
		}
		return sumOfEl;
	}
	
	/** summary of matrix elements
	 * @param matrix
	 * @return
	 */
	public  double sumOfMatrixElements(double[][] matrix){
		double sumOfEl = 0;
		int rows = matrix.length;
		int columns = matrix[0].length;
		for(int numOfRow=0;numOfRow<rows;numOfRow++){
			for(int numOfCol=0;numOfCol<columns;numOfCol++){
				sumOfEl += matrix[numOfRow][numOfCol];
			}
		}
		return sumOfEl;
	}
	/** summary of values in every row
	 * @param matrix
	 * @return
	 */
	public double[] sumsOfElementsInRows(double[][] matrix){
		int rows = matrix.length;
		int cols = matrix[0].length;
		double[] vectorToReturn = new double[rows];
		for(int numOfRow=0;numOfRow<rows;numOfRow++){
			for(int numOfCol=0;numOfCol<cols;numOfCol++){
				vectorToReturn[numOfRow] += matrix[numOfRow][numOfCol];
			}
		}
		return vectorToReturn;
	}
	
	/** summary of values in every column
	 * @param matrix
	 * @return
	 */
	public double[] sumsOfElementsInCols(double[][] matrix){
		int rows = matrix.length;
		int cols = matrix[0].length;
		double[] vectorToReturn = new double[rows];
		for(int numOfCol=0;numOfCol<cols;numOfCol++){
			for(int numOfRow=0;numOfRow<rows;numOfRow++){
				vectorToReturn[numOfCol] += matrix[numOfRow][numOfCol];
			}
		}
		return vectorToReturn;
	}
	/** generate random vector with parameters like max value of vector elements and his dimnesion
	 * @param maxValue
	 * @param dimnesion
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[] generateRandomVector(double maxValue, int dimnesion) throws MatrixesServiceException{
		validateDimnesion(dimnesion);
		double[] vectorToReturn = new double[dimnesion];
		for(int numOfCol=0;numOfCol<dimnesion;numOfCol++){
			vectorToReturn[numOfCol] = Math.random()*maxValue;
		}
		return vectorToReturn;
	}

	private void validateDimnesion(int dimnesion) throws MatrixesServiceException {
		if(dimnesion<=0) {
			throw new MatrixesServiceException(wrongDimnesion);
		}
	}
	
	/** generate random matrix described by max value and his dimnesions
	 * @param maxValue
	 * @param dimnesionX
	 * @param dimnesionY
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] generateRandomMatrix(double maxValue, int dimnesionX, int dimnesionY) throws MatrixesServiceException{
		validateDimnesion(dimnesionX);
		validateDimnesion(dimnesionY);
		double[][] matrixToReturn = new double[dimnesionX][dimnesionY];
		for(int numOfRow=0;numOfRow<dimnesionX;numOfRow++){
			for(int numOfCol=0;numOfCol<dimnesionY;numOfCol++){
				matrixToReturn[numOfRow][numOfCol] = Math.random()*maxValue;
			}
		}
		return matrixToReturn;
	}
	/**
	 * @param primaryVector
	 * @param secondaryVector
	 * @return
	 */
	public  int[] searchSorted(double[] primaryVector, double[] secondaryVector){
		int elsInSecVec = secondaryVector.length;
		int elsInPrimVec = primaryVector.length;
		int[] vectorToReturn = new int[elsInSecVec];
		
		for(int numOfElInSecVec=0;numOfElInSecVec<elsInSecVec;numOfElInSecVec++){
			for(int numOfElInPrimVec=0;numOfElInPrimVec<elsInPrimVec;numOfElInPrimVec++){
				if(primaryVector[numOfElInPrimVec]>secondaryVector[numOfElInSecVec]){
					vectorToReturn[numOfElInSecVec]=numOfElInPrimVec;
					break;
				}
			}
		}
		return vectorToReturn;
		
	}
	
	/** minimum values from two vectors
	 * @param primaryVector
	 * @param secondaryVector
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[] minimumValuesFromVectors(double[] primaryVector, double[] secondaryVector) throws MatrixesServiceException{
		
		validateVectors(primaryVector, secondaryVector);
		double[] minVector = new double[primaryVector.length];
		for(int numOfCol=0;numOfCol<primaryVector.length;numOfCol++){
			if(secondaryVector[numOfCol]<primaryVector[numOfCol]){
				minVector[numOfCol]=secondaryVector[numOfCol];
			}
			else{
				minVector[numOfCol]=primaryVector[numOfCol];
			}
		}
		return minVector;
	}
	
	/** select row of matrix as vector
	 * @param matrix
	 * @param numOfRow
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[] selectRow(double[][] matrix, int numOfRow) throws MatrixesServiceException{
		
		validateSelectedRow(numOfRow, matrix);
		
		int columns = matrix[0].length;
		double vectorToReturn[] = new double[columns];
		for(int numOfCol=0;numOfCol<columns;numOfCol++)
			vectorToReturn[numOfCol] = matrix[numOfRow][numOfCol];
		return vectorToReturn;
	}

	private void validateSelectedRow(int numOfRow, double[][] matrix) throws MatrixesServiceException {

		int rows = matrix.length-1;
		if(numOfRow>rows||numOfRow<0) {
			throw new MatrixesServiceException(numOfRowOutOfRange);
		}
	}
	
	/** energy of vector
	 * @param vector
	 * @return
	 */
	public  double squaredNormOfVector(double[] vector){
		double sumOfSquares = 0;
		int columns = vector.length;
		
		for(int numOfCol=0;numOfCol<columns;numOfCol++){
			sumOfSquares += Math.pow(vector[numOfCol],2);
		}
		return sumOfSquares;
	}
	
	/** euclidean distance between vector and matrix
	 * @param primaryVector
	 * @param matrix
	 * @param energiesOfRowsOfMatrix
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[] euclideanDistancesBetweenVectorAndMatrix(double[]primaryVector, double[][] matrix, double[] energiesOfRowsOfMatrix) throws MatrixesServiceException{
		 
		int rows = matrix.length;
		double[] distances = new double[rows];
		double energyOfPrimaryVector = squaredNormOfVector(primaryVector);
		
		distances = matrixMultiplyByVector(matrix, primaryVector);
		distances = vectorMultiplyByScalar(distances, -2);
		distances = vectorAddScalar(distances,  energyOfPrimaryVector);
		distances = vectorAddVector(distances, energiesOfRowsOfMatrix);
		return distances;
	}
	
	/** euclidean distance between matrixes
	 * @param primaryMatrix
	 * @param secondaryMatrix
	 * @param energiesOfRowsOfSecondaryMatrix
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] euclideanDistancesBetweenMatrixes(double[][] primaryMatrix, double[][] secondaryMatrix, double[] energiesOfRowsOfSecondaryMatrix) throws MatrixesServiceException{
		
		double[] energiesOfRowsOfPrimaryMatrix = vectorOfSquaresNormOfRowsFromMatrix(primaryMatrix);
		
		double [][] distances = matrixMultiplyByMatrix(primaryMatrix, transposeMatrix(secondaryMatrix));
		distances = matrixMultiplyByScalar(distances, -2);
		distances = matrixAddVector(distances, energiesOfRowsOfPrimaryMatrix);
		distances = matrixAddVector(distances, energiesOfRowsOfSecondaryMatrix);
		return distances;
	}
	
	/** covariance matrix
	 * @param matrix
	 * @return
	 * @throws StatisticsServiceException 
	 * @throws MatrixesServiceException 
	 */
	public  double[][] cov(double[][] matrix) throws StatisticsServiceException, MatrixesServiceException{
		
		StatisticsService statisticsService = new StatisticsService();
		
		double[][] covMatrix = matrix;
		double[] meanVector = statisticsService.getMean2(transposeMatrix(matrix));
		int columns = matrix[0].length;
		int rows = matrix.length;
		
		for(int numOfCol=0;numOfCol<columns;numOfCol++){
				for(int numOfRow=0; numOfRow<rows; numOfRow++){
					covMatrix[numOfRow][numOfCol] -= meanVector[numOfRow];
				}
			}
		covMatrix = matrixMultiplyByMatrix(matrix, transposeMatrix(covMatrix));
		covMatrix = matrixDividedByScalar(covMatrix, (double)(columns-1));
		
		return covMatrix;
	}
	/** matrix elements divided by value of scalar
	 * @param matrix
	 * @param scalar
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] matrixDividedByScalar(double[][] matrix, double scalar) throws MatrixesServiceException{
		
		validateDividing(scalar);
	
		int matrixNumOfRows = matrix.length;
		int matrixNumOfCols = matrix[0].length;
		double[][] matrixToReturn = new double[matrixNumOfRows][matrixNumOfCols];
		for(int numOfRow=0; numOfRow<matrixNumOfRows;numOfRow++){
			for(int numOfCol=0;numOfCol<matrixNumOfCols;numOfCol++)
				matrixToReturn[numOfRow][numOfCol] = matrix[numOfRow][numOfCol]/scalar;
		}
			
		return matrixToReturn;
	}
	
	/** returning vector with values from matrix which are in diagonal
	 * @param matrix
	 * @return
	 */
	public  double[] chooseDiagonalValues(double[][] matrix){
		int rows = matrix.length;
		double[] vectorToReturn = new double[rows];
		for(int numOfRow=0;numOfRow<rows;numOfRow++)
			vectorToReturn[numOfRow]=matrix[numOfRow][numOfRow];
		return vectorToReturn;
	}
	
	/** making logarithm from any element in vector
	 * @param vector
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[] makeLogarithmInVector(double[] vector) throws MatrixesServiceException{
		int columns = vector.length;
		double[] vectorToReturn = new double[columns];
		for(int numOfCol=0;numOfCol<columns;numOfCol++){

			validateValueToLogarithm(vector[numOfCol]);
			vectorToReturn[numOfCol] = Math.log(vector[numOfCol]);

		}
		return vectorToReturn;
	}

	private void validateValueToLogarithm(double value) throws MatrixesServiceException {
		if(value<=0)
			throw new MatrixesServiceException("Cannot make Log of value below or equal 0.");
	}
	
	/** making logarithm from any element in matrix
	 * @param matrix
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] makeLogrithmInMatrix(double[][] matrix) throws MatrixesServiceException{
		
		int rows = matrix.length;
		int columns = matrix[0].length;
		double[][] matrixToReturn = new double[rows][columns];
		
		for(int numOfRow=0;numOfRow<rows;numOfRow++){
			for(int numOfCol=0;numOfCol<columns;numOfCol++){
				
				validateValueToLogarithm(matrix[numOfRow][numOfCol]);
				matrixToReturn[numOfRow][numOfCol] = Math.log(matrix[numOfRow][numOfCol]);
			}
		}
		return matrixToReturn;
	}
	
	/** inverting elements in vector
	 * @param vector
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[] invertElementsInVector(double[] vector) throws MatrixesServiceException {
		int columns = vector.length;
		double[] vectorToReturn = new double[columns];
		for(int numOfCol=0;numOfCol<columns;numOfCol++){
			if(vector[numOfCol]!=0)
				vectorToReturn[numOfCol] = 1/(vector[numOfCol]);
			else
				vectorToReturn[numOfCol] = 0;
		}
		return vectorToReturn;
	}
	
	/** inverting elements in matrix
	 * @param matrix
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] invertElementsInMatrix(double[][] matrix) throws MatrixesServiceException{
		int rows = matrix.length;
		int columns = matrix[0].length;
		double[][] matrixToReturn = new double[rows][columns];
		
		for(int numOfRow=0;numOfRow<rows;numOfRow++){
			for(int numOfCol=0;numOfCol<columns;numOfCol++){
				
				if(matrix[numOfRow][numOfCol]!=0)
					matrixToReturn[numOfRow][numOfCol] = 1/(matrix[numOfRow][numOfCol]);
				else
					matrixToReturn[numOfRow][numOfCol] = 0;		
			}
		}
		return matrixToReturn;
	}
	
	/** matrix elements divided by elements from another matrix
	 * @param primaryMatrix
	 * @param secondaryMatrix
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] matrixDividedByMatrix(double[][] primaryMatrix, double[][] secondaryMatrix) throws MatrixesServiceException {
		
		validateMatrixesTheSameDimnesions(primaryMatrix,secondaryMatrix);
		int matrixToReturnNumOfRows = primaryMatrix.length;
		int matrixToReturnNumOfCols = primaryMatrix[0].length;
		double[][] matrixToReturn = new double[matrixToReturnNumOfRows][matrixToReturnNumOfCols];
		for(int numOfRow=0;numOfRow<matrixToReturnNumOfRows;numOfRow++){
			for(int numOfCol=0;numOfCol<matrixToReturnNumOfCols;numOfCol++){
				validateDividing(secondaryMatrix[numOfRow][numOfCol]);
				matrixToReturn[numOfRow][numOfCol] = primaryMatrix[numOfRow][numOfCol]/secondaryMatrix[numOfRow][numOfCol];
			}
		}
		return matrixToReturn;
		
	}

	private void validateDividing(double value)
			throws MatrixesServiceException {
		if(value==0.0)
			throw new MatrixesServiceException("Cannot divide by 0.");
	}
		
	/** elements of matrix power to double value
	 * @param matrix
	 * @param power
	 * @return
	 */
	public  double[][] matrixElementsToPower(double[][] matrix, double power){
		int rows = matrix.length;
		int columns = matrix[0].length;
		double[][] matrixToReturn = new double[rows][columns];
		for(int numOfRow=0;numOfRow<rows;numOfRow++){
			for(int numOfCol=0;numOfCol<columns;numOfCol++)
				matrixToReturn[numOfRow][numOfCol] = Math.pow(matrix[numOfRow][numOfCol], power);
		}
		return matrixToReturn;
	}
	
	public  double[] logSumExp(double[][] data) throws MatrixesServiceException{
			double[] maxValues = maxInCol(transposeMatrix(data));
			double[] vectorToReturn = makeLogarithmInVector(sumsOfElementsInRows(eulerNumberToMatrixElementsPower(matrixSubstractVector(transposeMatrix(data), maxValues))));
			vectorToReturn = vectorAddVector(vectorToReturn, maxValues);
		return vectorToReturn;
	}
	
	public  double[][] eulerNumberToMatrixElementsPower(double[][] matrix){
		int rows = matrix.length;
		int columns = matrix[0].length;
		double[][] matrixToReturn = new double[rows][columns];
		for(int numOfRow=0;numOfRow<rows;numOfRow++){
			for(int numOfCol=0;numOfCol<columns;numOfCol++)
				matrixToReturn[numOfRow][numOfCol] = Math.exp(matrix[numOfRow][numOfCol]);
		}
		return matrixToReturn;
	}
	
	/** maximum values in columns of matrix
	 * @param matrix
	 * @return
	 */
	public  double[] maxInCol(double[][] matrix){
		int cols = matrix[0].length;
		int rows = matrix.length;
		double maxValues[] = new double[cols];
			
			for(int numOfCol=0;numOfCol<cols;numOfCol++){
					maxValues[numOfCol] = Double.NEGATIVE_INFINITY;
			}  //JAK CO TO USUNĄĆ!!!
			
			for(int numOfCol=0;numOfCol<cols;numOfCol++){
				for(int numOfRow=0;numOfRow<rows;numOfRow++)
					if(maxValues[numOfCol]<matrix[numOfRow][numOfCol])
						maxValues[numOfCol]=matrix[numOfRow][numOfCol];
			}
		return maxValues;
	}
	/** maximum values in row of matrix
	 * @param matrix
	 * @return
	 */
	public  double[] maxInRow(double[][] matrix){
		int rows = matrix.length;
		double maxValues[] = new double[rows];
			
			for(int numOfRow=0;numOfRow<rows;numOfRow++){
					maxValues[numOfRow] = Double.NEGATIVE_INFINITY;
			}  //JAK CO TO USUNĄĆ!!!
			
			for(int numOfRow=0;numOfRow<rows;numOfRow++){
				int cols = matrix[0].length;
				for(int numOfCol=0;numOfCol<cols;numOfCol++)
					if(maxValues[numOfRow]<matrix[numOfRow][numOfCol])
						maxValues[numOfRow]=matrix[numOfRow][numOfCol];
			}
		return maxValues;
	}
	
	/** creating matrix fulled of elements with value = 1
	 * @param dimnesion
	 * @return
	 */
	public  double[][] eye(int dimnesion){
		double[][] matrixToReturn = new double[dimnesion][dimnesion];
		for(int numOfEl=0;numOfEl<dimnesion;numOfEl++)
			matrixToReturn[numOfEl][numOfEl]=1;
		return matrixToReturn;
	}
	
	/** creating matrix fulled of elements with value described by coef
	 * @param dimnesion
	 * @param coef
	 * @return
	 */
	public  double[][] eyeWithCoef(int dimnesion, double coef){
		double[][] matrixToReturn = new double[dimnesion][dimnesion];
		for(int numOfEl=0;numOfEl<dimnesion;numOfEl++)
			matrixToReturn[numOfEl][numOfEl]=coef;
		return matrixToReturn;
	}
	
	/** creating matrix by duplicating vector
	 * @param vector
	 * @param times
	 * @return
	 * @throws MatrixesServiceException
	 */
	public  double[][] duplicatedVectorsIntoMatrix(double[] vector, int times) throws MatrixesServiceException{
		validateTimesDuplicate(times);
		int columns = vector.length;
		double[][] matrixToReturn = new double[times][columns];
		for(int numOfRow=0;numOfRow<times;numOfRow++)
			matrixToReturn[numOfRow]=vector;
		return matrixToReturn;
	}

	private void validateTimesDuplicate(int times) throws MatrixesServiceException {
		if(times<1) {
			throw new MatrixesServiceException(wrongValueOfTimesDuplicating);
		}
	}
}
