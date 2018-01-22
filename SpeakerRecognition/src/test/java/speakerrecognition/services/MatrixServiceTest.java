package speakerrecognition.services;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.services.MatrixesService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MatrixServiceTest {

	@Autowired
	private MatrixesService matrixesService;

	@Test
	public void shouldMultiplyVectorByScalar() {
		// given
		double[] vector = { 1.0, 2.0, 3.0 };
		double scalar = 2.0;
		// when
		double[] expectedVector = { 2.0, 4.0, 6.0 };
		double[] returnedVector = matrixesService.vectorMultiplyByScalar(vector, scalar);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test
	public void shouldAddScalarToVector() {
		// given
		double[] vector = { 1.0, 2.0, 3.0 };
		double scalar = 2.0;
		// when
		double[] expectedVector = { 3.0, 4.0, 5.0 };
		double[] returnedVector = matrixesService.vectorAddScalar(vector, scalar);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test
	public void shouldMultiplyMatrixByScalar() {
		// given
		double[][] matrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		double scalar = 2.0;
		// when
		double[][] expectedMatrix = { { 2.0, 4.0, 6.0 }, { 8.0, 10.0, 12.0 } };
		double[][] returnedMatrix = matrixesService.matrixMultiplyByScalar(matrix, scalar);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldScalarAddToMatrix() {
		// given
		double[][] matrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		double scalar = 2.0;
		// when
		double[][] expectedMatrix = { { 3.0, 4.0, 5.0 }, { 6.0, 7.0, 8.0 } };
		double[][] returnedMatrix = matrixesService.matrixAddScalar(matrix, scalar);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldDivideMatrixByScalar() throws MatrixesServiceException {
		// given
		double[][] matrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		double scalar = 2.0;
		// when
		double[][] expectedMatrix = { { 1.0 / scalar, 2.0 / scalar, 3.0 / scalar },
				{ 4.0 / scalar, 5.0 / scalar, 6.0 / scalar } };
		double[][] returnedMatrix = matrixesService.matrixDividedByScalar(matrix, scalar);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldAddVectorToVectorElByEl() throws MatrixesServiceException {
		// given
		double[] primaryVector = { 3.0, 4.0, 7.0 };
		double[] secondaryVector = { 2.0, -3.0, 8.0 };
		// when
		double[] expectedVector = { 5.0, 1.0, 15.0 };
		double[] returnedVector = matrixesService.vectorAddVector(primaryVector, secondaryVector);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test
	public void shouldMultiplyMatrixByMatrix() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		double[][] secondaryMatrix = { { 1.0, 2.0 }, { 5.0, 6.0 }, { 9.0, 10.0 } };
		// when
		double[][] expectedMatrix = { { 38.0, 44.0 }, { 83.0, 98.0 } };
		double[][] returnedMatrix = matrixesService.matrixMultiplyByMatrix(primaryMatrix, secondaryMatrix);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test(expected = MatrixesServiceException.class)
	public void shouldFailByWrongDimnesionInSecondaryMatrix() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		double[][] secondaryMatrix = { { 1.0, 2.0 }, { 5.0, 6.0 } };
		// when
		matrixesService.matrixMultiplyByMatrix(primaryMatrix, secondaryMatrix);
		// then
		Assert.fail();
	}

	@Test
	public void shouldMultiplyMatrixByMatrixElementByElement() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		double[][] secondaryMatrix = { { 1.0, 5.0, 9.0 }, { 2.0, 6.0, 10.0 } };
		// when
		double[][] expectedMatrix = { { 1.0, 10.0, 27.0 }, { 8.0, 30.0, 60.0 } };
		double[][] returnedMatrix = matrixesService.multiplyMatrixesElementByElement(primaryMatrix, secondaryMatrix);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test(expected = MatrixesServiceException.class)
	public void shouldFailByNotTheSameDimnesionsMatrixes() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		double[][] secondaryMatrix = { { 1.0, 2.0 }, { 5.0, 6.0 } };
		// when
		matrixesService.multiplyMatrixesElementByElement(primaryMatrix, secondaryMatrix);
		// then
		Assert.fail();
	}

	@Test
	public void shouldAddMatrixToMatrixElementByElement() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		double[][] secondaryMatrix = { { 1.0, 5.0, 9.0 }, { 2.0, 6.0, 10.0 } };
		// when
		double[][] expectedMatrix = { { 2.0, 7.0, 12.0 }, { 6.0, 11.0, 16.0 } };
		double[][] returnedMatrix = matrixesService.matrixAddMatrix(primaryMatrix, secondaryMatrix);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldSubstractMatrixFromMatrixElementByElement() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		double[][] secondaryMatrix = { { 1.0, 5.0, 9.0 }, { 2.0, 6.0, 10.0 } };
		// when
		double[][] expectedMatrix = { { 0.0, -3.0, -6.0 }, { 2.0, -1.0, -4.0 } };
		double[][] returnedMatrix = matrixesService.matrixSubstractMatrix(primaryMatrix, secondaryMatrix);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldDivideMatrixByMatrixElementByElement() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		double[][] secondaryMatrix = { { 1.0, 5.0, 9.0 }, { 2.0, 6.0, 10.0 } };
		// when
		double[][] expectedMatrix = { { 1.0, 2.0 / 5.0, 3.0 / 9.0 }, { 4.0 / 2.0, 5.0 / 6.0, 6.0 / 10.0 } };
		double[][] returnedMatrix = matrixesService.matrixDividedByMatrix(primaryMatrix, secondaryMatrix);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test(expected = MatrixesServiceException.class)
	public void shouldFailByDivdingByZero() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		double[][] secondaryMatrix = { { 0.0, 5.0, 9.0 }, { 2.0, 6.0, 10.0 } };
		// when
		matrixesService.matrixDividedByMatrix(primaryMatrix, secondaryMatrix);
		// then
		Assert.fail();
	}

	@Test
	public void shouldMultiplyMatrixByVectorElementByElement() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 1.0, 2.0 }, { 5.0, 6.0 }, { 9.0, 10.0 } };
		double[] vector = { 3.0, 4.0, 7.0 };
		double[][] secondaryMatrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		// when
		double[][] expectedPrimaryMatrix = { { 3.0, 6.0 }, { 20.0, 24.0 }, { 63.0, 70.0 } };
		double[][] expectedSecondaryMatrix = { { 3.0, 8.0, 21.0 }, { 12.0, 20.0, 42.0 } };
		double[][] returnedPrimaryMatrix = matrixesService.matrixMultiplyByVectorElByEl(primaryMatrix, vector);
		double[][] returnedSecondaryMatrix = matrixesService.matrixMultiplyByVectorElByEl(secondaryMatrix, vector);
		int primaryRows = returnedPrimaryMatrix.length;
		int secondaryRows = returnedSecondaryMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < primaryRows; numOfRow++) {
			Assert.assertArrayEquals(expectedPrimaryMatrix[numOfRow], returnedPrimaryMatrix[numOfRow], 0.0);
		}
		for (int numOfRow = 0; numOfRow < secondaryRows; numOfRow++) {
			Assert.assertArrayEquals(expectedSecondaryMatrix[numOfRow], returnedSecondaryMatrix[numOfRow], 0.0);
		}
	}

	@Test(expected = MatrixesServiceException.class)
	public void shouldFailByTooManyVectorElements() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 1.0, 2.0 }, { 5.0, 6.0 }, { 9.0, 10.0 } };
		double[] vector = { 3.0, 4.0, 7.0, 12.0, 11.0, 111.0 };
		// when
		matrixesService.matrixMultiplyByVectorElByEl(primaryMatrix, vector);
		// then
		Assert.fail();
	}

	@Test
	public void shouldAddVectorToMatrixElementByElement() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 1.0, 2.0 }, { 5.0, 6.0 }, { 9.0, 10.0 } };
		double[] vector = { 3.0, 4.0, 7.0 };
		double[][] secondaryMatrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		// when
		double[][] expectedPrimaryMatrix = { { 4.0, 5.0 }, { 9.0, 10.0 }, { 16.0, 17.0 } };
		double[][] expectedSecondaryMatrix = { { 4.0, 6.0, 10.0 }, { 7.0, 9.0, 13.0 } };
		double[][] returnedPrimaryMatrix = matrixesService.matrixAddVector(primaryMatrix, vector);
		double[][] returnedSecondaryMatrix = matrixesService.matrixAddVector(secondaryMatrix, vector);
		int primaryRows = returnedPrimaryMatrix.length;
		int secondaryRows = returnedSecondaryMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < primaryRows; numOfRow++) {
			Assert.assertArrayEquals(expectedPrimaryMatrix[numOfRow], returnedPrimaryMatrix[numOfRow], 0.0);
		}
		for (int numOfRow = 0; numOfRow < secondaryRows; numOfRow++) {
			Assert.assertArrayEquals(expectedSecondaryMatrix[numOfRow], returnedSecondaryMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldSubstractVectorFromMatrixElementByElement() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 1.0, 2.0 }, { 5.0, 6.0 }, { 9.0, 10.0 } };
		double[] vector = { 3.0, 4.0, 7.0 };
		double[][] secondaryMatrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		// when
		double[][] expectedPrimaryMatrix = { { -2.0, -1.0 }, { 1.0, 2.0 }, { 2.0, 3.0 } };
		double[][] expectedSecondaryMatrix = { { -2.0, -2.0, -4.0 }, { 1.0, 1.0, -1.0 } };
		double[][] returnedPrimaryMatrix = matrixesService.matrixSubstractVector(primaryMatrix, vector);
		double[][] returnedSecondaryMatrix = matrixesService.matrixSubstractVector(secondaryMatrix, vector);
		int primaryRows = returnedPrimaryMatrix.length;
		int secondaryRows = returnedSecondaryMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < primaryRows; numOfRow++) {
			Assert.assertArrayEquals(expectedPrimaryMatrix[numOfRow], returnedPrimaryMatrix[numOfRow], 0.0);
		}
		for (int numOfRow = 0; numOfRow < secondaryRows; numOfRow++) {
			Assert.assertArrayEquals(expectedSecondaryMatrix[numOfRow], returnedSecondaryMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldMultiplyMatrixByVector() throws MatrixesServiceException {
		// given
		double[][] matrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		double[] vector = { 3.0, 4.0, 7.0 };
		// when
		double[] expectedVector = { 32.0, 74.0 };
		double[] returnedVector = matrixesService.matrixMultiplyByVector(matrix, vector);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test(expected = MatrixesServiceException.class)
	public void shouldFailByMatrixTooManyCols() throws MatrixesServiceException {
		// given
		double[][] matrix = { { 1.0, 2.0, 3.0, 5.0 }, { 4.0, 5.0, 6.0, 12.0 } };
		double[] vector = { 3.0, 4.0, 7.0 };
		// when
		matrixesService.matrixMultiplyByVector(matrix, vector);
		// then
		Assert.fail();
	}

	@Test
	public void shouldGetSquaredNormOfMatrix() {
		// given
		double[][] matrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		// when
		double expectedSumOfSquares = 91.0;
		double returnedSumOfSquares = matrixesService.squaredNormOfMatrix(matrix);
		// then
		Assert.assertEquals(expectedSumOfSquares, returnedSumOfSquares, 0.0);
	}

	@Test
	public void shouldGetSumOfMatrixElements() {
		// given
		double[][] matrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		// when
		double expectedSumOfSquares = 21.0;
		double returnedSumOfSquares = matrixesService.sumOfMatrixElements(matrix);
		// then
		Assert.assertEquals(expectedSumOfSquares, returnedSumOfSquares, 0.0);
	}

	@Test
	public void shouldGetSquaredNormOfVector() {
		// given
		double[] vector = { 3.0, 4.0, 7.0 };
		// when
		double expectedSumOfSquares = 74.0;
		double returnedSumOfSquares = matrixesService.squaredNormOfVector(vector);
		// then
		Assert.assertEquals(expectedSumOfSquares, returnedSumOfSquares, 0.0);
	}

	@Test
	public void shouldGetSumOfVectorElements() {
		// given
		double[] vector = { 3.0, 4.0, 7.0 };
		// when
		double expectedSumOfSquares = 14.0;
		double returnedSumOfSquares = matrixesService.sumOfVectorElements(vector);
		// then
		Assert.assertEquals(expectedSumOfSquares, returnedSumOfSquares, 0.0);
	}
	
	@Test
	public void shouldTransponseMatrix() {
		// given
		double[][] matrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 } };
		// when
		double[][] expectedMatrix = { { 1.0, 4.0 }, { 2.0, 5.0 }, { 3.0, 6.0 } };
		double[][] returnedMatrix = matrixesService.transposeMatrix(matrix);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldFillVectorWithScalar() {
		// given
		double[] vector = { 1.0, 2.0, 3.0 };
		double scalar = 2.0;
		// when
		double[] expectedVector = { 2.0, 2.0, 2.0 };
		double[] returnedVector = matrixesService.fillVectorWithScalar(vector, scalar);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test
	public void shouldGenerateRandomVector() throws MatrixesServiceException {
		// given
		double maxValue = 5.0;
		int dimnesion = 3;
		// when
		double[] expectedVector = { maxValue, maxValue, maxValue };
		double[] returnedVector = matrixesService.generateRandomVector(maxValue, dimnesion);
		// then
		for (int numOfEl = 0; numOfEl < dimnesion; numOfEl++) {
			assertTrue(expectedVector[numOfEl] > returnedVector[numOfEl]);
		}
	}

	@Test(expected = MatrixesServiceException.class)
	public void shouldFailByNullDimnesion() throws MatrixesServiceException {
		// given
		double maxValue = 5.0;
		int dimnesion = 0;
		// when
		matrixesService.generateRandomVector(maxValue, dimnesion);
		// then
		Assert.fail();
	}
	
	@Test
	public void shouldReturnSelectedRow() throws MatrixesServiceException {
		// given
		double[][] matrix = { { 1.0, 2.0 }, { 5.0, 6.0 }, { 9.0, 10.0 } };
		int numOfRow = 2;
		// when

		double[] expectedVector = { 9.0, 10.0 };
		double[] returnedVector = matrixesService.selectRow(matrix, numOfRow);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test(expected = MatrixesServiceException.class)
	public void shouldFailByWrongNumberOfSelectedRow() throws MatrixesServiceException {
		// given
		double[][] matrix = { { 1.0, 2.0 }, { 5.0, 6.0 }, { 9.0, 10.0 } };
		int numOfRow = 5;
		// when
		matrixesService.selectRow(matrix, numOfRow);
		// then
		Assert.fail();
	}

	@Test
	public void shouldReturnDiagonalValuesAsVector() throws MatrixesServiceException {
		// given
		double[][] matrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 }, { 7.0, 8.0, 9.0 } };
		// when

		double[] expectedVector = { 1.0, 5.0, 9.0 };
		double[] returnedVector = matrixesService.chooseDiagonalValues(matrix);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test
	public void shouldLogarithmInMatrix() throws MatrixesServiceException {
		// given
		double[][] matrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 }, { 7.0, 8.0, 9.0 } };
		// when

		double[][] expectedMatrix = { { Math.log(1.0), Math.log(2.0), Math.log(3.0) },
				{ Math.log(4.0), Math.log(5.0), Math.log(6.0) }, { Math.log(7.0), Math.log(8.0), Math.log(9.0) } };
		double[][] returnedMatrix = matrixesService.makeLogrithmInMatrix(matrix);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldLogarithmInVector() throws MatrixesServiceException {
		// given
		double[] vector = { 1.0, 2.0, 3.0 };
		// when
		double[] expectedVector = { Math.log(1.0), Math.log(2.0), Math.log(3.0) };
		double[] returnedVector = matrixesService.makeLogarithmInVector(vector);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test(expected = MatrixesServiceException.class)
	public void shouldFailByNegativeElementToLogarithm() throws MatrixesServiceException {
		// given
		double[] vector = { 1.0, 2.0, -3.0 };
		// when
		matrixesService.makeLogarithmInVector(vector);
		// then
		Assert.fail();
	}

	@Test
	public void shouldInvertElementsInVector() throws MatrixesServiceException {
		// given
		double[] vector = { 0.0, 2.0, 3.0 };
		// when
		double[] expectedVector = { 0.0, 1 / 2.0, 1 / 3.0 };
		double[] returnedVector = matrixesService.invertElementsInVector(vector);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test
	public void shouldInvertElementsInMatrix() throws MatrixesServiceException {
		// given
		double[][] matrix = { { 0.0, -2.0, 3.0 }, { 4.0, 5.0, 6.0 }, { 7.0, 8.0, 9.0 } };
		// when

		double[][] expectedMatrix = { { 0.0, 1 / -2.0, 1 / 3.0 }, { 1 / 4.0, 1 / 5.0, 1 / 6.0 },
				{ 1 / 7.0, 1 / 8.0, 1 / 9.0 } };
		double[][] returnedMatrix = matrixesService.invertElementsInMatrix(matrix);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldMatrixElementsToPower() {
		// given
		double[][] matrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 }, { 7.0, 8.0, 9.0 } };
		double power = 3.0;
		// when

		double[][] expectedMatrix = { { Math.pow(1.0, power), Math.pow(2.0, power), Math.pow(3.0, power) },
				{ Math.pow(4.0, power), Math.pow(5.0, power), Math.pow(6.0, power) },
				{ Math.pow(7.0, power), Math.pow(8.0, power), Math.pow(9.0, power) } };
		double[][] returnedMatrix = matrixesService.matrixElementsToPower(matrix, power);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldEulerNumberToPowerOfMatrixElements() {
		// given
		double[][] matrix = { { 1.0, 2.0, 3.0 }, { 4.0, 5.0, 6.0 }, { 7.0, 8.0, 9.0 } };
		// when

		double[][] expectedMatrix = { { Math.exp(1.0), Math.exp(2.0), Math.exp(3.0) },
				{ Math.exp(4.0), Math.exp(5.0), Math.exp(6.0) }, { Math.exp(7.0), Math.exp(8.0), Math.exp(9.0) } };
		double[][] returnedMatrix = matrixesService.eulerNumberToMatrixElementsPower(matrix);
		int rows = returnedMatrix.length;
		// then
		for (int numOfRow = 0; numOfRow < rows; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldMakeEyeMatrixWithCoef() throws MatrixesServiceException {
		// given
		int dimnesion = 2;
		double coef = -15.22;
		// when
		double[][] expectedMatrix = { { coef, 0 }, { 0, coef } };
		double[][] returnedMatrix = matrixesService.eyeWithCoef(dimnesion, coef);
		// then
		for (int numOfRow = 0; numOfRow < dimnesion; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test
	public void shouldDuplicatedVectorIntoMatrix() throws MatrixesServiceException {
		// given
		double[] vector = { 0.0, 2.0, 3.0 };
		int times = 2;
		// when
		double[][] expectedMatrix = { { 0.0, 2.0, 3.0 }, { 0.0, 2.0, 3.0 } };
		double[][] returnedMatrix = matrixesService.duplicatedVectorsIntoMatrix(vector, times);
		// then
		for (int numOfRow = 0; numOfRow < times; numOfRow++) {
			Assert.assertArrayEquals(expectedMatrix[numOfRow], returnedMatrix[numOfRow], 0.0);
		}
	}

	@Test(expected = MatrixesServiceException.class)
	public void shouldFailByDuplicatingTimesLowerThanOnes() throws MatrixesServiceException {
		// given
		double[] vector = { 0.0, 2.0, 3.0 };
		int times = 0;
		// when
		matrixesService.duplicatedVectorsIntoMatrix(vector, times);
		// then
		Assert.fail();
	}

	@Test
	public void shouldSearchMinimumValuesInVectors() throws MatrixesServiceException {
		// given
		double[] primaryVector = { 3.0, 4.0, 7.0 };
		double[] secondaryVector = { 2.0, -3.0, 8.0 };
		// when
		double[] expectedVector = { 2.0, -3.0, 7.0 };
		double[] returnedVector = matrixesService.minimumValuesFromVectors(primaryVector, secondaryVector);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test
	public void shouldReturnEuclideanDistancesVector() throws MatrixesServiceException {
		// given
		double[] primaryVector = { 3.0, 3.0 };
		double[][] matrix = { { 0.0, 1.0 }, { 1.0, 1.0 } };
		// when
		double[] expectedVector = { 13.0, 8.0 };
		double[] energiesOfRowsOfMatrix = matrixesService.vectorOfSquaresNormOfRowsFromMatrix(matrix);
		double[] returnedVector = matrixesService.euclideanDistancesBetweenVectorAndMatrix(primaryVector, matrix,
				energiesOfRowsOfMatrix);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test
	public void shouldReturnEuclideanDistancesMatrix() throws MatrixesServiceException {
		// given
		double[][] primaryMatrix = { { 3.0, 1.0 }, { 4.0, 1.0 } };
		double[][] secondaryMatrix = { { 1.0, 1.0 }, { 4.0, 1.0 } };
		// when
		double[][] expectedVector = { { 4.0, -14.0 }, { 24.0, 0.0 } };
		double[] energiesOfRowsOfSecondaryMatrix = matrixesService.vectorOfSquaresNormOfRowsFromMatrix(secondaryMatrix);
		double[][] returnedVector = matrixesService.euclideanDistancesBetweenMatrixes(primaryMatrix, secondaryMatrix,
				energiesOfRowsOfSecondaryMatrix);
		// then
		Assert.assertArrayEquals(expectedVector[0], returnedVector[0], 0.0);
		Assert.assertArrayEquals(expectedVector[1], returnedVector[1], 0.0);
	}

	@Test
	public void shouldReturnSumsOfElementsInRows() {
		// given
		double[][] matrix = { { 0.0, 5.0 }, { 1.0, 1.0 } };
		// when
		double[] expectedVector = { 5.0, 2.0 };
		double[] returnedVector = matrixesService.sumsOfElementsInRows(matrix);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test
	public void shouldReturnSumsOfElementsInCols() {
		// given
		double[][] matrix = { { 0.0, 5.0 }, { 1.0, 1.0 } };
		// when
		double[] expectedVector = { 1.0, 6.0 };
		double[] returnedVector = matrixesService.sumsOfElementsInCols(matrix);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test
	public void shouldReturnMaxValuesInCol() {
		// given
		double[][] matrix = { { 0.0, 5.0 }, { 1.0, 1.0 } };
		// when
		double[] expectedVector = { 1.0, 5.0 };
		double[] returnedVector = matrixesService.maxInCol(matrix);
		// then
		Assert.assertArrayEquals(expectedVector, returnedVector, 0.0);
	}

	@Test
	public void shouldReturnCovarianceMatrix() throws StatisticsServiceException, MatrixesServiceException {
		// given
		double[][] matrix = { { 9.0, 8.0, 4.0 }, { 9.0, 6.0, 8.0 } };
		// when
		double[][] expectedMatrix = { { 7.0, 0.0 }, { 0.0, 7.0 / 3.0 } };
		double[][] returnedMatrix = matrixesService.cov(matrix);
		// then
		Assert.assertArrayEquals(expectedMatrix[0], returnedMatrix[0], 0.001);
		Assert.assertArrayEquals(expectedMatrix[1], returnedMatrix[1], 0.001);
	}
}