package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HowManyTimesTest {
    @Test
	public void test1() throws Exception {
		assertEquals(0, original.HowManyTimes.howManyTimes("", "a"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(3, original.HowManyTimes.howManyTimes("aaa", "a"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(3, original.HowManyTimes.howManyTimes("aaaa", "aa"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(0, original.HowManyTimes.howManyTimes("", "x"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(4, original.HowManyTimes.howManyTimes("xyxyxyx", "x"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(4, original.HowManyTimes.howManyTimes("cacacacac", "cac"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(1, original.HowManyTimes.howManyTimes("john doe", "john"));
	}

	
}