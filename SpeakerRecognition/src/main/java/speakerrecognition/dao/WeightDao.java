package speakerrecognition.dao;

import java.util.List;

import speakerrecognition.entities.WeightEntity;

public interface WeightDao extends Dao<WeightEntity, Long> {

	List<WeightEntity> getById(int id);

}
