package speakerrecognition.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Assert;

import speakerrecognition.exceptions.MatrixesServiceException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LogProbabilityCalculatorServiceImplTest {

	@Mock
	private MatrixesService matrixesService;

	@InjectMocks
	private LogProbabilityCalculatorServiceImpl logProbabilityCalculatorServiceImpl;

	@Test
	public void shouldBeSuccess() throws MatrixesServiceException {
		// when
		double[][] mfcc = { { 0, 1, 2 }, { 3, 4, 5 } };
		double[][] means = new double[2][2];
		double[][] covars = new double[2][2];
		int rowsOfMFCC = mfcc.length;
		int rowsOfMeans = means.length;
		double[][] logProbability = new double[rowsOfMFCC][rowsOfMeans];
		double[][] logCovars = new double[2][2];
		double[] sumsOfElementsInRowFromLogCov = new double[2];
		double[][] squaredMeans = new double[2][2];
		double[][] squaredMeansDividedByCovars = new double[2][2];
		double[] sumsOfElementsInRowFromDividedByCovarsSquaredMeans = new double[2];
		double[][] transponsedMeansDividedByCovars = new double[2][2];
		double[][] mfccMultipliedByTransponsedMeansDividedByCovars = new double[2][2];
		double[][] transponsedInvertedCovars = new double[2][2];
		double[][] squaredMFCC = new double[2][2];
		double[][] squaredMFCCMultipliedByTransponsedInvertedCovars = new double[2][2];
		double[] sumsOfSumsCovsAndMeans = new double[2];
		double[][] sumOfTwoMainMatrixes = new double[2][2];

		Mockito.when(matrixesService.makeLogrithmInMatrix(Mockito.any(double[][].class))).thenReturn(logCovars);
		Mockito.when(matrixesService.sumsOfElementsInRows(Mockito.any(double[][].class)))
				.thenReturn(sumsOfElementsInRowFromLogCov);
		Mockito.when(matrixesService.matrixElementsToPower(Mockito.any(double[][].class), Mockito.anyDouble()))
				.thenReturn(squaredMeans);
		Mockito.when(
				matrixesService.matrixDividedByMatrix(Mockito.any(double[][].class), Mockito.any(double[][].class)))
				.thenReturn(squaredMeansDividedByCovars);
		Mockito.when(matrixesService.sumsOfElementsInRows(Mockito.any(double[][].class)))
				.thenReturn(sumsOfElementsInRowFromDividedByCovarsSquaredMeans);
		Mockito.when(
				matrixesService.matrixDividedByMatrix(Mockito.any(double[][].class), Mockito.any(double[][].class)))
				.thenReturn(transponsedMeansDividedByCovars);
		Mockito.when(matrixesService.transposeMatrix(Mockito.any(double[][].class)))
				.thenReturn(transponsedMeansDividedByCovars);
		Mockito.when(
				matrixesService.matrixMultiplyByMatrix(Mockito.any(double[][].class), Mockito.any(double[][].class)))
				.thenReturn(mfccMultipliedByTransponsedMeansDividedByCovars);
		Mockito.when(matrixesService.matrixMultiplyByScalar(Mockito.any(double[][].class), Mockito.anyDouble()))
				.thenReturn(mfccMultipliedByTransponsedMeansDividedByCovars);
		Mockito.when(matrixesService.invertElementsInMatrix(Mockito.any(double[][].class)))
				.thenReturn(transponsedInvertedCovars);
		Mockito.when(matrixesService.transposeMatrix(Mockito.any(double[][].class)))
				.thenReturn(transponsedInvertedCovars);
		Mockito.when(matrixesService.matrixElementsToPower(Mockito.any(double[][].class), Mockito.anyDouble()))
				.thenReturn(squaredMFCC);
		Mockito.when(
				matrixesService.matrixMultiplyByMatrix(Mockito.any(double[][].class), Mockito.any(double[][].class)))
				.thenReturn(squaredMFCCMultipliedByTransponsedInvertedCovars);
		Mockito.when(matrixesService.vectorAddScalar(Mockito.any(double[].class), Mockito.anyDouble()))
				.thenReturn(sumsOfElementsInRowFromLogCov);
		Mockito.when(matrixesService.vectorAddVector(Mockito.any(double[].class), Mockito.any(double[].class)))
				.thenReturn(sumsOfSumsCovsAndMeans);
		Mockito.when(matrixesService.matrixAddMatrix(Mockito.any(double[][].class), Mockito.any(double[][].class)))
				.thenReturn(sumOfTwoMainMatrixes);
		Mockito.when(matrixesService.matrixAddVector(Mockito.any(double[][].class), Mockito.any(double[].class)))
				.thenReturn(sumOfTwoMainMatrixes);
		Mockito.when(matrixesService.matrixMultiplyByScalar(Mockito.any(double[][].class), Mockito.anyDouble()))
				.thenReturn(logProbability);
		// given
		double[][] resultLogProbability = logProbabilityCalculatorServiceImpl.logMultivariateNormalDistribution(mfcc, means,
				covars);
		// then
		for (int i = 0; i < resultLogProbability.length; i++) {
			Assert.assertArrayEquals(logProbability[i], resultLogProbability[i], 0.1);
		}
	}
}
