package speakerrecognition.dao;

import java.util.List;

import speakerrecognition.entities.CovarEntity;

public interface CovarDao extends Dao<CovarEntity, Long> {

	List<CovarEntity> getById(int id);

}
