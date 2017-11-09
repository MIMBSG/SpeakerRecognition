package speakerrecognition.pojos;

import org.springframework.beans.factory.annotation.Autowired;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.services.MatrixesService;
import speakerrecognition.services.StatisticsService;

public class GMM {

	@Autowired
	private StatisticsService statService;
	@Autowired
	private MatrixesService matrixService;

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

	private double[] logLikelihoods = null;
	private double[][] responsibilities = null;

	private double[][] means = null;
	private double[] weights = null;
	private double[][] covars = null;

	private double[][] bestMeans = null;
	private double[] bestWeights = null;
	private double[][] bestCovars = null;

	public void GMMHelper(double[][] data, int compNum) {
		this.observations = data;
		this.numOfRows = data.length;
		this.numOfCols = data[0].length;
		this.numOfComponents = compNum;
		this.means = new double[compNum][data[0].length];
		this.weights = new double[data.length];
		this.covars = new double[compNum][data[0].length];
	}

	public void GMMHelper(double[][] data, int compNum, int maxIt) {
		this.observations = data;
		this.numOfRows = data.length;
		this.numOfCols = data[0].length;
		this.numOfComponents = compNum;
		this.means = new double[compNum][data[0].length];
		this.weights = new double[data.length];
		this.covars = new double[compNum][data[0].length];
		this.maxIter = maxIt;
	}

	public void GMMHelper(double[][] data, int compNum, int maxIt, double thr) {
		this.observations = data;
		this.numOfRows = data.length;
		this.numOfCols = data[0].length;
		this.numOfComponents = compNum;
		this.means = new double[compNum][data[0].length];
		this.weights = new double[data.length];
		this.covars = new double[compNum][data[0].length];
		this.maxIter = maxIt;
		this.threshold = thr;
	}

	public GMM() {
		super();
	}

	public void doMstep(double[][] data, double[][] responsibilities) throws MatrixesServiceException {
		double[] weights = matrixService.sumsOfElementsInCols(responsibilities); // sumsOfElementsInCols/Rows
																					// ?
		double[][] weightedXSum = matrixService.matrixMultiplyByMatrix(matrixService.transposeMatrix(responsibilities),
				data);
		double[] inverse_weights = matrixService
				.invertElementsInVector(matrixService.vectorAddScalar(weights, 10 * EPS));
		this.weights = matrixService.vectorAddScalar(matrixService.vectorMultiplyByScalar(weights,
				1.0 / (matrixService.sumOfVectorElements(weights) + 10 * EPS)), EPS);
		this.means = matrixService.matrixMultiplyByVectorElByEl(weightedXSum, inverse_weights);
		this.covars = covarMstepDiag(this.means, data, responsibilities, weightedXSum, inverse_weights, this.minCovar);
	}

	public double[][] covarMstepDiag(double[][] means, double[][] X, double[][] responsibilities,
			double[][] weightedXSum, double[] norm, double minCovar) throws MatrixesServiceException {
		double[][] temp = null;
		double[][] avgX2 = matrixService.matrixMultiplyByVectorElByEl(
				matrixService.matrixMultiplyByMatrix(matrixService.transposeMatrix(responsibilities),
						matrixService.multiplyMatrixesElementByElement(X, X)),
				norm);
		double[][] avgMeans2 = matrixService.matrixElementsToPower(means, 2);
		double[][] avgXMeans = matrixService.matrixMultiplyByVectorElByEl(
				matrixService.multiplyMatrixesElementByElement(means, weightedXSum), norm);
		temp = matrixService.matrixAddScalar(matrixService.matrixAddMatrix(
				matrixService.matrixSubstractMatrix(avgX2, matrixService.matrixMultiplyByScalar(avgXMeans, 2)),
				avgMeans2), minCovar);
		return temp;
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

	public StatisticsService getStatService() {
		return statService;
	}

	public void setStatService(StatisticsService statService) {
		this.statService = statService;
	}

	public MatrixesService getMatrixService() {
		return matrixService;
	}

	public void setMatrixService(MatrixesService matrixService) {
		this.matrixService = matrixService;
	}

}
