package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LargestPrimeFactorTest {
    @Test
	public void test1() throws Exception {
		assertEquals(2, original.LargestPrimeFactor.largestPrimeFactor(2048));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(5, original.LargestPrimeFactor.largestPrimeFactor(15));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(3, original.LargestPrimeFactor.largestPrimeFactor(27));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(7, original.LargestPrimeFactor.largestPrimeFactor(63));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(11, original.LargestPrimeFactor.largestPrimeFactor(330));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(29, original.LargestPrimeFactor.largestPrimeFactor(13195));
	}

	
}