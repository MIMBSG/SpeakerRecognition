package speakerrecognition.services.rests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.services.TOs.TopUsersTO;
import speakerrecognition.services.TOs.UserTO;
import speakerrecognition.services.interfaces.SpeakerModelService;
import speakerrecognition.services.interfaces.SpeakerRecognitionService;
import speakerrecognition.services.mappers.TopUsersMapper;
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

	@RequestMapping(value = "/register/{mfccVec}/{name}/{lastName}", method = RequestMethod.POST)
	@ResponseBody
	public UserTO registerUser(@PathVariable double[] mfccVec, @PathVariable String name,
			@PathVariable String lastName)
					throws MatrixesServiceException, StatisticsServiceException {
		return UserMapper.map(speakerModelService.creatorSpeakerModel(mfccVec, name, lastName));
	}

	@RequestMapping(value = "/recognition/{mfccVec}/", method = RequestMethod.POST)
	@ResponseBody
	public List<TopUsersTO> recognitionUser(@PathVariable double[] mfccVec)
			throws MatrixesServiceException, StatisticsServiceException {
		return TopUsersMapper.map2TOs(speakerRecognitionService.recognizing(mfccVec));
	}
}
