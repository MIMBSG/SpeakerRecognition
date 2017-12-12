package speakerrecognition.services.interfaces;

import speakerrecognition.entities.UserEntity;
import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.MfccServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;

public interface SpeakerRecognitionService {

	UserEntity recognizing(int frequency, double[] samples)
			throws MfccServiceException, MatrixesServiceException, StatisticsServiceException;
}
