package speakerrecognition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.pojos.LabelsInertiaDistances;
import speakerrecognition.services.interfaces.LabelsInertiaCalculatorService;

@Service
public class LabelsInertiaCalculatorServiceImpl implements LabelsInertiaCalculatorService {

	@Autowired
	private MatrixesService matrixesService;

	@Override
	public LabelsInertiaDistances labelsPrecomputeDence(double[][] matrix, double[] matrixSquaredNorms,
			double[][] centers, double[] distances) throws MatrixesServiceException {

		int rowsInMatrix = matrix.length;
		int elementsInCenters = centers.length;
		double[][] allDistances = matrixesService.euclideanDistancesBetweenMatrixes(centers, matrix,
				matrixSquaredNorms);
		double[] labels = new double[rowsInMatrix];
		labels = matrixesService.vectorAddScalar(labels, (double) -1);
		double[] minDistances = new double[rowsInMatrix];
		minDistances = matrixesService.vectorAddScalar(minDistances, Double.MAX_VALUE);

		for (int numOfCenterEl = 0; numOfCenterEl < elementsInCenters; numOfCenterEl++) {
			double[] rowOfAllDistances = allDistances[numOfCenterEl];
			int elementsInLabels = labels.length;
			for (int numOfEl = 0; numOfEl < elementsInLabels; numOfEl++) {
				if (rowOfAllDistances[numOfEl] < minDistances[numOfEl]) {
					labels[numOfEl] = numOfCenterEl;
				}
				minDistances[numOfEl] = Math.min(rowOfAllDistances[numOfEl], minDistances[numOfEl]);
			}
		}
		if (rowsInMatrix == distances.length)
			distances = minDistances;
		double inertia = matrixesService.sumOfVectorElements(minDistances);

		return new LabelsInertiaDistances(inertia, distances, labels);
	}

}