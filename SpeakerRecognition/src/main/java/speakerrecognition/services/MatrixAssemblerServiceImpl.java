package speakerrecognition.services;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.dao.CovarDao;
import speakerrecognition.dao.MeanDao;
import speakerrecognition.dao.WeightDao;
import speakerrecognition.entities.CovarEntity;
import speakerrecognition.entities.MeanEntity;
import speakerrecognition.entities.UserEntity;
import speakerrecognition.entities.WeightEntity;
import speakerrecognition.services.interfaces.MatrixAssemblerService;

@Service
public class MatrixAssemblerServiceImpl implements MatrixAssemblerService {

	@Autowired
	WeightDao weightDao;
	@Autowired
	MeanDao meanDao;
	@Autowired
	CovarDao covarDao;

	@Override
	public double[][] createCovarMatrix(Set<CovarEntity> covar) {
		int rows = getRowsNumberCovar(covar);
		int cols = getColumnsNumberCovar(covar);
		double[][] covarMatrix = new double[rows][cols];

		Iterator<CovarEntity> iterator = covar.iterator();

		while (iterator.hasNext()) {
			CovarEntity covarEntity = iterator.next();
			int numOfRow = covarEntity.getRowIndex();
			int numOfCol = covarEntity.getColumnIndex();
			covarMatrix[numOfRow][numOfCol] = covarEntity.getValue();
		}

		return covarMatrix;
	}

	@Override
	public double[][] createMeanMatrix(Set<MeanEntity> mean) {
		int rows = getRowsNumberMean(mean);
		int cols = getColumnsNumberMean(mean);
		double[][] meanMatrix = new double[rows][cols];

		Iterator<MeanEntity> iterator = mean.iterator();
		while (iterator.hasNext()) {
			MeanEntity meanEntity = iterator.next();
			int numOfRow = meanEntity.getRowIndex();
			int numOfCol = meanEntity.getColumnIndex();
			meanMatrix[numOfRow][numOfCol] = meanEntity.getValue();
		}
		return meanMatrix;
	}

	@Override
	public double[] createWeightVec(Set<WeightEntity> weight) {
		int elements = getNumberOfElementsWeight(weight);
		double[] weightVec = new double[elements];

		Iterator<WeightEntity> iterator = weight.iterator();
		while (iterator.hasNext()) {
			WeightEntity weightEntity = iterator.next();
			int numOfEl = weightEntity.getVecIndex();
			weightVec[numOfEl] = weightEntity.getValue();
		}
		return weightVec;
	}

	private int getRowsNumberCovar(Set<CovarEntity> covar) {
		int rows = 0;
		Iterator<CovarEntity> iterator = covar.iterator();
		while (iterator.hasNext()) {
			CovarEntity covarEntity = iterator.next();
			if (covarEntity.getRowIndex() > rows) {
				rows = covarEntity.getRowIndex();
			}
		}
		return rows + 1;
	}

	private int getColumnsNumberCovar(Set<CovarEntity> covar) {
		int cols = 0;
		Iterator<CovarEntity> iterator = covar.iterator();
		while (iterator.hasNext()) {
			CovarEntity covarEntity = iterator.next();
			if (covarEntity.getColumnIndex() > cols) {
				cols = covarEntity.getColumnIndex();
			}
		}
		return cols + 1;
	}

	private int getRowsNumberMean(Set<MeanEntity> mean) {
		int rows = 0;
		Iterator<MeanEntity> iterator = mean.iterator();
		while (iterator.hasNext()) {
			MeanEntity meanEntity = iterator.next();
			if (meanEntity.getRowIndex() > rows) {
				rows = meanEntity.getRowIndex();
			}
		}
		return rows + 1;
	}

	private int getColumnsNumberMean(Set<MeanEntity> mean) {
		int cols = 0;
		Iterator<MeanEntity> iterator = mean.iterator();
		while (iterator.hasNext()) {
			MeanEntity meanEntity = iterator.next();
			if (meanEntity.getColumnIndex() > cols) {
				cols = meanEntity.getColumnIndex();
			}
		}
		return cols + 1;
	}

	private int getNumberOfElementsWeight(Set<WeightEntity> weight) {
		int elements = 0;
		Iterator<WeightEntity> iterator = weight.iterator();
		while (iterator.hasNext()) {
			WeightEntity weightEntity = iterator.next();
			if (weightEntity.getVecIndex() > elements) {
				elements = weightEntity.getVecIndex();
			}
		}
		return elements + 1;
	}

	@Override
	public void createCovarEntity(double[][] covars, UserEntity user) {

		for (int row = 0; row < covars.length; row++) {
			for (int col = 0; col < covars[0].length; col++) {
				CovarEntity covarEntity = new CovarEntity(row, col, covars[row][col], user);
				covarDao.save(covarEntity);
			}
		}
	}

	@Override
	public void createMeanEntity(double[][] means, UserEntity user) {
		for (int row = 0; row < means.length; row++) {
			for (int col = 0; col < means[0].length; col++) {
				MeanEntity meanEntity = new MeanEntity(row, col, means[row][col], user);
				meanDao.save(meanEntity);
			}
		}
	}

	@Override
	public void createWeightEntity(double[] weights, UserEntity user) {
		for (int element = 0; element < weights.length; element++) {
			WeightEntity weightEntity = new WeightEntity(element, weights[element], user);
			weightDao.save(weightEntity);
		}
	}
}
