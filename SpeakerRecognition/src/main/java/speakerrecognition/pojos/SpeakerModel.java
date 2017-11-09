package speakerrecognition.pojos;

import java.util.List;

public class SpeakerModel {

	private double[][] means = null;
	private double[][] covars = null;
	private double[] weights = null;
	private String name = null;
	private List<String> test_nums;

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
		return test_nums;
	}

	public void setTest_nums(List<String> test_nums) {
		this.test_nums = test_nums;
	}

}
