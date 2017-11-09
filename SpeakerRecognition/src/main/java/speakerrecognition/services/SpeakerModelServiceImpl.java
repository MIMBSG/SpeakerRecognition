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
	private LogMultivariateNormalDensityService lprService;

	@Override
	public double getScore(double[][] mfcc, SpeakerModel speakerModel)
			throws StatisticsServiceException, MatrixesServiceException {

		double score = 0;

		double[][] means = speakerModel.getMeans();
		double[][] covars = speakerModel.getCovars();
		double[] weights = speakerModel.getWeights();

		double[][] lpr = lprService.logMultivariateNormalDensity(mfcc, means, covars);
		weights = matrixesService.makeLogarithmInVector(weights);
		lpr = matrixesService.matrixAddVector(lpr, weights);
		double[] logProb = matrixesService.logSumExp(lpr);
		score = statisticsService.getMean(logProb);

		return score;
	}

}
