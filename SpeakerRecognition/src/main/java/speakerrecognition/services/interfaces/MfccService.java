package speakerrecognition.services.interfaces;

import speakerrecognition.pojos.MfccParameters;

public interface MfccService {

	MfccParameters transformToMatrix(double[] mfccVec);
}
