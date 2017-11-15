package speakerrecognition.services.interfaces;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.pojos.LabelsInertiaDistances;

public interface LabelsInertiaCalculatorService {

	LabelsInertiaDistances labelsPrecomputeDence(double[][] matrix, double[] matrixSquaredNorms, double[][] centers,
			double[] distances) throws MatrixesServiceException;

}
