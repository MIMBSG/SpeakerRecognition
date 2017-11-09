package speakerrecognition.services.interfaces;

import java.util.List;

import speakerrecognition.entities.CovarEntity;
import speakerrecognition.entities.MeanEntity;
import speakerrecognition.entities.WeightEntity;

public interface CreaterMatrixService {

	public double[][] createCovarMatrix(List<CovarEntity> covar);

	public double[][] createMeanMatrix(List<MeanEntity> mean);

	public double[] createWeightVec(List<WeightEntity> weight);

}