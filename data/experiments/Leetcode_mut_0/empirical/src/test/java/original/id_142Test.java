package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SumSquares1Test {
    @Test
	public void test1() throws Exception {
		assertEquals(6, original.SumSquares1.sumSquares(Arrays.asList(1, 2, 3)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(14, original.SumSquares1.sumSquares(Arrays.asList(1, 4, 9)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(0, original.SumSquares1.sumSquares(Arrays.asList()));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(9, original.SumSquares1.sumSquares(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(-3, original.SumSquares1.sumSquares(Arrays.asList(-1, -1, -1, -1, -1, -1, -1, -1, -1)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(0, original.SumSquares1.sumSquares(Arrays.asList(0)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(-126, original.SumSquares1.sumSquares(Arrays.asList(-1, -5, 2, -1, -5)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(3030, original.SumSquares1.sumSquares(Arrays.asList(-56, -99, 1, 0, -2)));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(0, original.SumSquares1.sumSquares(Arrays.asList(-1, 0, 0, 0, 0, 0, 0, 0, -1)));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(-14196, original.SumSquares1.sumSquares(Arrays.asList(-16, -9, -2, 36, 36, 26, -20, 25, -40, 20, -4, 12, -26, 35, 37)));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(-1448, original.SumSquares1.sumSquares(Arrays.asList(-1, -3, 17, -1, -15, 13, -1, 14, -14, -12, -5, 14, -14, 6, 13, 11, 16, 16, 4, 10)));
	}

	
}