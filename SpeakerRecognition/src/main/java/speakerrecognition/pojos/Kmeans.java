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
	private int n_iter = 0;
	private int n_init = 10;
	private int max_iter = 300;
	private double tolerance = 0.0001;

	private double[][] best_cluster_centers = null;
	private double[] best_labels = null;
	private double best_inertia = Double.MAX_VALUE;
	private int n_iter_ = 0;

	public Kmeans(double[][] x, int numOfClust) throws StatisticsServiceException {
		this.numOfClusters = numOfClust;
		this.tolerance = tolerance(x, this.tolerance);
		this.numOfRows = x.length;
		this.numOfCols = x[0].length;
		this.data = x;
		this.best_cluster_centers = new double[numOfClust][x[0].length];
		this.best_labels = new double[x.length];

	}

	private double tolerance(double[][] x, double tol) throws StatisticsServiceException {

		double temp[] = statService.getVariance2(x);

		for (int i = 0; i < temp.length; i++) {
			temp[i] = temp[i] * tol;
		}
		return statService.getMean(temp);
	}

	public void setBest_cluster_centers(double[][] best_cluster_centers) {
		this.best_cluster_centers = best_cluster_centers;
	}

	public void setBest_labels(double[] best_labels) {
		this.best_labels = best_labels;
	}

	public void setBest_inertia(double best_inertia) {
		this.best_inertia = best_inertia;
	}

	public Kmeans() {
		super();
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

	public int getN_iter() {
		return n_iter;
	}

	public int getN_init() {
		return n_init;
	}

	public int getMax_iter() {
		return max_iter;
	}

	public double getTolerance() {
		return tolerance;
	}

	public double[][] getBest_cluster_centers() {
		return best_cluster_centers;
	}

	public double[] getBest_labels() {
		return best_labels;
	}

	public double getBest_inertia() {
		return best_inertia;
	}

	public int getN_iter_() {
		return n_iter_;
	}

}
