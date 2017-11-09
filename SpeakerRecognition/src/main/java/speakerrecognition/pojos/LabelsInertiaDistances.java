package speakerrecognition.pojos;

public class LabelsInertiaDistances {

	private double inertia;
	private double[] distances;
	private double[] labels;

	public LabelsInertiaDistances(double inertia, double[] distances, double[] labels) {
		super();
		this.inertia = inertia;
		this.distances = distances;
		this.labels = labels;
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

}
