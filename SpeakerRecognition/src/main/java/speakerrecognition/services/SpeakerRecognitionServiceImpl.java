package speakerrecognition.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.dao.UserDao;
import speakerrecognition.entities.UserEntity;
import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.MfccServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.MfccParameters;
import speakerrecognition.pojos.SpeakerModel;
import speakerrecognition.services.interfaces.MatrixAssemblerService;
import speakerrecognition.services.interfaces.MfccService;
import speakerrecognition.services.interfaces.SpeakerRecognitionService;
import speakerrecognition.services.interfaces.SpeakersSimilarytyCalculatorService;

@Service
public class SpeakerRecognitionServiceImpl implements SpeakerRecognitionService {

	@Autowired
	MfccService mfccService;
	@Autowired
	SpeakersSimilarytyCalculatorService speakersSimilarytyCalculatorService;
	@Autowired
	MatrixAssemblerService matrixAssemblerService;
	@Autowired
	UserDao userDao;

	@Override
	public UserEntity recognizing(int frequency, double[] samples)
			throws MfccServiceException, MatrixesServiceException, StatisticsServiceException {

		MfccParameters mfcc = mfccService.extractMfcc(samples, frequency);
		List<UserEntity> users = userDao.findAll();
		double score = Double.NEGATIVE_INFINITY;
		UserEntity winner = new UserEntity();
		for (UserEntity user : users) {
			double[][] covars = matrixAssemblerService.createCovarMatrix(user.getCovars());
			double[] weights = matrixAssemblerService.createWeightVec(user.getWeights());
			double[][] means = matrixAssemblerService.createMeanMatrix(user.getMeans());
			String name = user.getName();
			String lastName = user.getLastName();
			SpeakerModel speaker = new SpeakerModel(means, covars, weights, name, lastName);
			double tempScore = speakersSimilarytyCalculatorService.getScore(mfcc.getMfccCoefs(), speaker);
			if (tempScore > score) {
				score = tempScore;
				winner = user;
			}
		}
		return winner;
	}
}