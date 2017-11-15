package speakerrecognition.pojos;

public class GmmResult {
	private double[][] means;
	private double[] weights;
	private double[][] covars;

	public GmmResult(double[][] means, double[][] covars, double[] weights) {
		super();
		this.means = means;
		this.weights = weights;
		this.covars = covars;
	}

	public double[][] getMeans() {
		return means;
	}

	public void setMeans(double[][] means) {
		this.means = means;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public double[][] getCovars() {
		return covars;
	}

	public void setCovars(double[][] covars) {
		this.covars = covars;
	}

}
