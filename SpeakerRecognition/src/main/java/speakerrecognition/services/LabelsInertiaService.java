package speakerrecognition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.pojos.LabelsInertiaDistances;

@Service
public class LabelsInertiaService {

	@Autowired
	private MatrixesService matrixesService;

	public LabelsInertiaDistances labelsPrecomputeDence(double[][] matrix, double[] matrixSquaredNorms,
			double[][] centers, double[] distances) throws MatrixesServiceException {

		int rowsInMatrix = matrix.length;
		int elementsInCenters = centers.length;
		double[][] allDistances = matrixesService.euclideanDistancesBetweenMatrixes(centers, matrix,
				matrixSquaredNorms);
		double[] labels = new double[rowsInMatrix];
		labels = matrixesService.vectorAddScalar(labels, (double) -1);
		double[] minDistances = new double[rowsInMatrix];
		minDistances = matrixesService.vectorAddScalar(minDistances, Double.POSITIVE_INFINITY);

		for (int numOfCenterEl = 0; numOfCenterEl < elementsInCenters; numOfCenterEl++) {
			double[] rowOfAllDistances = allDistances[numOfCenterEl];
			int elementsInLabels = labels.length;
			for (int numOfEl = 0; numOfEl < elementsInLabels; numOfEl++) {
				if (rowOfAllDistances[numOfEl] < minDistances[numOfEl]) {
					labels[numOfEl] = numOfCenterEl;
				}
				minDistances[numOfEl] = Math.min(distances[numOfEl], minDistances[numOfEl]);
			}
		}
		if (rowsInMatrix == distances.length)
			distances = minDistances;
		double inertia = matrixesService.sumOfVectorElements(minDistances);
		LabelsInertiaDistances container = new LabelsInertiaDistances(inertia, distances, labels);

		return container;
	}

}