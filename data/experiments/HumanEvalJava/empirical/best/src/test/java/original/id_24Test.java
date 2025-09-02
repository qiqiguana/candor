package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LargestDivisorTest {
    @Test
	public void test1() throws Exception {
		assertEquals(5, original.LargestDivisor.largestDivisor(15));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(1, original.LargestDivisor.largestDivisor(3));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(1, original.LargestDivisor.largestDivisor(7));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(5, original.LargestDivisor.largestDivisor(10));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(50, original.LargestDivisor.largestDivisor(100));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(7, original.LargestDivisor.largestDivisor(49));
	}

	
}