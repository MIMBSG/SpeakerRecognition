package speakerrecognition.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import speakerrecognition.dao.CovarDao;
import speakerrecognition.dao.MeanDao;
import speakerrecognition.dao.WeightDao;
import speakerrecognition.entities.CovarEntity;
import speakerrecognition.entities.MeanEntity;
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
			int numOfEl = weightEntity.getIndex();
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
		return rows;
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
		return cols;
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
		return rows;
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
		return cols;
	}

	private int getNumberOfElementsWeight(Set<WeightEntity> weight) {
		int elements = 0;
		Iterator<WeightEntity> iterator = weight.iterator();
		while (iterator.hasNext()) {
			WeightEntity weightEntity = iterator.next();
			if (weightEntity.getIndex() > elements) {
				elements = weightEntity.getIndex();
			}
		}
		return elements;
	}

	@Override
	public Set<CovarEntity> createCovarEntity(double[][] covars) {
		Set<CovarEntity> covarEntities = new HashSet<CovarEntity>();
		CovarEntity covarEntity = new CovarEntity();
		for (int row = 0; row < covars.length; row++) {
			for (int col = 0; col < covars[0].length; col++) {
				covarEntity.setColumnIndex(col);
				covarEntity.setRowIndex(row);
				covarEntity.setValue(covars[row][col]);
				covarEntities.add(covarEntity);
				covarDao.save(covarEntity);
			}
		}
		return covarEntities;
	}

	@Override
	public Set<MeanEntity> createMeanEntity(double[][] means) {
		Set<MeanEntity> meanEntities = new HashSet<MeanEntity>();
		MeanEntity meanEntity = new MeanEntity();
		for (int row = 0; row < means.length; row++) {
			for (int col = 0; col < means[0].length; col++) {
				meanEntity.setColumnIndex(col);
				meanEntity.setRowIndex(row);
				meanEntity.setValue(means[row][col]);
				meanEntities.add(meanEntity);
				meanDao.save(meanEntity);
			}
		}
		return meanEntities;
	}

	@Override
	public Set<WeightEntity> createWeightEntity(double[] weights) {
		Set<WeightEntity> weightEntities = new HashSet<WeightEntity>();
		WeightEntity weightEntity = new WeightEntity();
		for (int element = 0; element < weights.length; element++) {
			weightEntity.setIndex(element);
			weightEntity.setValue(weights[element]);
			weightEntities.add(weightEntity);
			weightDao.save(weightEntity);
		}
		return weightEntities;
	}
}
