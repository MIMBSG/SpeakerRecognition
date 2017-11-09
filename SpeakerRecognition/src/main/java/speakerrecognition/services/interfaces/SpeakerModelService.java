package speakerrecognition.services.interfaces;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.SpeakerModel;

public interface SpeakerModelService {

	double getScore(double[][] mfcc, SpeakerModel speakerModel)
			throws StatisticsServiceException, MatrixesServiceException;
}
