package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CountNumsTest {
    @Test
	public void test1() throws Exception {
		assertEquals(0, original.CountNums.countNums(Arrays.asList()));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(0, original.CountNums.countNums(Arrays.asList(-1, -2, 0)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(6, original.CountNums.countNums(Arrays.asList(1, 1, 2, -2, 3, 4, 5)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(5, original.CountNums.countNums(Arrays.asList(1, 6, 9, -6, 0, 1, 5)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(4, original.CountNums.countNums(Arrays.asList(1, 100, 98, -7, 1, -1)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(5, original.CountNums.countNums(Arrays.asList(12, 23, 34, -45, -56, 0)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(1, original.CountNums.countNums(Arrays.asList(0, 1)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(1, original.CountNums.countNums(Arrays.asList(1)));
	}

	
}