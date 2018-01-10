package speakerrecognition.services.interfaces;

import speakerrecognition.exceptions.MatrixesServiceException;

public interface LogProbabilityCalculatorService {

	public double[][] logMultivariateNormalDistribution(double[][] mfcc, double[][] means, double[][] covars)
			throws MatrixesServiceException;
}
