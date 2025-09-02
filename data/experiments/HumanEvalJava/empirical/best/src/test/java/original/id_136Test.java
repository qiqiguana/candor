package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LargestSmallestIntegersTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(null, 1), original.LargestSmallestIntegers.largestSmallestIntegers(Arrays.asList(2, 4, 1, 3, 5, 7)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(null, 1), original.LargestSmallestIntegers.largestSmallestIntegers(Arrays.asList(2, 4, 1, 3, 5, 7, 0)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(-2, 1), original.LargestSmallestIntegers.largestSmallestIntegers(Arrays.asList(1, 3, 2, 4, 5, 6, -2)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(-7, 2), original.LargestSmallestIntegers.largestSmallestIntegers(Arrays.asList(4, 5, 3, 6, 2, 7, -7)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(-9, 2), original.LargestSmallestIntegers.largestSmallestIntegers(Arrays.asList(7, 3, 8, 4, 9, 2, 5, -9)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(null, null), original.LargestSmallestIntegers.largestSmallestIntegers(Arrays.asList()));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Arrays.asList(null, null), original.LargestSmallestIntegers.largestSmallestIntegers(Arrays.asList(0)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(Arrays.asList(-1, null), original.LargestSmallestIntegers.largestSmallestIntegers(Arrays.asList(-1, -3, -5, -6)));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(Arrays.asList(-1, null), original.LargestSmallestIntegers.largestSmallestIntegers(Arrays.asList(-1, -3, -5, -6, 0)));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(Arrays.asList(-3, 1), original.LargestSmallestIntegers.largestSmallestIntegers(Arrays.asList(-6, -4, -4, -3, 1)));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(Arrays.asList(-3, 1), original.LargestSmallestIntegers.largestSmallestIntegers(Arrays.asList(-6, -4, -4, -3, -100, 1)));
	}

	
}