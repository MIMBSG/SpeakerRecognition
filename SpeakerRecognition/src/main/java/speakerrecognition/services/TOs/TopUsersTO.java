package speakerrecognition.services.TOs;

public class TopUsersTO {
	
	private int id;
	private String name;
	private String lastName;
	private Double score;
	
	public TopUsersTO(int id, String name, String lastName, Double score) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.score = score;
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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
	
}
