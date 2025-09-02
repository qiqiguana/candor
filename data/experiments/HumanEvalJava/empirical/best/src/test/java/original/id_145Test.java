package original;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderByPointsTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(-1, -11, 1, -12, 11), original.OrderByPoints.orderByPoints(Arrays.asList(1, 11, -1, -11, -12)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(0, 2, 3, 6, 53, 423, 423, 423, 1234, 145, 37, 46, 56, 463, 3457), original.OrderByPoints.orderByPoints(Arrays.asList(1234, 423, 463, 145, 2, 423, 423, 53, 6, 37, 3457, 3, 56, 0, 46)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(), original.OrderByPoints.orderByPoints(Arrays.asList()));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(-3, -32, -98, -11, 1, 2, 43, 54), original.OrderByPoints.orderByPoints(Arrays.asList(1, -11, -32, 43, 54, -98, 2, -3)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(1, 10, 2, 11, 3, 4, 5, 6, 7, 8, 9), original.OrderByPoints.orderByPoints(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(-76, -21, 0, 4, 23, 6, 6), original.OrderByPoints.orderByPoints(Arrays.asList(0, 6, 6, -76, -21, 23, 4)));
	}

	
}