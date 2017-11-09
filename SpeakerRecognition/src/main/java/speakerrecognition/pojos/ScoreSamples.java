package speakerrecognition.pojos;

public class ScoreSamples {
	private double[][] data = null;
	private double[] log_likelihoods = null;
	private double[][] means = null;
	private double[][] covars = null;
	private double[] weights = null;
	///// out matrixes////
	private double[] logprob = null;
	private double[][] responsibilities = null;

	public ScoreSamples(double[][] X, double[][] means, double[][] covars, double[] weights, int numberOfComponents,
			double[] logprobabilty, double[][] responsibilities) {
		this.data = X;
		this.log_likelihoods = new double[X.length];
		this.responsibilities = new double[X.length][numberOfComponents];
		this.means = means;
		this.covars = covars;
		this.weights = weights;
		this.logprob = logprobabilty;
		this.responsibilities = responsibilities;
	}

	public double[] getLogprob() {
		return logprob;
	}

	public void setLogprob(double[] logprob) {
		this.logprob = logprob;
	}

	public double[][] getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(double[][] responsibilities) {
		this.responsibilities = responsibilities;
	}

}
