package speakerrecognition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.GmmResult;
import speakerrecognition.pojos.KmeansResult;
import speakerrecognition.pojos.ScoreSamples;
import speakerrecognition.services.interfaces.GmmService;
import speakerrecognition.services.interfaces.KmeansService;
import speakerrecognition.services.interfaces.ScoreSamplesService;

@Service
public class GmmServiceImpl implements GmmService {

	@Autowired
	private MatrixesService matrixService;
	@Autowired
	private StatisticsService statService;
	@Autowired
	private KmeansService kMeansService;
	@Autowired
	private ScoreSamplesService scoreSmplService;

	private static final double EPS = 2.2204460492503131e-16;
	private static final int N_INIT = 10;
	private static final double MIN_COVAR = 0.001;
	private static final int N_ITER = 10;
	private static final double TOLERANCE = 0.001;

	@Override
	public GmmResult fit(double[][] mfcc, int numOfComponents)
			throws MatrixesServiceException, StatisticsServiceException {
		double[][] finalMeans = new double[numOfComponents][mfcc[0].length];
		double[][] finalCovars = new double[numOfComponents][mfcc[0].length];
		double[] finalWeights = new double[mfcc.length];
		double prevLogLikelihood = Double.NaN;
		double currentLogLikelihood = 0;
		double[] logLikelihoods;
		double[][] responsibilities;
		double[][] cv;
		double maxLogProb = Double.NEGATIVE_INFINITY;
		double[][] means = new double[numOfComponents][mfcc[0].length];
		double[] weights = new double[mfcc.length];
		double[][] covars = new double[numOfComponents][mfcc[0].length];

		for (int i = 0; i < N_INIT; i++) {
			KmeansResult kMeansResult = kMeansService.fit(mfcc, numOfComponents);
			means = kMeansResult.getBestClusterCenters();
			weights = matrixService.fillVectorWithScalar(weights, (double) 1 / numOfComponents);
			covars = matrixService.cov(matrixService.transposeMatrix(mfcc));
			cv = matrixService.eyeWithCoef(mfcc[0].length, MIN_COVAR);
			covars = matrixService.matrixAddMatrix(covars, cv);
			covars = matrixService.duplicatedVectorsIntoMatrix(matrixService.chooseDiagonalValues(covars),
					numOfComponents);
			for (int j = 0; j < N_ITER; j++) {
				prevLogLikelihood = currentLogLikelihood;
				ScoreSamples scoreSamples = scoreSmplService.getScoreSamples(mfcc, means, covars, weights,
						numOfComponents);
				logLikelihoods = scoreSamples.getLogprob();
				responsibilities = scoreSamples.getResponsibilities();
				currentLogLikelihood = statService.getMean(logLikelihoods);

				if (!Double.isNaN(prevLogLikelihood)) {
					double difference = Math.abs(currentLogLikelihood - prevLogLikelihood);
					if (difference < TOLERANCE) {
						break;
					}
				}

				GmmResult gmmTempResult = doMstep(mfcc, responsibilities, means, covars, weights);
				means = gmmTempResult.getMeans();
				covars = gmmTempResult.getCovars();
				weights = gmmTempResult.getWeights();
			}

			if (currentLogLikelihood > maxLogProb) {
				maxLogProb = currentLogLikelihood;
				finalMeans = means;
				finalCovars = covars;
				finalWeights = weights;
			}

		}
		return new GmmResult(finalMeans, finalCovars, finalWeights);
	}

	GmmResult doMstep(double[][] data, double[][] responsibilities, double[][] means, double[][] covars,
			double[] weights) throws MatrixesServiceException {
		double[] tempWeights = matrixService.sumsOfElementsInCols(responsibilities);
		double[][] weightedXSum = matrixService.matrixMultiplyByMatrix(matrixService.transposeMatrix(responsibilities),
				data);
		double[] inverse_weights = matrixService
				.invertElementsInVector(matrixService.vectorAddScalar(tempWeights, 10 * EPS));
		weights = matrixService.vectorAddScalar(matrixService.vectorMultiplyByScalar(tempWeights,
				1.0 / (matrixService.sumOfVectorElements(tempWeights) + 10 * EPS)), EPS);
		means = matrixService.matrixMultiplyByVectorElByEl(weightedXSum, inverse_weights);
		covars = covarMstepDiag(means, data, responsibilities, weightedXSum, inverse_weights, MIN_COVAR);

		return new GmmResult(means, covars, weights);
	}

	double[][] covarMstepDiag(double[][] means, double[][] X, double[][] responsibilities, double[][] weightedXSum,
			double[] norm, double minCovar) throws MatrixesServiceException {
		double[][] covarDiagToReturn = null;
		double[][] avgX2 = matrixService.matrixMultiplyByVectorElByEl(
				matrixService.matrixMultiplyByMatrix(matrixService.transposeMatrix(responsibilities),
						matrixService.multiplyMatrixesElementByElement(X, X)),
				norm);
		double[][] avgMeans2 = matrixService.matrixElementsToPower(means, 2);
		double[][] avgXMeans = matrixService.matrixMultiplyByVectorElByEl(
				matrixService.multiplyMatrixesElementByElement(means, weightedXSum), norm);
		covarDiagToReturn = matrixService.matrixAddScalar(matrixService.matrixAddMatrix(
				matrixService.matrixSubstractMatrix(avgX2, matrixService.matrixMultiplyByScalar(avgXMeans, 2)),
				avgMeans2), minCovar);
		return covarDiagToReturn;
	}
}
