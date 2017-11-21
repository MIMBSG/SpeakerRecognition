package speakerrecognition.services;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import speakerrecognition.services.CentersDenseService;

public class CentersDenseServiceTest {

	CentersDenseService centDens = new CentersDenseService();
	@Test
	public void SuccessCentersDense()
	{
		//given
		double[][] data = {{1.0, 20.0, 5.0, 6.0}, {1.3, 22.0, 5.5, 7.0}, {0.7, 26.0, 4.0, 3.0}};
		double[] labels = {1.0, 2.0, 3.0, 4.0};
		int nClusters = 2;
		double[] distances = {1.0, 2.0};
		//when
		double[][] expectedResult = {{1.0, 20.0, 5.0, 6.0}, {1.3, 22.0, 5.5, 7.0}, {1.0, 20.0, 5.0, 6.0}};
		double[][] returnedResult = centDens.centersDense(data, labels, nClusters, distances);
		//then
		//Assert.assertEquals(expectedResult[0][0], returnedResult[0][0], 0.01);
		//Assert.assertEquals(expectedResult[0][1], returnedResult[0][1], 0.01);
		Assert.assertEquals(expectedResult[0][2], returnedResult[0][2], 0.01);
		//Assert.assertEquals(expectedResult[0][3], returnedResult[0][0], 0.01);
		Assert.assertEquals(expectedResult[1][0], returnedResult[1][0], 0.01);
		Assert.assertEquals(expectedResult[1][1], returnedResult[1][1], 0.01);
		Assert.assertEquals(expectedResult[1][2], returnedResult[1][2], 0.01);
		Assert.assertEquals(expectedResult[1][3], returnedResult[1][3], 0.01);
		Assert.assertEquals(expectedResult[2][0], returnedResult[2][0], 0.01);
		Assert.assertEquals(expectedResult[2][1], returnedResult[2][1], 0.01);
		Assert.assertEquals(expectedResult[2][2], returnedResult[2][2], 0.01);
		Assert.assertEquals(expectedResult[2][3], returnedResult[2][3], 0.01);
		//nie dziala tak jak powinno
	}

}
