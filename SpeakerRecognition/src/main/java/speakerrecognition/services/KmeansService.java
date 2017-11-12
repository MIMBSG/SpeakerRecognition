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

	private static final int N_INIT = 10;
	private static final int MAX_ITER = 300;

	public Kmeans fit(double[][] observations, int numOfComponents)
			throws StatisticsServiceException, MatrixesServiceException {

		Kmeans kmeansParameters = new Kmeans(observations, numOfComponents, computeTolerance(observations));
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

		for (int i = 0; i < N_INIT; i++) {
			LabelsInertiaDistancesCenters kmeansSingleParameters = kmeansSingleService.kmeansSingle(
					kmeansParameters.getData(), kmeansParameters.getNumOfClusters(), squaredNorms, MAX_ITER,
					kmeansParameters.getTolerance());
			clusterCenters = kmeansSingleParameters.getCenters().clone();
			inertia = kmeansSingleParameters.getInertia();
			labels = kmeansSingleParameters.getLabels().clone();
			if (inertia < kmeansParameters.getBestInertia()) {
				kmeansParameters.setBestInertia(inertia);
				kmeansParameters.setBestLabels(labels);
				kmeansParameters.setBestClusterCenters(clusterCenters);
			}
		}
		kmeansParameters.setBestClusterCenters(
				matrixService.matrixAddVector(kmeansParameters.getBestClusterCenters(), matrixMean));
		return kmeansParameters;
	}

	private double computeTolerance(double[][] x) throws StatisticsServiceException {
		double tol = 0.0001;
		double temp[] = statService.getVariance2(x);

		for (int i = 0; i < temp.length; i++) {
			temp[i] = temp[i] * tol;
		}
		return statService.getMean(temp);
	}
}
