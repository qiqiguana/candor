package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DoubleTheDifferenceTest {
    @Test
	public void test1() throws Exception {
		assertEquals(0, original.DoubleTheDifference.doubleTheDifference(Arrays.asList()));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(25, original.DoubleTheDifference.doubleTheDifference(Arrays.asList(5, 4)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(0, original.DoubleTheDifference.doubleTheDifference(Arrays.asList(0.1, 0.2, 0.3)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(0, original.DoubleTheDifference.doubleTheDifference(Arrays.asList(-10, -20, -30)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(0, original.DoubleTheDifference.doubleTheDifference(Arrays.asList(-1, -2, 8)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(34, original.DoubleTheDifference.doubleTheDifference(Arrays.asList(0.2, 3, 5)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(166650, original.DoubleTheDifference.doubleTheDifference(Arrays.asList(-99, -97, -95, -93, -91, -89, -87, -85, -83, -81, -79, -77, -75, -73, -71, -69, -67, -65, -63, -61, -59, -57, -55, -53, -51, -49, -47, -45, -43, -41, -39, -37, -35, -33, -31, -29, -27, -25, -23, -21, -19, -17, -15, -13, -11, -9, -7, -5, -3, -1, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43, 45, 47, 49, 51, 53, 55, 57, 59, 61, 63, 65, 67, 69, 71, 73, 75, 77, 79, 81, 83, 85, 87, 89, 91, 93, 95, 97, 99)));
	}

	
}