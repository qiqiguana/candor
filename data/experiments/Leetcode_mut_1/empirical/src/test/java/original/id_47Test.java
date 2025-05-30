package original;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MedianTest {
    @Test
	public void test1() throws Exception {
		assertEquals(3, original.Median.median(Arrays.asList(3, 1, 2, 4, 5)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(8.0, original.Median.median(Arrays.asList(-10, 4, 6, 1000, 10, 20)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(5, original.Median.median(Arrays.asList(5)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(5.5, original.Median.median(Arrays.asList(6, 5)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(7, original.Median.median(Arrays.asList(8, 1, 3, 9, 9, 2, 7)));
	}

	
}