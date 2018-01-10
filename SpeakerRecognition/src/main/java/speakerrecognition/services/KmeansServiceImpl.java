package speakerrecognition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.KmeansResult;
import speakerrecognition.pojos.LabelsInertiaDistancesCenters;
import speakerrecognition.services.interfaces.KmeansService;
import speakerrecognition.services.interfaces.KmeansSingleService;

@Service
public class KmeansServiceImpl implements KmeansService {

	@Autowired
	private StatisticsService statService;
	@Autowired
	private MatrixesService matrixService;
	@Autowired
	private KmeansSingleService kmeansSingleService;

	private static final int N_INIT = 10;
	private static final int MAX_ITER = 300;

	@Override
	public KmeansResult fit(double[][] mfcc, int numOfComponents)
			throws StatisticsServiceException, MatrixesServiceException {
		
		double[][] observations = new double[mfcc.length][mfcc[0].length];
		double[][] clusterCenters;
		double inertia;
		double[][] bestClusterCenters = new double[numOfComponents][observations[0].length];
		double tolerance = computeTolerance(observations);
		int numOfRows = observations.length;
		int numOfCols = observations[0].length;
		double bestInertia = Double.MAX_VALUE;

		double[] matrixMean = statService.getMean2(mfcc);
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfCols; j++) {
				observations[i][j] = mfcc[i][j] - matrixMean[j];
			}
		}

		double[] squaredNorms = matrixService.vectorOfSquaresNormOfRowsFromMatrix(observations);

		for (int i = 0; i < N_INIT; i++) {
			LabelsInertiaDistancesCenters kmeansSingleParameters = kmeansSingleService.kmeansSingle(observations,
					numOfComponents, squaredNorms, MAX_ITER, tolerance);
			clusterCenters = kmeansSingleParameters.getCenters().clone();
			inertia = kmeansSingleParameters.getInertia();
			if (inertia < bestInertia) {
				bestInertia = inertia;
				bestClusterCenters = clusterCenters;
			}
		}
		bestClusterCenters = matrixService.matrixAddVector(bestClusterCenters, matrixMean);
		return new KmeansResult(bestClusterCenters);
	}

	double computeTolerance(double[][] x) throws StatisticsServiceException {
		double tol = 0.0001;
		double temp[] = statService.getVariance2(x);

		for (int i = 0; i < temp.length; i++) {
			temp[i] = temp[i] * tol;
		}
		return statService.getMean(temp);
	}
}
