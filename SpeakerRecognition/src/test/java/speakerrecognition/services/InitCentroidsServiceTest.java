package speakerrecognition.services;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.services.interfaces.InitCentroidService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InitCentroidsServiceTest {

	@Mock
	private MatrixesService matrixesService;
	
	@InjectMocks
	private InitCentroidsServiceImpl initCentroidsServiceImpl;
	
	@Test
	public void dummyTest() throws MatrixesServiceException{
		//given
		double[][] data = {{1,2},{4,5}};
		int nClusters = 2;
		double[] xSqNorms = {3,4};
		double[] closestDistSq = new double[2];
		double currentPot = 0;
		double[] randVals = new double[2];
		double sum = 0;
		double[] closestDistSqCumsum = new double[2];
		int[] candidateIds = new int[2];
		double[][] distanceToCandidates = new double[2][2];
		double[] selectedRow = new double[2];
		double[] newDistSq = new double[2];
		double newPot = 0;
		double[][] initCentsResult = {{4,5},{1,2}};
		
		Mockito.when(matrixesService.euclideanDistancesBetweenVectorAndMatrix(Mockito.any(double[].class),Mockito.any(double[][].class),Mockito.any(double[].class))).thenReturn(closestDistSq);
		Mockito.when(matrixesService.sumOfVectorElements(Mockito.any(double[].class))).thenReturn(currentPot);
		Mockito.when(matrixesService.generateRandomVector(Mockito.any(double.class), Mockito.any(int.class))).thenReturn(randVals);
		Mockito.when(matrixesService.sumOfVectorElements(Mockito.any(double[].class))).thenReturn(sum);
		Mockito.when(matrixesService.fillVectorWithScalar(Mockito.any(double[].class), Mockito.any(double.class))).thenReturn(closestDistSqCumsum);
		Mockito.when(matrixesService.searchSorted(Mockito.any(double[].class),Mockito.any(double[].class))).thenReturn(candidateIds);
		Mockito.when(matrixesService.euclideanDistancesBetweenMatrixes(Mockito.any(double[][].class),Mockito.any(double[][].class),Mockito.any(double[].class))).thenReturn(distanceToCandidates);
		Mockito.when(matrixesService.selectRow(Mockito.any(double[][].class),Mockito.any(int.class))).thenReturn(selectedRow);
		Mockito.when(matrixesService.minimumValuesFromVectors(Mockito.any(double[].class),Mockito.any(double[].class))).thenReturn(newDistSq);
		Mockito.when(matrixesService.sumOfVectorElements(Mockito.any(double[].class))).thenReturn(newPot);
		
		//when
		double[][] result = initCentroidsServiceImpl.initCentroids(data, nClusters, xSqNorms);
		//then
		Assert.assertEquals(initCentsResult, result);
	}

}
