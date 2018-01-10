package speakerrecognition.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.pojos.ScoreSamples;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ScoreSamplesServiceImplTest {

	@Mock
	private MatrixesService matrixesService;
	@Mock
	private LogProbabilityCalculatorServiceImpl lprService;

	@InjectMocks
	private ScoreSamplesServiceImpl scoreSamplesServiceImpl;

	@Test
	public void shouldBeSuccess() throws MatrixesServiceException {
		// when
		double[][] mfcc = { { 0, 1, 2 }, { 3, 4, 5 } };
		double[][] means = new double[2][2];
		double[][] covars = new double[2][2];
		double[] weights = new double[2];
		int numberOfComponents = 32;
		double[][] logProbabilities = new double[2][2];
		double[] logProb = new double[2];
		double[][] responsibilities = new double[2][2];

		Mockito.when(lprService.logMultivariateNormalDistribution(Mockito.any(double[][].class),
				Mockito.any(double[][].class), Mockito.eq(covars))).thenReturn(logProbabilities);
		Mockito.when(matrixesService.makeLogarithmInVector(Mockito.any(double[].class))).thenReturn(weights);
		Mockito.when(matrixesService.matrixAddVector(Mockito.any(double[][].class), Mockito.any(double[].class)))
				.thenReturn(logProbabilities);
		Mockito.when(matrixesService.logSumExp(Mockito.any(double[][].class))).thenReturn(logProb);
		Mockito.when(matrixesService.matrixSubstractVector(Mockito.any(double[][].class), Mockito.any(double[].class)))
				.thenReturn(logProbabilities);
		Mockito.when(matrixesService.eulerNumberToMatrixElementsPower(Mockito.any(double[][].class)))
				.thenReturn(responsibilities);

		ScoreSamples expectedScoreSamples = new ScoreSamples(mfcc, means, covars, weights, numberOfComponents, logProb,
				responsibilities);
		// given
		ScoreSamples scoreSamples = scoreSamplesServiceImpl.getScoreSamples(mfcc, means, covars, weights,
				numberOfComponents);
		// then
		Assert.assertEquals(expectedScoreSamples, scoreSamples);
	}

}
