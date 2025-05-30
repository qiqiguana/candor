package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class XOrYTest {
    @Test
	public void test1() throws Exception {
		assertEquals(34, original.XOrY.xOrY(7, 34, 12));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(5, original.XOrY.xOrY(15, 8, 5));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(33, original.XOrY.xOrY(3, 33, 5212));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(3, original.XOrY.xOrY(1259, 3, 52));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(-1, original.XOrY.xOrY(7919, -1, 12));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(583, original.XOrY.xOrY(3609, 1245, 583));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(129, original.XOrY.xOrY(91, 56, 129));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(1234, original.XOrY.xOrY(6, 34, 1234));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(0, original.XOrY.xOrY(1, 2, 0));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(2, original.XOrY.xOrY(2, 2, 0));
	}

	
}