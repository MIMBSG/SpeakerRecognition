package speakerrecognition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.services.interfaces.LogProbabilityCalculatorService;

@Service
public class LogProbabilityCalculatorServiceImpl implements LogProbabilityCalculatorService {

	@Autowired
	private MatrixesService matrixesService;

	@Override
	public double[][] logMultivariateNormalDistribution(double[][] mfcc, double[][] means, double[][] covars)
			throws MatrixesServiceException {

		int rowsOfMFCC = mfcc.length;
		int rowsOfMeans = means.length;
		double[][] logProbability = new double[rowsOfMFCC][rowsOfMeans];
		int colsOfMFCC = mfcc[0].length;

		double[][] logCovars = matrixesService.makeLogrithmInMatrix(covars);
		double[] sumsOfElementsInRowFromLogCov = matrixesService.sumsOfElementsInRows(logCovars);

		double[][] squaredMeans = matrixesService.matrixElementsToPower(means, 2);
		double[][] squaredMeansDividedByCovars = matrixesService.matrixDividedByMatrix(squaredMeans, covars);
		double[] sumsOfElementsInRowFromDividedByCovarsSquaredMeans = matrixesService
				.sumsOfElementsInRows(squaredMeansDividedByCovars);

		double[][] transponsedMeansDividedByCovars = matrixesService
				.transposeMatrix(matrixesService.matrixDividedByMatrix(means, covars));
		double[][] mfccMultipliedByTransponsedMeansDividedByCovars = matrixesService.matrixMultiplyByMatrix(mfcc,
				transponsedMeansDividedByCovars);
		mfccMultipliedByTransponsedMeansDividedByCovars = matrixesService
				.matrixMultiplyByScalar(mfccMultipliedByTransponsedMeansDividedByCovars, -2);

		double[][] transponsedInvertedCovars = matrixesService
				.transposeMatrix(matrixesService.invertElementsInMatrix(covars));
		double[][] squaredMFCC = matrixesService.matrixElementsToPower(mfcc, 2);
		double[][] squaredMFCCMultipliedByTransponsedInvertedCovars = matrixesService
				.matrixMultiplyByMatrix(squaredMFCC, transponsedInvertedCovars);

		sumsOfElementsInRowFromLogCov = matrixesService.vectorAddScalar(sumsOfElementsInRowFromLogCov,
				colsOfMFCC * Math.log(2 * Math.PI));
		double[] sumsOfSumsCovsAndMeans = matrixesService
				.vectorAddVector(sumsOfElementsInRowFromDividedByCovarsSquaredMeans, sumsOfElementsInRowFromLogCov);

		double[][] sumOfTwoMainMatrixes = matrixesService.matrixAddMatrix(
				squaredMFCCMultipliedByTransponsedInvertedCovars, mfccMultipliedByTransponsedMeansDividedByCovars);
		sumOfTwoMainMatrixes = matrixesService.matrixAddVector(sumOfTwoMainMatrixes, sumsOfSumsCovsAndMeans);
		logProbability = matrixesService.matrixMultiplyByScalar(sumOfTwoMainMatrixes, -0.5);

		return logProbability;
	}

}
