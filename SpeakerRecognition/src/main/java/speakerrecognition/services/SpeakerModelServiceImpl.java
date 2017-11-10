package speakerrecognition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.SpeakerModel;
import speakerrecognition.services.interfaces.SpeakerModelService;

@Service
public class SpeakerModelServiceImpl implements SpeakerModelService {

	@Autowired
	private MatrixesService matrixesService;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private LogProbabilityService lprService;

	@Override
	public double getScore(double[][] mfcc, SpeakerModel speakerModel)
			throws StatisticsServiceException, MatrixesServiceException {

		double score = 0;

		double[][] means = speakerModel.getMeans();
		double[][] covars = speakerModel.getCovars();
		double[] weights = speakerModel.getWeights();

		double[][] logProbabilities = lprService.logMultivariateNormalDensity(mfcc, means, covars);
		weights = matrixesService.makeLogarithmInVector(weights);
		logProbabilities = matrixesService.matrixAddVector(logProbabilities, weights);
		double[] logProb = matrixesService.logSumExp(logProbabilities);
		score = statisticsService.getMean(logProb);

		return score;
	}

}
