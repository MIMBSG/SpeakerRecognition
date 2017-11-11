package speakerrecognition.services;

import java.util.List;

import org.springframework.stereotype.Service;

import speakerrecognition.entities.CovarEntity;
import speakerrecognition.entities.MeanEntity;
import speakerrecognition.entities.WeightEntity;
import speakerrecognition.services.interfaces.MatrixAssemblerService;

@Service
public class MatrixAssemblerServiceImpl implements MatrixAssemblerService {

	@Override
	public double[][] createCovarMatrix(List<CovarEntity> covar) {
		int rows = getRowsNumberVar(covar);
		int cols = getColumnsNumberVar(covar);
		double[][] covarMatrix = new double[rows][cols];

		for (CovarEntity covarEntity : covar) {
			int numOfRow = covarEntity.getRowIndex();
			int numOfCol = covarEntity.getColumnIndex();
			covarMatrix[numOfRow][numOfCol] = covarEntity.getValue();
		}
		return covarMatrix;
	}

	@Override
	public double[][] createMeanMatrix(List<MeanEntity> mean) {
		int rows = getRowsNumberMean(mean);
		int cols = getColumnsNumberMean(mean);
		double[][] meanMatrix = new double[rows][cols];

		for (MeanEntity meanEntity : mean) {
			int numOfRow = meanEntity.getRowIndex();
			int numOfCol = meanEntity.getColumnIndex();
			meanMatrix[numOfRow][numOfCol] = meanEntity.getValue();
		}
		return meanMatrix;
	}

	@Override
	public double[] createWeightVec(List<WeightEntity> weight) {
		int elements = getNumberOfElementsWeight(weight);
		double[] weightVec = new double[elements];

		for (WeightEntity weightEntity : weight) {
			int numOfEl = weightEntity.getIndex();
			weightVec[numOfEl] = weightEntity.getValue();
		}
		return weightVec;
	}

	private int getRowsNumberVar(List<CovarEntity> var) {
		int rows = 0;
		for (CovarEntity varEntity : var) {
			if (varEntity.getRowIndex() > rows) {
				rows = varEntity.getRowIndex();
			}
		}
		return rows;
	}

	private int getColumnsNumberVar(List<CovarEntity> var) {
		int cols = 0;
		for (CovarEntity varEntity : var) {
			if (varEntity.getColumnIndex() > cols) {
				cols = varEntity.getColumnIndex();
			}
		}
		return cols;
	}

	private int getRowsNumberMean(List<MeanEntity> mean) {
		int rows = 0;
		for (MeanEntity meanEntity : mean) {
			if (meanEntity.getRowIndex() > rows) {
				rows = meanEntity.getRowIndex();
			}
		}
		return rows;
	}

	private int getColumnsNumberMean(List<MeanEntity> mean) {
		int cols = 0;
		for (MeanEntity meanEntity : mean) {
			if (meanEntity.getColumnIndex() > cols) {
				cols = meanEntity.getColumnIndex();
			}
		}
		return cols;
	}

	private int getNumberOfElementsWeight(List<WeightEntity> weight) {
		int elements = 0;
		for (WeightEntity weightEntity : weight) {
			if (weightEntity.getIndex() > elements) {
				elements = weightEntity.getIndex();
			}
		}
		return elements;
	}
}
