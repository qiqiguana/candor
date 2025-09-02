package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    @Test
	public void test1() throws Exception {
		assertEquals(1, original.Search.search(Arrays.asList(5, 5, 5, 5, 1)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(4, original.Search.search(Arrays.asList(4, 1, 4, 1, 4, 4)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(-1, original.Search.search(Arrays.asList(3, 3)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(8, original.Search.search(Arrays.asList(8, 8, 8, 8, 8, 8, 8, 8)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(2, original.Search.search(Arrays.asList(2, 3, 3, 2, 2)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(1, original.Search.search(Arrays.asList(2, 7, 8, 8, 4, 8, 7, 3, 9, 6, 5, 10, 4, 3, 6, 7, 1, 7, 4, 10, 8, 1)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(2, original.Search.search(Arrays.asList(3, 2, 8, 2)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(1, original.Search.search(Arrays.asList(6, 7, 1, 8, 8, 10, 5, 8, 5, 3, 10)));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(-1, original.Search.search(Arrays.asList(8, 8, 3, 6, 5, 6, 4)));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(1, original.Search.search(Arrays.asList(6, 9, 6, 7, 1, 4, 7, 1, 8, 8, 9, 8, 10, 10, 8, 4, 10, 4, 10, 1, 2, 9, 5, 7, 9)));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(1, original.Search.search(Arrays.asList(1, 9, 10, 1, 3)));
	}

	@Test
	public void test12() throws Exception {
		assertEquals(5, original.Search.search(Arrays.asList(6, 9, 7, 5, 8, 7, 5, 3, 7, 5, 10, 10, 3, 6, 10, 2, 8, 6, 5, 4, 9, 5, 3, 10)));
	}

	@Test
	public void test13() throws Exception {
		assertEquals(1, original.Search.search(Arrays.asList(1)));
	}

	@Test
	public void test14() throws Exception {
		assertEquals(4, original.Search.search(Arrays.asList(8, 8, 10, 6, 4, 3, 5, 8, 2, 4, 2, 8, 4, 6, 10, 4, 2, 1, 10, 2, 1, 1, 5)));
	}

	@Test
	public void test15() throws Exception {
		assertEquals(2, original.Search.search(Arrays.asList(2, 10, 4, 8, 2, 10, 5, 1, 2, 9, 5, 5, 6, 3, 8, 6, 4, 10)));
	}

	@Test
	public void test16() throws Exception {
		assertEquals(1, original.Search.search(Arrays.asList(1, 6, 10, 1, 6, 9, 10, 8, 6, 8, 7, 3)));
	}

	@Test
	public void test17() throws Exception {
		assertEquals(4, original.Search.search(Arrays.asList(9, 2, 4, 1, 5, 1, 5, 2, 5, 7, 7, 7, 3, 10, 1, 5, 4, 2, 8, 4, 1, 9, 10, 7, 10, 2, 8, 10, 9, 4)));
	}

	@Test
	public void test18() throws Exception {
		assertEquals(4, original.Search.search(Arrays.asList(2, 6, 4, 2, 8, 7, 5, 6, 4, 10, 4, 6, 3, 7, 8, 8, 3, 1, 4, 2, 2, 10, 7)));
	}

	@Test
	public void test19() throws Exception {
		assertEquals(2, original.Search.search(Arrays.asList(9, 8, 6, 10, 2, 6, 10, 2, 7, 8, 10, 3, 8, 2, 6, 2, 3, 1)));
	}

	@Test
	public void test20() throws Exception {
		assertEquals(-1, original.Search.search(Arrays.asList(5, 5, 3, 9, 5, 6, 3, 2, 8, 5, 6, 10, 10, 6, 8, 4, 10, 7, 7, 10, 8)));
	}

	@Test
	public void test21() throws Exception {
		assertEquals(-1, original.Search.search(Arrays.asList(10)));
	}

	@Test
	public void test22() throws Exception {
		assertEquals(2, original.Search.search(Arrays.asList(9, 7, 7, 2, 4, 7, 2, 10, 9, 7, 5, 7, 2)));
	}

	@Test
	public void test23() throws Exception {
		assertEquals(1, original.Search.search(Arrays.asList(5, 4, 10, 2, 1, 1, 10, 3, 6, 1, 8)));
	}

	@Test
	public void test24() throws Exception {
		assertEquals(1, original.Search.search(Arrays.asList(7, 9, 9, 9, 3, 4, 1, 5, 9, 1, 2, 1, 1, 10, 7, 5, 6, 7, 6, 7, 7, 6)));
	}

	@Test
	public void test25() throws Exception {
		assertEquals(-1, original.Search.search(Arrays.asList(3, 10, 10, 9, 2)));
	}

	
}