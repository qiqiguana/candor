package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RescaleToUnitTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(0.0, 0.25, 0.5, 0.75, 1.0), original.RescaleToUnit.rescaleToUnit(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(0.0, 1.0), original.RescaleToUnit.rescaleToUnit(Arrays.asList(2.0, 49.9)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(1.0, 0.0), original.RescaleToUnit.rescaleToUnit(Arrays.asList(100.0, 49.9)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(0.0, 0.25, 0.5, 0.75, 1.0), original.RescaleToUnit.rescaleToUnit(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(0.25, 0.0, 1.0, 0.5, 0.75), original.RescaleToUnit.rescaleToUnit(Arrays.asList(2.0, 1.0, 5.0, 3.0, 4.0)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(0.25, 0.0, 1.0, 0.5, 0.75), original.RescaleToUnit.rescaleToUnit(Arrays.asList(12.0, 11.0, 15.0, 13.0, 14.0)));
	}

	
}