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
@Table(name = "covar")
public class CovarEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "rowIndex")
	private int rowIndex;

	@Column(name = "columnIndex")
	private int columnIndex;

	@Column(name = "value")
	private Double value;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user")
	private UserEntity user;

	public CovarEntity(int rowIndex, int columnIndex, Double value, UserEntity user) {
		super();
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.value = value;
		this.user = user;
	}

	public CovarEntity() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
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
