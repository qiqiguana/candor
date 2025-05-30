package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MonotonicTest {
    @Test
	public void test1() throws Exception {
		assertEquals(true, original.Monotonic.monotonic(Arrays.asList(1, 2, 4, 10)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(true, original.Monotonic.monotonic(Arrays.asList(1, 2, 4, 20)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(false, original.Monotonic.monotonic(Arrays.asList(1, 20, 4, 10)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(true, original.Monotonic.monotonic(Arrays.asList(4, 1, 0, -10)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.Monotonic.monotonic(Arrays.asList(4, 1, 1, 0)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(false, original.Monotonic.monotonic(Arrays.asList(1, 2, 3, 2, 5, 60)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(true, original.Monotonic.monotonic(Arrays.asList(1, 2, 3, 4, 5, 60)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(true, original.Monotonic.monotonic(Arrays.asList(9, 9, 9, 9)));
	}

	
}