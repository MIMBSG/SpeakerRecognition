package speakerrecognition.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import speakerrecognition.dao.WeightDao;
import speakerrecognition.entities.WeightEntity;

@Repository
public class WeightDaoImpl extends AbstractDao<WeightEntity, Long> implements WeightDao {

	WeightDaoImpl() {

	}

	public List<WeightEntity> getById(int id) {
		TypedQuery<WeightEntity> query = entityManager
				.createQuery("select weight from WeightEntity weight where weight.id = :id", WeightEntity.class);

		query.setParameter("id", id);

		return query.getResultList();
	}

}
