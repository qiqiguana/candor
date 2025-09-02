package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MinsubarraysumTest {
    @Test
	public void test1() throws Exception {
		assertEquals(1, original.Minsubarraysum.minsubarraysum(Arrays.asList(2, 3, 4, 1, 2, 4)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(-6, original.Minsubarraysum.minsubarraysum(Arrays.asList(-1, -2, -3)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(-14, original.Minsubarraysum.minsubarraysum(Arrays.asList(-1, -2, -3, 2, -10)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(-9999999999999999L, original.Minsubarraysum.minsubarraysum(Arrays.asList(-9999999999999999L)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(0, original.Minsubarraysum.minsubarraysum(Arrays.asList(0, 10, 20, 1000000)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(-6, original.Minsubarraysum.minsubarraysum(Arrays.asList(-1, -2, -3, 10, -5)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(-6, original.Minsubarraysum.minsubarraysum(Arrays.asList(100, -1, -2, -3, 10, -5)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(3, original.Minsubarraysum.minsubarraysum(Arrays.asList(10, 11, 13, 8, 3, 4)));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(-33, original.Minsubarraysum.minsubarraysum(Arrays.asList(100, -33, 32, -1, 0, -2)));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(-10, original.Minsubarraysum.minsubarraysum(Arrays.asList(-10)));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(7, original.Minsubarraysum.minsubarraysum(Arrays.asList(7)));
	}

	@Test
	public void test12() throws Exception {
		assertEquals(-1, original.Minsubarraysum.minsubarraysum(Arrays.asList(1, -1)));
	}

	
}