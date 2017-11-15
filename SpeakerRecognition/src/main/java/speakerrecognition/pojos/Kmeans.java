package speakerrecognition.pojos;

import speakerrecognition.exceptions.StatisticsServiceException;

public class Kmeans {

	private int numOfClusters;//pot
	private int numOfRows;//pot
	private int numOfCols;//pot
	private double[][] data;//pot
	private double tolerance;//pot

	private double[][] bestClusterCenters;//pot
	private double[] bestLabels;//pot
	private double bestInertia = Double.MAX_VALUE;//pot

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
