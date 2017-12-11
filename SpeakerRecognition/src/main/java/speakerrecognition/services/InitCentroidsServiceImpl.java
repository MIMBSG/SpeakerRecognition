package speakerrecognition.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.services.interfaces.InitCentroidsService;

@Service
public class InitCentroidsServiceImpl implements InitCentroidsService {

	@Autowired
	private MatrixesService matrixesService;

	@Override
	public double[][] initCentroids(double[][] data, int nClusters, double[] xSqNorms) throws MatrixesServiceException {

		double[][] centers = new double[nClusters][data[0].length];
		int nLocalTrials = 2 + (int) (Math.log(nClusters));
		int center_id = (int) Math.floor(Math.random() * data.length);
		for (int i = 0; i < data[0].length; i++) {
			centers[0][i] = data[center_id][i];
		}
		double[] closestDistSq = matrixesService.euclideanDistancesBetweenVectorAndMatrix(centers[0], data, xSqNorms);
		double currentPot = matrixesService.sumOfVectorElements(closestDistSq);
		for (int c = 1; c < nClusters; c++) {
			double[] randVals = matrixesService.generateRandomVector(currentPot, nLocalTrials);
			double[] closestDistSqCumsum = matrixesService.cumulatedSumOfElements(closestDistSq);
			int[] candidateIds = matrixesService.searchSorted(closestDistSqCumsum, randVals);
			double[][] dataCandidates = new double[nLocalTrials][data[0].length];
			for (int z = 0; z < nLocalTrials; z++) {
				for (int j = 0; j < data[0].length; j++) {
					dataCandidates[z][j] = data[candidateIds[z]][j];
				}
			}
			int bestCandidate = -1;
			double bestPot = 99999999;
			double[] bestDistSq = null;
			double[][] distanceToCandidates = matrixesService.euclideanDistancesBetweenMatrixes(dataCandidates, data,
					xSqNorms);

			for (int trial = 0; trial < nLocalTrials; trial++) {
				double[] newDistSq = matrixesService.minimumValuesFromVectors(closestDistSq,
						matrixesService.selectRow(distanceToCandidates, trial));
				double newPot = matrixesService.sumOfVectorElements(newDistSq);

				if (bestCandidate == -1 || newPot < bestPot) {
					bestCandidate = candidateIds[trial];
					bestPot = newPot;
					bestDistSq = Arrays.copyOf(newDistSq, newDistSq.length);
				}
			}
			double[] centerTemp = Arrays.copyOf(data[bestCandidate], data[bestCandidate].length);
			for (int i = 0; i < centers[0].length; i++) {
				centers[c][i] = centerTemp[i];
			}
			currentPot = bestPot;
			closestDistSq = Arrays.copyOf(bestDistSq, bestDistSq.length);

		}
		return centers;
	}
}
