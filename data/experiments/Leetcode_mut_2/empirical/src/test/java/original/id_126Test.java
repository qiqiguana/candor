package original;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IsSortedTest {
    @Test
	public void test1() throws Exception {
		assertEquals(true, original.IsSorted.isSorted(Arrays.asList(5)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(true, original.IsSorted.isSorted(Arrays.asList(1, 2, 3, 4, 5)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(false, original.IsSorted.isSorted(Arrays.asList(1, 3, 2, 4, 5)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(true, original.IsSorted.isSorted(Arrays.asList(1, 2, 3, 4, 5, 6)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.IsSorted.isSorted(Arrays.asList(1, 2, 3, 4, 5, 6, 7)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(false, original.IsSorted.isSorted(Arrays.asList(1, 3, 2, 4, 5, 6, 7)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(true, original.IsSorted.isSorted(Arrays.asList()));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(true, original.IsSorted.isSorted(Arrays.asList(1)));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(false, original.IsSorted.isSorted(Arrays.asList(3, 2, 1)));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(false, original.IsSorted.isSorted(Arrays.asList(1, 2, 2, 2, 3, 4)));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(false, original.IsSorted.isSorted(Arrays.asList(1, 2, 3, 3, 3, 4)));
	}

	@Test
	public void test12() throws Exception {
		assertEquals(true, original.IsSorted.isSorted(Arrays.asList(1, 2, 2, 3, 3, 4)));
	}

	@Test
	public void test13() throws Exception {
		assertEquals(true, original.IsSorted.isSorted(Arrays.asList(1, 2, 3, 4)));
	}

	
}