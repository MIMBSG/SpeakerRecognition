package speakerrecognition.services;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.MfccServiceException;
import speakerrecognition.pojos.MfccParameters;
import speakerrecognition.services.interfaces.MfccService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MfccServiceTest {

	@Mock
	private MatrixesService matrixService;

	@InjectMocks
	private MfccServiceImpl mfccServiceImpl;

	@Test
	public void arrangeTestSuccess() throws MfccServiceException {
		// given
		int lowLimit = 1;
		int highLimit = 9;
		double[] expectedResult = { 1, 2, 3, 4, 5, 6, 7, 8 };
		// when
		double result[] = mfccServiceImpl.arrange(lowLimit, highLimit);
		// then
		Assert.assertArrayEquals(expectedResult, result, 0.000001);
	}

	@Test(expected = MfccServiceException.class)
	public void arrangeTestFailWrongLimits() throws MfccServiceException {
		// given
		int lowLimit = 8;
		int highLimit = 1;
		double[] expectedResult = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0 };
		// when
		double result[] = mfccServiceImpl.arrange(lowLimit, highLimit);
		// then
		Assert.assertArrayEquals(expectedResult, result, 0.000001);
	}

	@Test
	public void energyOfVectorTest() throws MfccServiceException {
		// given
		double[] vector = { 1.0, 2.0, 3.0, 4.0 };
		double expectedEnergy = 30.0;
		// when
		double energy = mfccServiceImpl.energyOfVector(vector);
		// then
		Assert.assertEquals(expectedEnergy, energy, 0.000001);
	}

	@Test
	public void preemphasisTest() throws MfccServiceException {
		// given
		double[] vector = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0 };
		double preEmph = 0.5;
		double[] expectedResult = { 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0 };
		// when
		double[] result = mfccServiceImpl.preemphasis(vector, preEmph);
		// then
		Assert.assertArrayEquals(expectedResult, result, 0.000001);
	}

	@Test
	public void dctMatrixTest() throws MfccServiceException, MatrixesServiceException {
		// given
		int size = 2;
		int mfccNum = 2;
		double[][] d1Matrix = new double[2][2];

		Mockito.when(matrixService.multiplyMatrixesElementByElement(Mockito.any(double[][].class),
				Mockito.any(double[][].class))).thenReturn(d1Matrix);
		// double expectedResult[][] = { { 0.3821, -0.2943}, { -0.2943, -0.4622
		// } };
		double expectedResult[][] = { { 0.7071, 0.7071 }, { 1, 1 } };
		// when
		double result[][] = mfccServiceImpl.dctMatrix(size, mfccNum);
		// then
		Assert.assertArrayEquals(expectedResult[0], result[0], 0.001);
		Assert.assertArrayEquals(expectedResult[1], result[1], 0.001);
	}

	@Test
	public void melFilterBankTest() throws MfccServiceException, MatrixesServiceException {
		// given
		int numOfFilterBanks = 2;
		int fftLength = 2;
		int sampleRate = 2;
		double[] tempRow = new double[2];

		Mockito.when(matrixService.vectorMultiplyByScalar(Mockito.any(double[].class), Mockito.any(double.class)))
				.thenReturn(tempRow);

		double[][] expectedResult = { { 0.0, 0.0 }, { 0.0, 1.0 } };
		// when
		double result[][] = mfccServiceImpl.melFilterBank(numOfFilterBanks, fftLength, sampleRate);
		// then
		Assert.assertArrayEquals(expectedResult[0], result[0], 0.001);
		Assert.assertArrayEquals(expectedResult[1], result[1], 0.001);
	}

	@Test
	public void hammingTest() throws MfccServiceException, MatrixesServiceException {
		// given
		int frameLen = 4;
		double[] expectedResult = {0.21473,0.8652,0.8652,0.21473};
		// when
		double[] result = mfccServiceImpl.hamming(frameLen);
		// then
		Assert.assertArrayEquals(expectedResult, result, 0.001);
	}

}
