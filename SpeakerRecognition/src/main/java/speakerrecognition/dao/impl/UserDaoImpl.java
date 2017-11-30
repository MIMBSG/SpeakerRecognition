package speakerrecognition.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import speakerrecognition.dao.UserDao;
import speakerrecognition.entities.UserEntity;

@Repository
public class UserDaoImpl extends AbstractDao<UserEntity, Long> implements UserDao {

	UserDaoImpl() {

	}

	public List<UserEntity> getById(int id) {
		TypedQuery<UserEntity> query = entityManager.createQuery("select user from UserEntity user where user.id = :id",
				UserEntity.class);

		query.setParameter("id", id);

		return query.getResultList();
	}

}
