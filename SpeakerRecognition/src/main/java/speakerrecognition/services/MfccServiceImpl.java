package speakerrecognition.services;

import org.springframework.stereotype.Service;

import speakerrecognition.pojos.MfccParameters;
import speakerrecognition.services.interfaces.MfccService;

@Service
public class MfccServiceImpl implements MfccService {

	private static final int MFCC_NUM = 13;
	
	@Override
	public MfccParameters transformToMatrix(double[] mfccVec) {
		
		int mfccRows = mfccVec.length/MFCC_NUM;
		double[][] mfcc = new double[mfccRows][MFCC_NUM];
		int row = 0;
		int col = 0;
		for(int el=0; el < mfccVec.length; el++){
			mfcc[row][col] = mfccVec[el];
			col++;
			if(col%13==0){
				row++;
				col = 0;
			}
		}
		return new MfccParameters(mfcc);
	}
}
