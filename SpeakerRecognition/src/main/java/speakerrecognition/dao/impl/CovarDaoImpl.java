package speakerrecognition.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import speakerrecognition.dao.CovarDao;
import speakerrecognition.entities.CovarEntity;

public class CovarDaoImpl extends AbstractDao<CovarEntity, Long> implements CovarDao {

	CovarDaoImpl() {

	}

	public List<CovarEntity> getById(int id) {
		TypedQuery<CovarEntity> query = entityManager.createQuery("select var from VarEntity var where var.id = :id",
				CovarEntity.class);

		query.setParameter("id", id);

		return query.getResultList();
	}

}
