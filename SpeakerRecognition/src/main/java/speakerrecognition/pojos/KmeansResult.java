package speakerrecognition.pojos;

public class KmeansResult {

	private double[][] bestClusterCenters;

	public KmeansResult(double[][] bestClusterCenters) {
		super();
		this.bestClusterCenters = bestClusterCenters;
	}

	public double[][] getBestClusterCenters() {
		return bestClusterCenters;
	}

	public void setBestClusterCenters(double[][] bestClusterCenters) {
		this.bestClusterCenters = bestClusterCenters;
	}

}
