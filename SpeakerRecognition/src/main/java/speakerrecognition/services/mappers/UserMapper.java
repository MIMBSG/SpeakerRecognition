package speakerrecognition.services.mappers;

import java.util.List;
import java.util.stream.Collectors;

import speakerrecognition.entities.UserEntity;
import speakerrecognition.services.TOs.UserTO;

public class UserMapper {

	public static UserTO map(UserEntity userEntity) {
		if (userEntity != null) {
			UserTO userTO = new UserTO(userEntity.getId(), userEntity.getName(), userEntity.getLastName());
			return userTO;
		}
		return null;
	}
	public static UserEntity map(UserTO userTO) {
		if (userTO != null) {
			UserEntity userEntity = new UserEntity();
			userEntity.setId(userTO.getId());
			userEntity.setLastName(userTO.getLastName());
			userEntity.setName(userTO.getName());
			return userEntity;
		}
		return null;

	}

	public static List<UserTO> map2TOs(List<UserEntity> userEntities) {
		return userEntities.stream().map(UserMapper::map).collect(Collectors.toList());
	}

	public static List<UserEntity> map2Entities(List<UserTO> userTOs) {
		return userTOs.stream().map(UserMapper::map).collect(Collectors.toList());
	}
}
