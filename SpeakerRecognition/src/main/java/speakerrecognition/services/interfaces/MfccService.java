package speakerrecognition.services.interfaces;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.MfccServiceException;
import speakerrecognition.pojos.MfccParameters;

public interface MfccService {

	MfccParameters extractMfcc(double[] samplesVector, int fs) throws MfccServiceException, MatrixesServiceException;

	double[] arrange(int lowLimit, int highLimit) throws MfccServiceException;

	double energyOfVector(double[] vector);

	double[] preemphasis(double[] vector, double preEmph);

	double[][] dctMatrix(int size, int mfccNum) throws MatrixesServiceException;

	double[][] melFilterBank(int numOfFilterBanks, int fftLength, int sampleRate) throws MfccServiceException;

}
