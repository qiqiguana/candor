package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SortArray1Test {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(), original.SortArray1.sortArray(Arrays.asList()));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(5), original.SortArray1.sortArray(Arrays.asList(5)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5), original.SortArray1.sortArray(Arrays.asList(2, 4, 3, 0, 1, 5)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(6, 5, 4, 3, 2, 1, 0), original.SortArray1.sortArray(Arrays.asList(2, 4, 3, 0, 1, 5, 6)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(1, 2), original.SortArray1.sortArray(Arrays.asList(2, 1)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(0, 11, 15, 32, 42, 87), original.SortArray1.sortArray(Arrays.asList(15, 42, 87, 32, 11, 0)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Arrays.asList(23, 21, 14, 11), original.SortArray1.sortArray(Arrays.asList(21, 14, 23, 11)));
	}

	
}