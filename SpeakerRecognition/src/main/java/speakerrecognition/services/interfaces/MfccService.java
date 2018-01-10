package speakerrecognition.services.interfaces;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.MfccServiceException;
import speakerrecognition.pojos.MfccParameters;

public interface MfccService {

	MfccParameters extractMfcc(double[] samplesVector, int fs) throws MfccServiceException, MatrixesServiceException;
}
