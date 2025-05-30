package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BelowThresholdTest {
    @Test
	public void test1() throws Exception {
		assertEquals(true, original.BelowThreshold.belowThreshold(Arrays.asList(1, 2, 4, 10), 100));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(false, original.BelowThreshold.belowThreshold(Arrays.asList(1, 20, 4, 10), 5));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(true, original.BelowThreshold.belowThreshold(Arrays.asList(1, 20, 4, 10), 21));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(true, original.BelowThreshold.belowThreshold(Arrays.asList(1, 20, 4, 10), 22));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.BelowThreshold.belowThreshold(Arrays.asList(1, 8, 4, 10), 11));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(false, original.BelowThreshold.belowThreshold(Arrays.asList(1, 8, 4, 10), 10));
	}

	
}