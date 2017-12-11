package speakerrecognition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.pojos.LabelsInertiaDistances;
import speakerrecognition.pojos.LabelsInertiaDistancesCenters;
import speakerrecognition.services.CentersDenseService;
import speakerrecognition.services.interfaces.InitCentroidsService;
import speakerrecognition.services.interfaces.KmeansSingleService;
import speakerrecognition.services.interfaces.LabelsInertiaCalculatorService;

@Service
public class KmeansSingleServiceImpl implements KmeansSingleService {

	@Autowired
	private LabelsInertiaCalculatorService labelsInteria;
	@Autowired
	private MatrixesService matrixesService;
	@Autowired
	private CentersDenseService centersDense;
	@Autowired
	private InitCentroidsService initCentroids;

	@Override
	public LabelsInertiaDistancesCenters kmeansSingle(double[][] data, int nClusters, double[] xSqNorms, int maxIter,
			double tol) throws MatrixesServiceException {
		double[][] centers = initCentroids.initCentroids(data, nClusters, xSqNorms);
		double[] distances = new double[data.length];
		double[] bestLabels = null;
		double[][] bestCenters = null;
		double bestInertia = Double.MAX_VALUE;

		for (int i = 0; i < maxIter; i++) {
			double[][] centersOld = centers.clone();
			LabelsInertiaDistances labelsInertiaDistances = labelsInteria.labelsPrecomputeDence(data, xSqNorms, centers,
					distances);
			distances = labelsInertiaDistances.getDistances();

			centers = centersDense.centersDense(data, labelsInertiaDistances.getLabels(), nClusters, distances);

			if (labelsInertiaDistances.getInertia() < bestInertia) {
				bestLabels = labelsInertiaDistances.getLabels().clone();
				bestCenters = centers.clone();
				bestInertia = labelsInertiaDistances.getInertia();
			}

			if (matrixesService
					.squaredNormOfMatrix(matrixesService.matrixSubstractMatrix(centersOld, centers)) <= tol) {
				break;
			}
		}

		return new LabelsInertiaDistancesCenters(bestInertia, distances, bestLabels, bestCenters);
	}

}
