package speakerrecognition.services.rests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.MfccServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.services.TOs.UserTO;
import speakerrecognition.services.interfaces.SpeakerModelService;
import speakerrecognition.services.interfaces.SpeakerRecognitionService;
import speakerrecognition.services.mappers.UserMapper;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/rest/user")
public class UserRestService {

	private SpeakerModelService speakerModelService;
	private SpeakerRecognitionService speakerRecognitionService;

	@Autowired
	public UserRestService(SpeakerModelService speakerModelService,
			SpeakerRecognitionService speakerRecognitionService) {
		this.speakerModelService = speakerModelService;
		this.speakerRecognitionService = speakerRecognitionService;
	}

	@RequestMapping(value = "/register/{samples},{name},{lastName}", method = RequestMethod.GET)
	@ResponseBody
	public UserTO registerUser(@PathVariable double[] samples, String name, String lastName)
			throws MfccServiceException, MatrixesServiceException, StatisticsServiceException {
		return UserMapper.map(speakerModelService.creatorSpeakerModel(samples, name, lastName));
	}

	@RequestMapping(value = "/recognition/{samples}", method = RequestMethod.GET)
	@ResponseBody
	public UserTO recognitionUser(@PathVariable double[] samples)
			throws MfccServiceException, MatrixesServiceException, StatisticsServiceException {
		return UserMapper.map(speakerRecognitionService.recognizing(samples));
	}
}
