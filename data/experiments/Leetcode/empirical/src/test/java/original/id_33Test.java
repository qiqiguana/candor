package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SortThirdTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(1, 2, 3), original.SortThird.sortThird(Arrays.asList(1, 2, 3)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(2, 6, 3, 4, 8, 9, 5), original.SortThird.sortThird(Arrays.asList(5, 6, 3, 4, 8, 9, 2)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(1, 2, 3), original.SortThird.sortThird(Arrays.asList(1, 2, 3)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(1, 3, -5, 2, -3, 3, 5, 0, 123, 9, -10), original.SortThird.sortThird(Arrays.asList(5, 3, -5, 2, -3, 3, 9, 0, 123, 1, -10)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(-10, 8, -12, 3, 23, 2, 4, 11, 12, 5), original.SortThird.sortThird(Arrays.asList(5, 8, -12, 4, 23, 2, 3, 11, 12, -10)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(2, 6, 3, 4, 8, 9, 5), original.SortThird.sortThird(Arrays.asList(5, 6, 3, 4, 8, 9, 2)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Arrays.asList(2, 8, 3, 4, 6, 9, 5), original.SortThird.sortThird(Arrays.asList(5, 8, 3, 4, 6, 9, 2)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(Arrays.asList(2, 6, 9, 4, 8, 3, 5), original.SortThird.sortThird(Arrays.asList(5, 6, 9, 4, 8, 3, 2)));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(Arrays.asList(2, 6, 3, 4, 8, 9, 5, 1), original.SortThird.sortThird(Arrays.asList(5, 6, 3, 4, 8, 9, 2, 1)));
	}

	
}