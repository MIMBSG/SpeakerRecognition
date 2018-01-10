package speakerrecognition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.pojos.ScoreSamples;
import speakerrecognition.services.interfaces.LogProbabilityCalculatorService;
import speakerrecognition.services.interfaces.ScoreSamplesService;

@Service
public class ScoreSamplesServiceImpl implements ScoreSamplesService {

	@Autowired
	private MatrixesService matrix;
	@Autowired
	private LogProbabilityCalculatorService lprService;

	public ScoreSamples getScoreSamples(double[][] mfcc, double[][] means, double[][] covars, double[] weights,
			int numberOfComponents) throws MatrixesServiceException {

		double[][] lpr = lprService.logMultivariateNormalDistribution(mfcc, means, covars);
		lpr = matrix.matrixAddVector(lpr, matrix.makeLogarithmInVector(weights));
		double[] logprob = matrix.logSumExp(lpr);
		double[][] responsibilities = matrix
				.eulerNumberToMatrixElementsPower(matrix.matrixSubstractVector(lpr, logprob));
		ScoreSamples scoreSamples = new ScoreSamples(mfcc, means, covars, weights, numberOfComponents, logprob,
				responsibilities);
		return scoreSamples;
	}
}
