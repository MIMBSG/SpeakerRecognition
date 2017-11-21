package speakerrecognition.pojos;

import java.util.Arrays;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(distances);
		long temp;
		temp = Double.doubleToLongBits(inertia);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(labels);
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
		LabelsInertiaDistances other = (LabelsInertiaDistances) obj;
		if (!Arrays.equals(distances, other.distances))
			return false;
		if (Double.doubleToLongBits(inertia) != Double.doubleToLongBits(other.inertia))
			return false;
		if (!Arrays.equals(labels, other.labels))
			return false;
		return true;
	}

}
