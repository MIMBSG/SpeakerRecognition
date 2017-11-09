package speakerrecognition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.Kmeans;
import speakerrecognition.pojos.LabelsInertiaDistances;
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

		double[][] cluster_centers = null;
		double[] labels = null;
		double inertia = 0;

		double[] matrixMean = statService.getMean2(kmeansParameters.getData());
		for (int i = 0; i < kmeansParameters.getNumOfRows(); i++) {
			for (int j = 0; j < kmeansParameters.getNumOfCols(); j++) {
				kmeansParameters.getData()[i][j] -= matrixMean[j];
			}
		}

		double[] squaredNorms = matrixService.vectorOfSquaresNormOfRowsFromMatrix(kmeansParameters.getData());

		for (int i = 0; i < kmeansParameters.getN_init(); i++) {
			LabelsInertiaDistancesCenters kmeansSingleParameters = kmeansSingleService.kmeansSingle(
					kmeansParameters.getData(), kmeansParameters.getNumOfClusters(), squaredNorms,
					kmeansParameters.getMax_iter(), kmeansParameters.getTolerance());
			cluster_centers = kmeansSingleParameters.getCenters().clone();
			inertia = kmeansSingleParameters.getInertia();
			labels = kmeansSingleParameters.getLabels().clone();
			if (inertia < kmeansParameters.getBest_inertia()) {
				kmeansParameters.setBest_inertia(inertia);
				kmeansParameters.setBest_labels(labels);
				kmeansParameters.setBest_cluster_centers(cluster_centers);
			}
		}
		kmeansParameters.setBest_cluster_centers(
				matrixService.matrixAddVector(kmeansParameters.getBest_cluster_centers(), matrixMean));
	}
}
