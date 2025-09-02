package original;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HistogramTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Map.of("a", 2, "b", 2), original.Histogram.histogram("a b b a"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Map.of("a", 2, "b", 2), original.Histogram.histogram("a b c a b"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Map.of("a", 1, "b", 1, "c", 1, "d", 1, "g", 1), original.Histogram.histogram("a b c d g"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Map.of("r", 1, "t", 1, "g", 1), original.Histogram.histogram("r t g"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Map.of("b", 4), original.Histogram.histogram("b b b b a"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Map.of("r", 1, "t", 1, "g", 1), original.Histogram.histogram("r t g"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Map.of(), original.Histogram.histogram(""));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(Map.of("a", 1), original.Histogram.histogram("a"));
	}

	
}