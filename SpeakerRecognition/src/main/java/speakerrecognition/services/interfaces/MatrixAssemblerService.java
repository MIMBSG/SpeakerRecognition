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

	public Set<CovarEntity> createCovarEntity(double[][] covars, UserEntity user);

	public Set<MeanEntity> createMeanEntity(double[][] means, UserEntity user);

	public Set<WeightEntity> createWeightEntity(double[] weights, UserEntity user);

}