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
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.SpeakerModel;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpeakerSimilarytyCalculatorServiceImplTest {

	@Mock
	private MatrixesService matrixesService;
	@Mock
	private StatisticsService statisticsService;
	@Mock
	private LogProbabilityCalculatorServiceImpl lprService;

	@InjectMocks
	private SpeakersSimilarityCalculatorServiceImpl speakerSimilarytyCalculatorServiceImpl;

	@Test
	public void dummyTest() throws MatrixesServiceException, StatisticsServiceException {
		// given
		double[][] mfcc = { { 0, 1, 2 }, { 3, 4, 5 } };
		double[][] means = new double[2][2];
		double[][] covars = new double[2][2];
		double[] weights = new double[2];
		SpeakerModel speakerModel = new SpeakerModel(means, covars, weights, "", "");

		double[][] logProbabilities = new double[2][2];
		double[] logProb = new double[2];

		double expected = 500;

		Mockito.when(lprService.logMultivariateNormalDistribution(Mockito.any(double[][].class),
				Mockito.any(double[][].class), Mockito.eq(covars))).thenReturn(logProbabilities);
		Mockito.doReturn(weights).when(matrixesService).makeLogarithmInVector(Mockito.any(double[].class));
		Mockito.when(matrixesService.matrixAddVector(Mockito.any(double[][].class), Mockito.any(double[].class)))
				.thenReturn(logProbabilities);
		Mockito.when(matrixesService.logSumExp(Mockito.any(double[][].class))).thenReturn(logProb);
		Mockito.when(statisticsService.getMean(Mockito.eq(logProb))).thenReturn(expected);

		// when
		double score = speakerSimilarytyCalculatorServiceImpl.getScore(mfcc, speakerModel);

		/// then
		Assert.assertEquals(expected, score, 0.1);
		Mockito.verify(lprService, Mockito.times(1)).logMultivariateNormalDistribution(Mockito.any(double[][].class),
				Mockito.any(double[][].class), Mockito.eq(covars));

	}

}
