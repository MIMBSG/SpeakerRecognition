package speakerrecognition.services.interfaces;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.GmmResult;

public interface GmmService {

	GmmResult fit(double[][] mfcc, int numOfComponents) throws MatrixesServiceException, StatisticsServiceException;

}
