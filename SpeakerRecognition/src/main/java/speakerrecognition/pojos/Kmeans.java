package speakerrecognition.pojos;

import speakerrecognition.exceptions.StatisticsServiceException;

public class Kmeans {

	private int numOfClusters;
	private int numOfRows;
	private int numOfCols;
	private double[][] data;
	private double tolerance;

	private double[][] bestClusterCenters;
	private double[] bestLabels;
	private double bestInertia = Double.MAX_VALUE;

	public Kmeans() {
		super();
	}

	public Kmeans(double[][] x, int numOfClust, double tolerance) throws StatisticsServiceException {
		this.numOfClusters = numOfClust;
		this.tolerance = tolerance;
		this.numOfRows = x.length;
		this.numOfCols = x[0].length;
		this.data = x;
		this.bestClusterCenters = new double[numOfClust][x[0].length];
		this.bestLabels = new double[x.length];

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

	public double getTolerance() {
		return tolerance;
	}

	public double[][] getBestClusterCenters() {
		return bestClusterCenters;
	}

	public double[] getBestLabels() {
		return bestLabels;
	}

	public double getBestInertia() {
		return bestInertia;
	}

}
