package speakerrecognition.services;

import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.StatisticsServiceException;

/**
 * @author MB
 *
 */

@Service
public class StatisticsService {
	private static final String NULL_POINTER_ERROR = "Data cannot be null!";
	private static final String WRONG_MATRIX_SIZE_ERROR = "Row sizes do not match!";

	/**
	 * average value of vector
	 * 
	 * @param data[]
	 * @return double
	 * @throws StatisticsServiceException
	 */
	public double getMean(double[] data) throws StatisticsServiceException {
		nullPointerCheckVector(data);
		double sum = 0.0;
		for (double a : data) {
			sum += a;
		}
		return sum / (double) data.length;
	}

	/**
	 * average of matrix which consists of vectors
	 * 
	 * @param data[][]
	 * @return data[]
	 * @throws StatisticsServiceException
	 */
	public double[] getMean2(double[][] data) throws StatisticsServiceException {
		nullPointerCheckMatrix(data);
		checkMatrixRows(data);
		int numOfRows = data.length;
		int numOfCols = data[0].length;

		double sum[] = new double[numOfCols];
		for (int j = 0; j < numOfCols; j++) {
			for (int i = 0; i < numOfRows; i++) {
				sum[j] += data[i][j];
			}
			sum[j] /= numOfRows;
		}
		return sum;
	}

	/**
	 * variance of a vector
	 * 
	 * @param data[]
	 * @return data
	 * @throws StatisticsServiceException
	 */
	public double getVariance(double[] data) throws StatisticsServiceException {
		nullPointerCheckVector(data);
		double mean = getMean(data);
		double temp = 0.0;
		for (double a : data)
			temp += (mean - a) * (mean - a);
		return temp / (data.length - 1);
	}

	/**
	 * vector of variances from a matrix which consists of vectors
	 * 
	 * @param data[][]
	 * @return data[]
	 * @throws StatisticsServiceException
	 */
	public double[] getVariance2(double[][] data) throws StatisticsServiceException {
		nullPointerCheckMatrix(data);
		checkMatrixRows(data);
		int numOfRows = data.length;
		int numOfCols = data[0].length;
		double sum[] = getMean2(data);
		double[] temp = new double[numOfCols];
		for (int j = 0; j < numOfCols; j++) {
			for (int i = 0; i < numOfRows; i++) {
				temp[j] += Math.pow((data[i][j] - sum[j]), 2);
			}
			temp[j] /= (numOfCols - 1);
		}

		return temp;
	}

	private void nullPointerCheckMatrix(double[][] data) throws StatisticsServiceException {
		if (data == null) {
			throw new StatisticsServiceException(NULL_POINTER_ERROR);
		}
		if (data[0] == null) {
			throw new StatisticsServiceException(NULL_POINTER_ERROR);
		}
	}

	private void nullPointerCheckVector(double[] data) throws StatisticsServiceException {
		if (data == null) {
			throw new StatisticsServiceException(NULL_POINTER_ERROR);
		}
	}

	private void checkMatrixRows(double[][] data) throws StatisticsServiceException {
		int testLength = 0;
		for (double[] temp : data) {
			if (testLength == 0) {
				testLength = temp.length;
			} else {
				if (testLength != temp.length) {
					throw new StatisticsServiceException(WRONG_MATRIX_SIZE_ERROR);
				}
			}
		}
	}

}
