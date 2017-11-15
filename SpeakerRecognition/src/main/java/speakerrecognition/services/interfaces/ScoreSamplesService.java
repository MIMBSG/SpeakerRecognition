package speakerrecognition.services.interfaces;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.pojos.ScoreSamples;

public interface ScoreSamplesService {

	public ScoreSamples getScoreSamples(double[][] mfcc, double[][] means, double[][] covars, double[] weights,
			int numberOfComponents) throws MatrixesServiceException;
}
