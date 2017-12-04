package speakerrecognition.services;

import org.springframework.stereotype.Service;

@Service
public class CentersDenseService {

	public double[][] centersDense(double[][] data, double[] labels, int nClusters, double[] distances) {
		double[][] result = new double[nClusters][data[0].length];
		for (int j = 0; j < data[0].length; j++) {
			double[] sum = new double[nClusters];
			for (int k = 0; k < nClusters; k++) {
				int samplesNum = 0;
				for (int z = 0; z < labels.length; z++) {
					if (labels[z] == (double) k) {
						sum[k] += data[z][j];
						samplesNum += 1;
					}
				}
				if (samplesNum != 0)
					sum[k] /= samplesNum;
			}
			for (int i = 0; i < nClusters; i++) {
				result[i][j] = sum[i];
			}
		}
		return result;
	}

}
