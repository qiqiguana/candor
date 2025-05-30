package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StrangeSortListTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(1, 4, 2, 3), original.StrangeSortList.strangeSortList(Arrays.asList(1, 2, 3, 4)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(5, 9, 6, 8, 7), original.StrangeSortList.strangeSortList(Arrays.asList(5, 6, 7, 8, 9)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(1, 5, 2, 4, 3), original.StrangeSortList.strangeSortList(Arrays.asList(1, 2, 3, 4, 5)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(1, 9, 5, 8, 6, 7), original.StrangeSortList.strangeSortList(Arrays.asList(5, 6, 7, 8, 9, 1)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(5, 5, 5, 5), original.StrangeSortList.strangeSortList(Arrays.asList(5, 5, 5, 5)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(), original.StrangeSortList.strangeSortList(Arrays.asList()));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Arrays.asList(1, 8, 2, 7, 3, 6, 4, 5), original.StrangeSortList.strangeSortList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(Arrays.asList(-5, 5, -5, 5, 0, 2, 2, 2), original.StrangeSortList.strangeSortList(Arrays.asList(0, 2, 2, 2, 5, 5, -5, -5)));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(Arrays.asList(111111), original.StrangeSortList.strangeSortList(Arrays.asList(111111)));
	}

	
}