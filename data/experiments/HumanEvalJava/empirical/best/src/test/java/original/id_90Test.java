package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NextSmallestTest {
    @Test
	public void test1() throws Exception {
		assertEquals(2, original.NextSmallest.nextSmallest(Arrays.asList(1, 2, 3, 4, 5)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(2, original.NextSmallest.nextSmallest(Arrays.asList(5, 1, 4, 3, 2)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(null, original.NextSmallest.nextSmallest(Arrays.asList()));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(null, original.NextSmallest.nextSmallest(Arrays.asList(1, 1)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(1, original.NextSmallest.nextSmallest(Arrays.asList(1, 1, 1, 1, 0)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(null, original.NextSmallest.nextSmallest(Arrays.asList(1, 1)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(-35, original.NextSmallest.nextSmallest(Arrays.asList(-35, 34, 12, -45)));
	}

	
}