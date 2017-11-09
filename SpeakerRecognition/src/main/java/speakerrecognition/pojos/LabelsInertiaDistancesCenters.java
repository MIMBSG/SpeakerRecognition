package speakerrecognition.pojos;

public class LabelsInertiaDistancesCenters {

	private double inertia;
	private double[] distances;
	private double[] labels;
	private double[][] centers;

	public LabelsInertiaDistancesCenters(double inertia, double[] distances, double[] labels, double[][] centers) {
		super();
		this.inertia = inertia;
		this.distances = distances;
		this.labels = labels;
		this.centers = centers;
	}

	public double getInertia() {
		return inertia;
	}

	public double[] getDistances() {
		return distances;
	}

	public double[] getLabels() {
		return labels;
	}

	public double[][] getCenters() {
		return centers;
	}

}
