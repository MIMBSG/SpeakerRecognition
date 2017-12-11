package speakerrecognition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import speakerrecognition.dao.UserDao;
import speakerrecognition.entities.UserEntity;
import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.MfccServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.GmmResult;
import speakerrecognition.pojos.MfccParameters;
import speakerrecognition.services.interfaces.GmmService;
import speakerrecognition.services.interfaces.MatrixAssemblerService;
import speakerrecognition.services.interfaces.MfccService;
import speakerrecognition.services.interfaces.SpeakerModelService;

@Service
@Transactional
public class SpeakerModelServiceImpl implements SpeakerModelService {

	@Autowired
	MfccService mfccService;
	@Autowired
	GmmService gmmService;
	@Autowired
	MatrixAssemblerService matrixAssemblerService;
	@Autowired
	UserDao userDao;

	private static final int FREQUENCY = 16000;//44100
	private static final int NUM_OF_MIXTURES = 32;//8

	@Override
	public UserEntity creatorSpeakerModel(double[] samples, String name, String lastName)
			throws MfccServiceException, MatrixesServiceException, StatisticsServiceException {

		MfccParameters mfcc = mfccService.extractMfcc(samples, FREQUENCY);
		GmmResult gmm = gmmService.fit(mfcc.getMfccCoefs(), NUM_OF_MIXTURES);
		UserEntity userEntity = new UserEntity(name, lastName);
		matrixAssemblerService.createWeightEntity(gmm.getWeights(), userEntity);
		matrixAssemblerService.createCovarEntity(gmm.getCovars(), userEntity);
		matrixAssemblerService.createMeanEntity(gmm.getMeans(), userEntity);
		userDao.save(userEntity);
		return userDao.getById(userEntity.getId());
	}
}
