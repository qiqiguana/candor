package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RightAngleTriangleTest {
    @Test
	public void test1() throws Exception {
		assertEquals(true, original.RightAngleTriangle.rightAngleTriangle(3, 4, 5));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(false, original.RightAngleTriangle.rightAngleTriangle(1, 2, 3));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(true, original.RightAngleTriangle.rightAngleTriangle(10, 6, 8));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(false, original.RightAngleTriangle.rightAngleTriangle(2, 2, 2));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.RightAngleTriangle.rightAngleTriangle(7, 24, 25));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(false, original.RightAngleTriangle.rightAngleTriangle(10, 5, 7));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(true, original.RightAngleTriangle.rightAngleTriangle(5, 12, 13));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(true, original.RightAngleTriangle.rightAngleTriangle(15, 8, 17));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(true, original.RightAngleTriangle.rightAngleTriangle(48, 55, 73));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(false, original.RightAngleTriangle.rightAngleTriangle(1, 1, 1));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(false, original.RightAngleTriangle.rightAngleTriangle(2, 2, 10));
	}

	
}