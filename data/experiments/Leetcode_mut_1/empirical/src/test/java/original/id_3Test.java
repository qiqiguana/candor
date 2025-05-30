package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BelowZeroTest {
    @Test
	public void test1() throws Exception {
		assertEquals(false, original.BelowZero.belowZero(Arrays.asList(1, 2, 3)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(true, original.BelowZero.belowZero(Arrays.asList(1, 2, -4, 5)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(false, original.BelowZero.belowZero(Arrays.asList()));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(false, original.BelowZero.belowZero(Arrays.asList(1, 2, -3, 1, 2, -3)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.BelowZero.belowZero(Arrays.asList(1, 2, -4, 5, 6)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(false, original.BelowZero.belowZero(Arrays.asList(1, -1, 2, -2, 5, -5, 4, -4)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(true, original.BelowZero.belowZero(Arrays.asList(1, -1, 2, -2, 5, -5, 4, -5)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(true, original.BelowZero.belowZero(Arrays.asList(1, -2, 2, -2, 5, -5, 4, -4)));
	}

	
}