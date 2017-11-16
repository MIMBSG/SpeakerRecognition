package speakerrecognition.services;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MatrixServiceTest.class, ScoreSamplesServiceImplTest.class,
		SpeakerSimilarytyCalculatorServiceImplTest.class, StatisticsServiceTest.class })
public class AllTests {

}
