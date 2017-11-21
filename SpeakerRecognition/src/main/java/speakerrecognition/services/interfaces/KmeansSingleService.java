package speakerrecognition.services.interfaces;

import speakerrecognition.exceptions.CentersDenseServiceException;
import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.pojos.LabelsInertiaDistancesCenters;

public interface KmeansSingleService {

	LabelsInertiaDistancesCenters kmeansSingle(double[][] data, int nClusters, double[] xSqNorms, int maxIter,
			double tol) throws MatrixesServiceException, CentersDenseServiceException;

}
