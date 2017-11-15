package speakerrecognition.services.interfaces;

import speakerrecognition.exceptions.MatrixesServiceException;

public interface InitCentroidService {

	double[][] initCentroids(double[][] data, int nClusters, double[] xSqNorms) throws MatrixesServiceException;

}
