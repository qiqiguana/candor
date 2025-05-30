package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SkjkasdkdTest {
    @Test
	public void test1() throws Exception {
		assertEquals(10, original.Skjkasdkd.skjkasdkd(Arrays.asList(0, 3, 2, 1, 3, 5, 7, 4, 5, 5, 5, 2, 181, 32, 4, 32, 3, 2, 32, 324, 4, 3)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(25, original.Skjkasdkd.skjkasdkd(Arrays.asList(1, 0, 1, 8, 2, 4597, 2, 1, 3, 40, 1, 2, 1, 2, 4, 2, 5, 1)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(13, original.Skjkasdkd.skjkasdkd(Arrays.asList(1, 3, 1, 32, 5107, 34, 83278, 109, 163, 23, 2323, 32, 30, 1, 9, 3)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(11, original.Skjkasdkd.skjkasdkd(Arrays.asList(0, 724, 32, 71, 99, 32, 6, 0, 5, 91, 83, 0, 5, 6)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(3, original.Skjkasdkd.skjkasdkd(Arrays.asList(0, 81, 12, 3, 1, 21)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(7, original.Skjkasdkd.skjkasdkd(Arrays.asList(0, 8, 1, 2, 1, 7)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(19, original.Skjkasdkd.skjkasdkd(Arrays.asList(8191)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(19, original.Skjkasdkd.skjkasdkd(Arrays.asList(8191, 123456, 127, 7)));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(10, original.Skjkasdkd.skjkasdkd(Arrays.asList(127, 97, 8192)));
	}

	
}