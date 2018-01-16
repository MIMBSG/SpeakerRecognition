package speakerrecognition.services.mappers;

import java.util.List;
import java.util.stream.Collectors;

import speakerrecognition.pojos.SpeakerResponse;
import speakerrecognition.services.TOs.TopUsersTO;

public class TopUsersMapper {

	public static SpeakerResponse map(TopUsersTO topUsersTO){
		if (topUsersTO != null) {
			SpeakerResponse speaker = new SpeakerResponse(topUsersTO.getId(),topUsersTO.getName(),topUsersTO.getLastName(),topUsersTO.getScore());
			return speaker;
		}
		return null;
	}
	public static TopUsersTO map(SpeakerResponse speaker) {
		if (speaker != null) {
			TopUsersTO topUsersTO = new TopUsersTO(speaker.getId(),speaker.getName(),speaker.getLastName(),speaker.getScore());
			return topUsersTO;
		}
		return null;
	}

	public static List<TopUsersTO> map2TOs(List<SpeakerResponse> speakers) {
		return speakers.stream().map(TopUsersMapper::map).collect(Collectors.toList());
	}

	public static List<SpeakerResponse> map2Entities(List<TopUsersTO> topUsersTO) {
		return topUsersTO.stream().map(TopUsersMapper::map).collect(Collectors.toList());
	}

}
