package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SortArrayTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(1, 2, 4, 3, 5), original.SortArray.sortArray(Arrays.asList(1, 5, 2, 3, 4)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(0, 1, 2, 4, 3), original.SortArray.sortArray(Arrays.asList(1, 0, 2, 3, 4)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(), original.SortArray.sortArray(Arrays.asList()));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(2, 2, 4, 4, 3, 3, 5, 5, 5, 7, 77), original.SortArray.sortArray(Arrays.asList(2, 5, 77, 4, 5, 3, 5, 7, 2, 3, 4)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(32, 3, 5, 6, 12, 44), original.SortArray.sortArray(Arrays.asList(3, 6, 44, 12, 32, 5)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(2, 4, 8, 16, 32), original.SortArray.sortArray(Arrays.asList(2, 4, 8, 16, 32)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Arrays.asList(2, 4, 8, 16, 32), original.SortArray.sortArray(Arrays.asList(2, 4, 8, 16, 32)));
	}

	
}