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

	public void fit() throws MatrixesServiceException, StatisticsServiceException {
		GMM gmmParams = new GMM();
		Kmeans kMeansParams = new Kmeans();
		double change = 0;

		double[][] cv = new double[gmmParams.getNumOfCols()][gmmParams.getNumOfRows()];
		double maxLogProb = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < gmmParams.getnInit(); i++) {
			kMeansService.fit(kMeansParams);
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

				gmmParams.doMstep(gmmParams.getObservations(), gmmParams.getResponsibilities());
			}

			if (gmmParams.getCurrentLogLikelihood() > maxLogProb) {
				maxLogProb = gmmParams.getCurrentLogLikelihood();
				gmmParams.setBestMeans(gmmParams.getBestMeans());
				gmmParams.setBestCovars(gmmParams.getBestCovars());
				gmmParams.setBestWeights(gmmParams.getBestWeights());
			}

		}
	}
}
