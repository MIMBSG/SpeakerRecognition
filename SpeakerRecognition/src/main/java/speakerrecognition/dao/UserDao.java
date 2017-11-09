package speakerrecognition.dao;

import java.util.List;

import speakerrecognition.entities.UserEntity;

public interface UserDao extends Dao<UserEntity, Long> {

	List<UserEntity> getById(int id);
}
