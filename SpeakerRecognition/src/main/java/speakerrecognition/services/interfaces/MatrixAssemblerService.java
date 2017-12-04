package speakerrecognition.services.interfaces;

import java.util.Set;

import speakerrecognition.entities.CovarEntity;
import speakerrecognition.entities.MeanEntity;
import speakerrecognition.entities.UserEntity;
import speakerrecognition.entities.WeightEntity;

public interface MatrixAssemblerService {

	public double[][] createCovarMatrix(Set<CovarEntity> covar);

	public double[][] createMeanMatrix(Set<MeanEntity> mean);

	public double[] createWeightVec(Set<WeightEntity> weight);

	public void createCovarEntity(double[][] covars, UserEntity user);

	public void createMeanEntity(double[][] means, UserEntity user);

	public void createWeightEntity(double[] weights, UserEntity user);

}