package speakerrecognition.services.interfaces;

import speakerrecognition.entities.UserEntity;
import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;

public interface SpeakerModelService {

	UserEntity creatorSpeakerModel(double[] mfccVec, String name, String lastName)
			throws MatrixesServiceException, StatisticsServiceException;
}
