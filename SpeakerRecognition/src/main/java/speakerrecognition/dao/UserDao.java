package speakerrecognition.dao;

import speakerrecognition.entities.UserEntity;

public interface UserDao extends Dao<UserEntity, Long> {

	UserEntity getById(int id);
}
