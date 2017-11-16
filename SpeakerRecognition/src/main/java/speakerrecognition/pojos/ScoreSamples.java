package speakerrecognition.pojos;

import java.util.Arrays;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(covars);
		result = prime * result + Arrays.deepHashCode(data);
		result = prime * result + Arrays.hashCode(logLikelihoods);
		result = prime * result + Arrays.hashCode(logprob);
		result = prime * result + Arrays.deepHashCode(means);
		result = prime * result + Arrays.deepHashCode(responsibilities);
		result = prime * result + Arrays.hashCode(weights);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScoreSamples other = (ScoreSamples) obj;
		if (!Arrays.deepEquals(covars, other.covars))
			return false;
		if (!Arrays.deepEquals(data, other.data))
			return false;
		if (!Arrays.equals(logLikelihoods, other.logLikelihoods))
			return false;
		if (!Arrays.equals(logprob, other.logprob))
			return false;
		if (!Arrays.deepEquals(means, other.means))
			return false;
		if (!Arrays.deepEquals(responsibilities, other.responsibilities))
			return false;
		if (!Arrays.equals(weights, other.weights))
			return false;
		return true;
	}

}
