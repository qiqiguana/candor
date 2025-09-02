package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MinpathTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(1, 2, 1), original.Minpath.minpath(Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9)), 3));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(1), original.Minpath.minpath(Arrays.asList(Arrays.asList(5, 9, 3), Arrays.asList(4, 1, 6), Arrays.asList(7, 8, 2)), 1));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(1, 2, 1, 2), original.Minpath.minpath(Arrays.asList(Arrays.asList(1, 2, 3, 4), Arrays.asList(5, 6, 7, 8), Arrays.asList(9, 10, 11, 12), Arrays.asList(13, 14, 15, 16)), 4));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(1, 10, 1, 10, 1, 10, 1), original.Minpath.minpath(Arrays.asList(Arrays.asList(6, 4, 13, 10), Arrays.asList(5, 7, 12, 1), Arrays.asList(3, 16, 11, 15), Arrays.asList(8, 14, 9, 2)), 7));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(1, 7, 1, 7, 1), original.Minpath.minpath(Arrays.asList(Arrays.asList(8, 14, 9, 2), Arrays.asList(6, 4, 13, 15), Arrays.asList(5, 7, 1, 12), Arrays.asList(3, 10, 11, 16)), 5));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(1, 6, 1, 6, 1, 6, 1, 6, 1), original.Minpath.minpath(Arrays.asList(Arrays.asList(11, 8, 7, 2), Arrays.asList(5, 16, 14, 4), Arrays.asList(9, 3, 15, 6), Arrays.asList(12, 13, 10, 1)), 9));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Arrays.asList(1, 6, 1, 6, 1, 6, 1, 6, 1, 6, 1, 6), original.Minpath.minpath(Arrays.asList(Arrays.asList(12, 13, 10, 1), Arrays.asList(9, 3, 15, 6), Arrays.asList(5, 16, 14, 4), Arrays.asList(11, 8, 7, 2)), 12));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(Arrays.asList(1, 3, 1, 3, 1, 3, 1, 3), original.Minpath.minpath(Arrays.asList(Arrays.asList(2, 7, 4), Arrays.asList(3, 1, 5), Arrays.asList(6, 8, 9)), 8));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(Arrays.asList(1, 5, 1, 5, 1, 5, 1, 5), original.Minpath.minpath(Arrays.asList(Arrays.asList(6, 1, 5), Arrays.asList(3, 8, 9), Arrays.asList(2, 7, 4)), 8));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(Arrays.asList(1, 2, 1, 2, 1, 2, 1, 2, 1, 2), original.Minpath.minpath(Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4)), 10));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(Arrays.asList(1, 3, 1, 3, 1, 3, 1, 3, 1, 3), original.Minpath.minpath(Arrays.asList(Arrays.asList(1, 3), Arrays.asList(3, 2)), 10));
	}

	
}