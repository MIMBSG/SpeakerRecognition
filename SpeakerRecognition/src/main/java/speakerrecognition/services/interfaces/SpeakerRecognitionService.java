package speakerrecognition.services.interfaces;

import java.util.List;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.SpeakerResponse;

public interface SpeakerRecognitionService {

	List<SpeakerResponse> recognizing(int frequency, double[] mfccVec)
			throws MatrixesServiceException, StatisticsServiceException;
}
