package speakerrecognition.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.Assert;
import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.pojos.LabelsInertiaDistances;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LabelsInertiaCalculatorServiceImplTest {

	@Mock
	private MatrixesService matrixesService;

	@InjectMocks
	private LabelsInertiaCalculatorServiceImpl labelsInertiaCalculatorServiceImpl;

	@Test
	public void shouldBeSuccess() throws MatrixesServiceException {
		// when
		double[][] matrix = new double[2][2];
		double[] matrixSquaredNorms = new double[2];
		double[][] centers = new double[2][2];
		double[] distances = new double[2];
		int rowsInMatrix = matrix.length;
		double[][] allDistances = new double[2][2];
		double[] labels = new double[rowsInMatrix];
		double[] minDistances = new double[rowsInMatrix];
		double inertia = 0;

		Mockito.when(matrixesService.euclideanDistancesBetweenMatrixes(Mockito.any(double[][].class),
				Mockito.any(double[][].class), Mockito.any(double[].class))).thenReturn(allDistances);
		Mockito.when(matrixesService.vectorAddScalar(Mockito.any(double[].class), Mockito.any(double.class)))
				.thenReturn(labels);
		Mockito.when(matrixesService.vectorAddScalar(Mockito.any(double[].class), Mockito.any(double.class)))
				.thenReturn(minDistances);
		Mockito.when(matrixesService.sumOfVectorElements(Mockito.any(double[].class))).thenReturn(inertia);

		LabelsInertiaDistances expectedLabelesInertiaDistances = new LabelsInertiaDistances(inertia, distances, labels);
		// given
		LabelsInertiaDistances labelsInertiaDistances = labelsInertiaCalculatorServiceImpl.labelsPrecomputeDence(matrix,
				matrixSquaredNorms, centers, minDistances);
		// then
		Assert.assertEquals(expectedLabelesInertiaDistances, labelsInertiaDistances);
	}
}
