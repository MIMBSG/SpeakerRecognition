package speakerrecognition.dao;

import java.util.List;

import speakerrecognition.entities.MeanEntity;

public interface MeanDao extends Dao<MeanEntity, Long> {

	List<MeanEntity> getById(int id);

}
