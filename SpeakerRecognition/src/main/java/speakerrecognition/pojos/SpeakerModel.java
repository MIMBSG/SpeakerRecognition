package speakerrecognition.pojos;

public class SpeakerModel {

	private double[][] means;
	private double[][] covars;
	private double[] weights;
	private String name;
	private String lastName;

	public SpeakerModel() {
		super();
	}

	public SpeakerModel(double[][] means, double[][] covars, double[] weights, String name, String lastName) {
		super();
		this.means = means;
		this.covars = covars;
		this.weights = weights;
		this.name = name;
		this.lastName = lastName;
	}

	public double[][] getMeans() {
		return means;
	}

	public void setMeans(double[][] means) {
		this.means = means;
	}

	public double[][] getCovars() {
		return covars;
	}

	public void setCovars(double[][] covars) {
		this.covars = covars;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
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

}
