package speakerrecognition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.GMM;
import speakerrecognition.pojos.Kmeans;
import speakerrecognition.pojos.ScoreSamples;
import speakerrecognition.services.KmeansService;
import speakerrecognition.services.ScoreSamplesService;

@Service
public class GMMService {

	@Autowired
	private MatrixesService matrixService;
	@Autowired
	private StatisticsService statService;
	@Autowired
	private KmeansService kMeansService;
	@Autowired
	private ScoreSamplesService scoreSmplService;

	public GMM fit() throws MatrixesServiceException, StatisticsServiceException {
		GMM gmmParams = new GMM();
		double change = 0;

		double[][] cv;
		double maxLogProb = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < gmmParams.getnInit(); i++) {
			Kmeans kMeansParams = kMeansService.fit(gmmParams.getObservations(), gmmParams.getNumOfComponents());
			gmmParams.setMeans(kMeansParams.getBestClusterCenters());
			gmmParams.setWeights(matrixService.fillVectorWithScalar(gmmParams.getWeights(),
					(double) 1 / gmmParams.getNumOfComponents()));

			gmmParams.setCovars(matrixService.cov(matrixService.transposeMatrix(gmmParams.getObservations())));
			cv = matrixService.eyeWithCoef(gmmParams.getObservations()[0].length, gmmParams.getMinCovar());
			gmmParams.setCovars(matrixService.matrixAddMatrix(gmmParams.getCovars(), cv));
			gmmParams.setCovars(matrixService.duplicatedVectorsIntoMatrix(
					matrixService.chooseDiagonalValues(gmmParams.getCovars()), gmmParams.getNumOfComponents()));//

			for (int j = 0; j < gmmParams.getnIter(); j++) {
				gmmParams.setPrevLogLikelihood(gmmParams.getCurrentLogLikelihood());
				ScoreSamples scoreSamples = scoreSmplService.getScoreSamples(gmmParams.getObservations(),
						gmmParams.getMeans(), gmmParams.getCovars(), gmmParams.getWeights(),
						gmmParams.getNumOfComponents());

				gmmParams.setLogLikelihoods(scoreSamples.getLogprob());
				gmmParams.setResponsibilities(scoreSamples.getResponsibilities());
				gmmParams.setCurrentLogLikelihood(statService.getMean(gmmParams.getLogLikelihoods()));

				if (!Double.isNaN(gmmParams.getPrevLogLikelihood())) {
					change = Math.abs(gmmParams.getCurrentLogLikelihood() - gmmParams.getPrevLogLikelihood());
					if (change < gmmParams.getTol()) {
						gmmParams.setConverged(true);
						break;
					}
				}

				doMstep(gmmParams.getObservations(), gmmParams.getResponsibilities(), gmmParams);
			}

			if (gmmParams.getCurrentLogLikelihood() > maxLogProb) {
				maxLogProb = gmmParams.getCurrentLogLikelihood();
				gmmParams.setBestMeans(gmmParams.getBestMeans());
				gmmParams.setBestCovars(gmmParams.getBestCovars());
				gmmParams.setBestWeights(gmmParams.getBestWeights());
			}

		}
		return gmmParams;
	}

	private void doMstep(double[][] data, double[][] responsibilities, GMM gmmParams) throws MatrixesServiceException {
		double[] weights = matrixService.sumsOfElementsInCols(responsibilities); // sumsOfElementsInCols/Rows
		// ?
		double[][] weightedXSum = matrixService.matrixMultiplyByMatrix(matrixService.transposeMatrix(responsibilities),
				data);
		double[] inverse_weights = matrixService
				.invertElementsInVector(matrixService.vectorAddScalar(weights, 10 * gmmParams.getEPS()));
		gmmParams.setWeights(matrixService.vectorAddScalar(
				matrixService.vectorMultiplyByScalar(weights,
						1.0 / (matrixService.sumOfVectorElements(weights) + 10 * gmmParams.getEPS())),
				gmmParams.getEPS()));
		gmmParams.setMeans(matrixService.matrixMultiplyByVectorElByEl(weightedXSum, inverse_weights));
		gmmParams.setCovars(covarMstepDiag(gmmParams.getMeans(), data, responsibilities, weightedXSum, inverse_weights,
				gmmParams.getMinCovar()));
	}

	private double[][] covarMstepDiag(double[][] means, double[][] X, double[][] responsibilities,
			double[][] weightedXSum, double[] norm, double minCovar) throws MatrixesServiceException {
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
