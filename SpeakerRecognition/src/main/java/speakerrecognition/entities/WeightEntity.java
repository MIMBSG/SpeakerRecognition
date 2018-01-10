package speakerrecognition.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "weight")
public class WeightEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "vecIndex")
	private int vecIndex;

	@Column(name = "value")
	private Double value;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user")
	private UserEntity user;

	public WeightEntity(int vecIndex, Double value, UserEntity user) {
		super();
		this.vecIndex = vecIndex;
		this.value = value;
		this.user = user;
	}

	public WeightEntity() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVecIndex() {
		return vecIndex;
	}

	public void setVecIndex(int vecIndex) {
		this.vecIndex = vecIndex;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
