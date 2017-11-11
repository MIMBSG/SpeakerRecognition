package speakerrecognition.pojos;

import java.util.List;

public class SpeakerModel {

	private double[][] means;
	private double[][] covars;
	private double[] weights;
	private String name;
	private List<String> testNums;

	public SpeakerModel() {
		super();
	}

	public SpeakerModel(double[][] means, double[][] covars, double[] weights, String name, List<String> testNums) {
		super();
		this.means = means;
		this.covars = covars;
		this.weights = weights;
		this.name = name;
		this.testNums = testNums;
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

	public List<String> getTest_nums() {
		return testNums;
	}

	public void setTest_nums(List<String> testNums) {
		this.testNums = testNums;
	}

}
