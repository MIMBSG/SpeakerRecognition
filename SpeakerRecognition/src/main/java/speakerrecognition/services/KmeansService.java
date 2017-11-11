package speakerrecognition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.Kmeans;
import speakerrecognition.pojos.LabelsInertiaDistancesCenters;

@Service
public class KmeansService {

	@Autowired
	private StatisticsService statService;
	@Autowired
	private MatrixesService matrixService;
	@Autowired
	private KmeansSingleService kmeansSingleService;

	public void fit(Kmeans kmeansParameters) throws StatisticsServiceException, MatrixesServiceException {

		double[][] clusterCenters = null;
		double[] labels = null;
		double inertia = 0;

		double[] matrixMean = statService.getMean2(kmeansParameters.getData());
		for (int i = 0; i < kmeansParameters.getNumOfRows(); i++) {
			for (int j = 0; j < kmeansParameters.getNumOfCols(); j++) {
				kmeansParameters.getData()[i][j] -= matrixMean[j];
			}
		}

		double[] squaredNorms = matrixService.vectorOfSquaresNormOfRowsFromMatrix(kmeansParameters.getData());

		for (int i = 0; i < kmeansParameters.getNInit(); i++) {
			LabelsInertiaDistancesCenters kmeansSingleParameters = kmeansSingleService.kmeansSingle(
					kmeansParameters.getData(), kmeansParameters.getNumOfClusters(), squaredNorms,
					kmeansParameters.getMax_iter(), kmeansParameters.getTolerance());
			clusterCenters = kmeansSingleParameters.getCenters().clone();
			inertia = kmeansSingleParameters.getInertia();
			labels = kmeansSingleParameters.getLabels().clone();
			if (inertia < kmeansParameters.getBest_inertia()) {
				kmeansParameters.setBestInertia(inertia);
				kmeansParameters.setBestLabels(labels);
				kmeansParameters.setBestClusterCenters(clusterCenters);
			}
		}
		kmeansParameters.setBestClusterCenters(
				matrixService.matrixAddVector(kmeansParameters.getBestClusterCenters(), matrixMean));
	}
}
