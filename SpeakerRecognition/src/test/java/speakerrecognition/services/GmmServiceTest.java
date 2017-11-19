package speakerrecognition.services;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import speakerrecognition.exceptions.MatrixesServiceException;
import speakerrecognition.exceptions.StatisticsServiceException;
import speakerrecognition.pojos.GmmResult;
import speakerrecognition.pojos.KmeansResult;
import speakerrecognition.pojos.ScoreSamples;
import speakerrecognition.pojos.SpeakerModel;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GmmServiceTest {
    @Mock
    private MatrixesService matrixService;
    @Mock
    private StatisticsService statService;
    @Mock
    private KmeansServiceImpl kMeansService;
    @Mock
    private ScoreSamplesServiceImpl scoreSmplService;
    @InjectMocks
    private GmmServiceImpl gmmService;

    @Test
    public void fitTest() throws MatrixesServiceException, StatisticsServiceException {
        //given
        double[][] mfcc = { { 0, 1, 2 }, { 3, 4, 5 } };
        double[][] means = new double[2][2];
        double[][] covars = new double[2][2];
        double[] weights = new double[2];
        int numberOfComponenys = 2;
        double[][] cv = new double[2][2];
        KmeansResult kmeansResult = new KmeansResult(new double[2][2]);
        ScoreSamples scoreSamples = new ScoreSamples(new double[2][2],new double[2][2],new double[2][2],new double[2],2,new double[2],new double[2][2]);
        double currentLogLikelihood = 1;
        GmmResult gmmResult = new GmmResult(means,covars,weights);

        Mockito.when(kMeansService.fit(Mockito.any(double[][].class),Mockito.any(int.class))).thenReturn(kmeansResult);
        Mockito.when(matrixService.fillVectorWithScalar(Mockito.any(double[].class), Mockito.any(double.class))).thenReturn(weights);
        Mockito.when(matrixService.cov(Mockito.any(double[][].class))).thenReturn(covars);
        Mockito.when(matrixService.eyeWithCoef(Mockito.any(int.class),Mockito.any(double.class))).thenReturn(cv);
        Mockito.when(matrixService.matrixAddMatrix(Mockito.any(double[][].class),Mockito.any(double[][].class))).thenReturn(covars);
        Mockito.when(matrixService.duplicatedVectorsIntoMatrix(Mockito.any(double[].class),Mockito.any(int.class))).thenReturn(covars);
        Mockito.when(scoreSmplService.getScoreSamples(Mockito.any(double[][].class),Mockito.any(double[][].class),Mockito.any(double[][].class),Mockito.any(double[].class),Mockito.any(int.class))).thenReturn(scoreSamples);
        Mockito.when(statService.getMean(Mockito.any(double[].class))).thenReturn(currentLogLikelihood);

        // when
        GmmResult result = gmmService.fit(mfcc,numberOfComponenys);

        //then
       // Assert.assertEquals(gmmResult,result);
    }
    @Test
    public void doMstepTest() throws MatrixesServiceException {
        //given
        double[] tempWeights = new double[3];
        double[][] weightedXSum = new double[3][3];
        double[] inverse_weights = new double[3];
        double[] weights = new double[3];
        double[][] means = new double[3][3];
        double[][] tempInverseMatrix = new double[3][3];

        Mockito.when(matrixService.sumsOfElementsInCols(Mockito.any(double[][].class))).thenReturn(tempWeights);
        Mockito.when(matrixService.matrixMultiplyByMatrix(Mockito.any(double[][].class),Mockito.any(double[][].class))).thenReturn(weightedXSum);
        Mockito.when(matrixService.transposeMatrix(Mockito.any(double[][].class))).thenReturn(tempInverseMatrix);
        Mockito.when(matrixService.invertElementsInVector(Mockito.any(double[].class))).thenReturn(inverse_weights);
        Mockito.when(matrixService.vectorAddScalar(Mockito.any(double[].class),Mockito.any(double.class))).thenReturn(weights);
        Mockito.when(matrixService.matrixMultiplyByVectorElByEl(Mockito.any(double[][].class),Mockito.any(double[].class))).thenReturn(means);
        //TO DO
    }
    @Test
    public void covarMstepDiag() throws MatrixesServiceException {
        //given
        double[][] avgX = new double[2][2];
        double[][] avgMeans2 = new double[2][2];
        double[][] avgXMeans = new double[2][2];
        double[][] covarDiagToReturn = new double[2][2];

        Mockito.when(matrixService.matrixMultiplyByVectorElByEl(Mockito.any(double[][].class),Mockito.any(double[].class))).thenReturn(avgX);
        Mockito.when(matrixService.matrixElementsToPower(Mockito.any(double[][].class),Mockito.any(double.class))).thenReturn(avgMeans2);
        Mockito.when(matrixService.matrixAddScalar(Mockito.any(double[][].class),Mockito.any(double.class))).thenReturn(covarDiagToReturn);
    }
}
