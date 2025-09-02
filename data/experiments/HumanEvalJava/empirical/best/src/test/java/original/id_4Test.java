package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MeanAbsoluteDeviationTest {
    @Test
	public void test1() throws Exception {
		assertEquals(0.6666666666666666, original.MeanAbsoluteDeviation.meanAbsoluteDeviation(Arrays.asList(1.0, 2.0, 3.0)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(1.0, original.MeanAbsoluteDeviation.meanAbsoluteDeviation(Arrays.asList(1.0, 2.0, 3.0, 4.0)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(1.2, original.MeanAbsoluteDeviation.meanAbsoluteDeviation(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0)));
	}

	
}