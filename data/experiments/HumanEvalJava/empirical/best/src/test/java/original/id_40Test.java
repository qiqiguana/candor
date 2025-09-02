package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TriplesSumToZeroTest {
    @Test
	public void test1() throws Exception {
		assertEquals(false, original.TriplesSumToZero.triplesSumToZero(Arrays.asList(1, 3, 5, 0)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(false, original.TriplesSumToZero.triplesSumToZero(Arrays.asList(1, 3, 5, -1)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(true, original.TriplesSumToZero.triplesSumToZero(Arrays.asList(1, 3, -2, 1)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(false, original.TriplesSumToZero.triplesSumToZero(Arrays.asList(1, 2, 3, 7)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(false, original.TriplesSumToZero.triplesSumToZero(Arrays.asList(1, 2, 5, 7)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(true, original.TriplesSumToZero.triplesSumToZero(Arrays.asList(2, 4, -5, 3, 9, 7)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(false, original.TriplesSumToZero.triplesSumToZero(Arrays.asList(1)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(false, original.TriplesSumToZero.triplesSumToZero(Arrays.asList(1, 3, 5, -100)));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(false, original.TriplesSumToZero.triplesSumToZero(Arrays.asList(100, 3, 5, -100)));
	}

	
}