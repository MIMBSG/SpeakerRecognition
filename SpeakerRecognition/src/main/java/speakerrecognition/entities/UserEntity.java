package speakerrecognition.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "lastName", nullable = false)
	private String lastName;

	@OneToMany(targetEntity = MeanEntity.class, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<MeanEntity> means = new HashSet<MeanEntity>();

	@OneToMany(targetEntity = CovarEntity.class, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<CovarEntity> covars = new HashSet<CovarEntity>();

	@OneToMany(targetEntity = WeightEntity.class, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<WeightEntity> weights = new HashSet<WeightEntity>();

	public UserEntity(Set<MeanEntity> means, Set<CovarEntity> covars, Set<WeightEntity> weights, String name,
			String lastName) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.means = means;
		this.covars = covars;
		this.weights = weights;
	}

	public UserEntity(String name, String lastName) {
		super();
		this.name = name;
		this.lastName = lastName;
	}

	public UserEntity() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<MeanEntity> getMeans() {
		return means;
	}

	public void setMeans(Set<MeanEntity> means) {
		this.means = means;
	}

	public Set<CovarEntity> getCovars() {
		return covars;
	}

	public void setCovars(Set<CovarEntity> covars) {
		this.covars = covars;
	}

	public Set<WeightEntity> getWeights() {
		return weights;
	}

	public void setWeights(Set<WeightEntity> weights) {
		this.weights = weights;
	}

}
