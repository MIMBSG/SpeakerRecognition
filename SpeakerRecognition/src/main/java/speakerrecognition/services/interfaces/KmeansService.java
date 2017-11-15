package speakerrecognition.services.interfaces;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.KmeansResult;

public interface KmeansService {

	KmeansResult fit(double[][] observations, int numOfComponents)
			throws StatisticsServiceException, MatrixesServiceException;

}
