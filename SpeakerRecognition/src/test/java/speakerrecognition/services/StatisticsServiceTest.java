package speakerrecognition.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import speakerrecognition.exceptions.StatisticsServiceException;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StatisticsServiceTest {
	
	@Autowired
	private StatisticsService statserv;

	@Test
	public void SuccessGetMean() throws StatisticsServiceException {
		// given
		double[] data = { 3.0, 2.0, 3.0, 4.0 };
		// when
		double expectedAvg = 3.0;
		double returnedAvg = statserv.getMean(data);
		// then
		Assert.assertEquals(expectedAvg, returnedAvg, 0.0);
	}

	@Test
	public void SuccessGetMean2() throws StatisticsServiceException {
		// given
		double[][] data = { { 3.0, 2.0, 3.0, 4.0 }, { 30.0, 20.0, 30.0, 40.0 }, { 1.5, 2.0, 2.5, 4.0 } };
		// when
		double[] expectedAvg = { 11.5, 8.0, 11.833333, 16.0 };
		double[] returnedAvg = statserv.getMean2(data);
		// then
		Assert.assertArrayEquals(expectedAvg, returnedAvg, 0.0001);
	}

	@Test(expected = StatisticsServiceException.class)
	public void WrongMatrixSizeGetMean2() throws StatisticsServiceException {
		// given
		double[][] data = { { 3.0, 2.0, 3.0 }, { 30.0, 20.0, 30.0, 40.0 }, { 1.5, 2.0, 2.5, 4.0 } };
		// when
		double[] expectedAvg = { 11.5, 8.0, 11.83333, 16.0 };
		double[] returnedAvg = statserv.getMean2(data);
		// then
		Assert.assertArrayEquals(expectedAvg, returnedAvg, 0.00001);
	}

	@Test
	public void SuccessGetVariance() throws StatisticsServiceException {
		// given
		double[] data = { 3.0, 2.0, 3.0, 4.0 };
		// when
		double expectedVar = 0.666666667;
		double returnedVar = statserv.getVariance(data);
		// then
		Assert.assertEquals(expectedVar, returnedVar, 0.0000001);
	}

	@Test
	public void SuccessGetVariance2() throws StatisticsServiceException {
		// given
		double[][] data = { { 3.0, 2.0, 3.0, 4.0 }, { 30.0, 20.0, 30.0, 4.0 }, { 1.5, 2.0, 2.5, 4.0 } };
		// when
		double[] expectedVar = { 171.5, 72.0, 165.05554, 0.0 };
		double[] returnedVar = statserv.getVariance2(data);
		// then
		Assert.assertArrayEquals(expectedVar, returnedVar, 0.01);
		// Assert.assertEquals(expectedVar[0], returnedVar[0], 0.000001);
		// Assert.assertEquals(expectedVar[1], returnedVar[1], 0.000001);
		// Assert.assertEquals(expectedVar[2], returnedVar[2], 0.000001);
	}

	@Test(expected = StatisticsServiceException.class)
	public void WrongMatrixSizeGetVariance2() throws StatisticsServiceException {
		// given
		double[][] data = { { 3.0, 2.0, 3.0 }, { 30.0, 20.0, 30.0, 40.0 }, { 1.5, 2.0, 2.5, 4.0 } };
		// when
		double[] expectedAvg = { 0.6666667, 66.6666667, 1.166667 };
		double[] returnedAvg = statserv.getVariance2(data);
		// then
		Assert.assertArrayEquals(expectedAvg, returnedAvg, 0.0001);
	}

	@Test(expected = StatisticsServiceException.class)
	public void WrongMatrixSizeGetStdDev2() throws StatisticsServiceException {
		// given
		double[][] data = { { 3.0, 2.0, 3.0 }, { 30.0, 20.0, 30.0, 40.0 }, { 1.5, 2.0, 2.5, 4.0 } };
		// when
		double[] expectedStdDev = { 3.0, 30.0, 2.5 };
		double[] returnedStdDev = statserv.getMean2(data);
		// then
		Assert.assertArrayEquals(expectedStdDev, returnedStdDev, 0.0);
	}
}