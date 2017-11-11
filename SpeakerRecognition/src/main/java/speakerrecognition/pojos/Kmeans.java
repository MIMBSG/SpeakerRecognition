package speakerrecognition.pojos;

import org.springframework.beans.factory.annotation.Autowired;

import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.services.StatisticsService;

public class Kmeans {

	@Autowired
	private StatisticsService statService;

	private int numOfClusters;
	private int numOfRows;
	private int numOfCols;
	private double[][] data;
	private static final int N_ITER = 0;
	private static final int N_INIT = 10;
	private static final int MAX_ITER = 300;
	private double tolerance = 0.0001;

	private double[][] bestClusterCenters;
	private double[] bestLabels;
	private double bestInertia = Double.MAX_VALUE;

	public Kmeans() {
		super();
	}

	public Kmeans(double[][] x, int numOfClust) throws StatisticsServiceException {
		this.numOfClusters = numOfClust;
		this.tolerance = tolerance(x, this.tolerance);
		this.numOfRows = x.length;
		this.numOfCols = x[0].length;
		this.data = x;
		this.bestClusterCenters = new double[numOfClust][x[0].length];
		this.bestLabels = new double[x.length];

	}

	private double tolerance(double[][] x, double tol) throws StatisticsServiceException {

		double temp[] = statService.getVariance2(x);

		for (int i = 0; i < temp.length; i++) {
			temp[i] = temp[i] * tol;
		}
		return statService.getMean(temp);
	}

	public void setBestClusterCenters(double[][] bestClusterCenters) {
		this.bestClusterCenters = bestClusterCenters;
	}

	public void setBestLabels(double[] bestLabels) {
		this.bestLabels = bestLabels;
	}

	public void setBestInertia(double bestInertia) {
		this.bestInertia = bestInertia;
	}

	public int getNumOfClusters() {
		return numOfClusters;
	}

	public int getNumOfRows() {
		return numOfRows;
	}

	public int getNumOfCols() {
		return numOfCols;
	}

	public double[][] getData() {
		return data;
	}

	public int getNIter() {
		return N_ITER;
	}

	public int getNInit() {
		return N_INIT;
	}

	public int getMax_iter() {
		return MAX_ITER;
	}

	public double getTolerance() {
		return tolerance;
	}

	public double[][] getBestClusterCenters() {
		return bestClusterCenters;
	}

	public double[] getBestLabels() {
		return bestLabels;
	}

	public double getBest_inertia() {
		return bestInertia;
	}

}
