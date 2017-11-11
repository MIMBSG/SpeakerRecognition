package speakerrecognition.pojos;

public class ScoreSamples {
	private double[][] data;
	private double[] logLikelihoods;
	private double[][] means;
	private double[][] covars;
	private double[] weights;
	private double[] logprob;
	private double[][] responsibilities;

	public ScoreSamples(double[][] X, double[][] means, double[][] covars, double[] weights, int numberOfComponents,
			double[] logprobabilty, double[][] responsibilities) {
		this.data = X;
		this.logLikelihoods = new double[X.length];
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

	public double[][] getData() {
		return data;
	}

	public double[] getLogLikelihoods() {
		return logLikelihoods;
	}

	public double[][] getMeans() {
		return means;
	}

	public double[][] getCovars() {
		return covars;
	}

	public double[] getWeights() {
		return weights;
	}
}
