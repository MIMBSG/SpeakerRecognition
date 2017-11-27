package speakerrecognition.services.interfaces;

import speakerrecognition.entities.UserEntity;
import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.MfccServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;

public interface SpeakerModelService {

	UserEntity creatorSpeakerModel(double[] samples, String name, String lastName)
			throws MfccServiceException, MatrixesServiceException, StatisticsServiceException;
}
