package speakerrecognition.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import speakerrecognition.dao.UserDao;
import speakerrecognition.entities.CovarEntity;
import speakerrecognition.entities.MeanEntity;
import speakerrecognition.entities.UserEntity;
import speakerrecognition.entities.WeightEntity;
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

	private static final int FREQUENCY = 44100;
	private static final int NUM_OF_MIXTURES = 8;

	@Override
	public UserEntity creatorSpeakerModel(double[] samples, String name, String lastName)
			throws MfccServiceException, MatrixesServiceException, StatisticsServiceException {

		MfccParameters mfcc = mfccService.extractMfcc(samples, FREQUENCY);
		GmmResult gmm = gmmService.fit(mfcc.getMfccCoefs(), NUM_OF_MIXTURES);
		Set<WeightEntity> weightEntities = matrixAssemblerService.createWeightEntity(gmm.getWeights());
		Set<CovarEntity> covarEntities = matrixAssemblerService.createCovarEntity(gmm.getCovars());
		Set<MeanEntity> meanEntities = matrixAssemblerService.createMeanEntity(gmm.getMeans());
		UserEntity userEntity = new UserEntity(meanEntities, covarEntities, weightEntities, name, lastName);
		userDao.save(userEntity);
		return userDao.getById(userEntity.getId());
	}

}
