package speakerrecognition.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.dao.UserDao;
import speakerrecognition.entities.UserEntity;
import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.MfccParameters;
import speakerrecognition.pojos.SpeakerModel;
import speakerrecognition.pojos.SpeakerResponse;
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
	
	private static final double THRESHOLD = -35.3;
	
	@Override
	public List<SpeakerResponse> recognizing(double[] mfccVec)
			throws MatrixesServiceException, StatisticsServiceException {

		MfccParameters mfcc = mfccService.transformToMatrix(mfccVec);
		List<UserEntity> users = userDao.findAll();
		double score = THRESHOLD;
		List<SpeakerResponse> speakers = new ArrayList<SpeakerResponse>();
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
			}
			speakers.add(new SpeakerResponse(user.getId(),name,lastName,tempScore));
		}
		if(score == THRESHOLD)
			speakers.add(new SpeakerResponse(-1,"NotFound","NotFound",10.0));
		
		speakers = speakers.stream().sorted((object1,object2) -> object1.getScore().compareTo(object2.getScore())).collect(Collectors.toList());
		Collections.reverse(speakers);
		
		if(speakers.size()>4)
			speakers = speakers.subList(0, 4);
		
		return speakers;
	}
}