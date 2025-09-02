package original;

import java.util.*;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Maximum1Test {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(-4, -3, 5), original.Maximum1.maximum(Arrays.asList(-3, -4, 5), 3));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(4, 4), original.Maximum1.maximum(Arrays.asList(4, -4, 4), 2));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(2), original.Maximum1.maximum(Arrays.asList(-3, 2, 1, 2, -1, -2, 1), 1));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(2, 20, 123), original.Maximum1.maximum(Arrays.asList(123, -123, 20, 0, 1, 2, -3), 3));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(0, 1, 2, 20), original.Maximum1.maximum(Arrays.asList(-123, 20, 0, 1, 2, -3), 4));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(-13, -8, 0, 0, 3, 5, 15), original.Maximum1.maximum(Arrays.asList(5, 15, 0, 3, -13, -8, 0), 7));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Arrays.asList(3, 5), original.Maximum1.maximum(Arrays.asList(-1, 0, 2, 5, 3, -10), 2));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(Arrays.asList(5), original.Maximum1.maximum(Arrays.asList(1, 0, 5, -7), 1));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(Arrays.asList(-4, 4), original.Maximum1.maximum(Arrays.asList(4, -4), 2));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(Arrays.asList(-10, 10), original.Maximum1.maximum(Arrays.asList(-10, 10), 2));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(Arrays.asList(), original.Maximum1.maximum(Arrays.asList(1, 2, 3, -23, 243, -400, 0), 0));
	}

	
}