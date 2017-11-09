package speakerrecognition.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import speakerrecognition.dao.MeanDao;
import speakerrecognition.entities.MeanEntity;

@Repository
public class MeanDaoImpl extends AbstractDao<MeanEntity, Long> implements MeanDao {

	MeanDaoImpl() {

	}

	public List<MeanEntity> getById(int id) {
		TypedQuery<MeanEntity> query = entityManager.createQuery("select mean from MeanEntity mean where mean.id = :id",
				MeanEntity.class);

		query.setParameter("id", id);

		return query.getResultList();
	}

}
