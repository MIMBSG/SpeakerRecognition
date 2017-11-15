package speakerrecognition.pojos;

public class GMM {

	private static final double EPS = 2.2204460492503131e-16;
	private int nInit = 10;
	private int nIter = 10;
	private int numOfRows;
	private int numOfCols;
	private int maxIter;
	private double threshold;
	private int numOfComponents;
	private double[][] observations;
	private double minCovar = 0.001;
	private boolean converged = false;
	private double currentLogLikelihood = 0;
	private double prevLogLikelihood = Double.NaN;
	private double tol = 0.001;

	private double[] logLikelihoods;
	private double[][] responsibilities;

	private double[][] means;
	private double[] weights;
	private double[][] covars;

	private double[][] bestMeans;
	private double[] bestWeights;
	private double[][] bestCovars;

	public GMM() {
		super();
	}

	public int getnInit() {
		return nInit;
	}

	public void setnInit(int nInit) {
		this.nInit = nInit;
	}

	public int getnIter() {
		return nIter;
	}

	public void setnIter(int nIter) {
		this.nIter = nIter;
	}

	public int getNumOfRows() {
		return numOfRows;
	}

	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}

	public int getNumOfCols() {
		return numOfCols;
	}

	public void setNumOfCols(int numOfCols) {
		this.numOfCols = numOfCols;
	}

	public int getMaxIter() {
		return maxIter;
	}

	public void setMaxIter(int maxIter) {
		this.maxIter = maxIter;
	}

	public double getThreshold() {
		return threshold;
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}

	public int getNumOfComponents() {
		return numOfComponents;
	}

	public void setNumOfComponents(int numOfComponents) {
		this.numOfComponents = numOfComponents;
	}

	public double[][] getObservations() {
		return observations;
	}

	public void setObservations(double[][] observations) {
		this.observations = observations;
	}

	public double getMinCovar() {
		return minCovar;
	}

	public void setMinCovar(double minCovar) {
		this.minCovar = minCovar;
	}

	public boolean isConverged() {
		return converged;
	}

	public void setConverged(boolean converged) {
		this.converged = converged;
	}

	public double getCurrentLogLikelihood() {
		return currentLogLikelihood;
	}

	public void setCurrentLogLikelihood(double currentLogLikelihood) {
		this.currentLogLikelihood = currentLogLikelihood;
	}

	public double getPrevLogLikelihood() {
		return prevLogLikelihood;
	}

	public void setPrevLogLikelihood(double prevLogLikelihood) {
		this.prevLogLikelihood = prevLogLikelihood;
	}

	public double getTol() {
		return tol;
	}

	public void setTol(double tol) {
		this.tol = tol;
	}

	public double[] getLogLikelihoods() {
		return logLikelihoods;
	}

	public void setLogLikelihoods(double[] logLikelihoods) {
		this.logLikelihoods = logLikelihoods;
	}

	public double[][] getResponsibilities() {
		return responsibilities;
	}

	public void setResponsibilities(double[][] responsibilities) {
		this.responsibilities = responsibilities;
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

	public double[][] getBestMeans() {
		return bestMeans;
	}

	public void setBestMeans(double[][] bestMeans) {
		this.bestMeans = bestMeans;
	}

	public double[] getBestWeights() {
		return bestWeights;
	}

	public void setBestWeights(double[] bestWeights) {
		this.bestWeights = bestWeights;
	}

	public double[][] getBestCovars() {
		return bestCovars;
	}

	public void setBestCovars(double[][] bestCovars) {
		this.bestCovars = bestCovars;
	}

	public static double getEPS() {
		return EPS;
	}

}
