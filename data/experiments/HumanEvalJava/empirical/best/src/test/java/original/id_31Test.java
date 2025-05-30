package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IsPrimeTest {
    @Test
	public void test1() throws Exception {
		assertEquals(false, original.IsPrime.isPrime(6));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(true, original.IsPrime.isPrime(101));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(true, original.IsPrime.isPrime(11));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(true, original.IsPrime.isPrime(13441));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.IsPrime.isPrime(61));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(false, original.IsPrime.isPrime(4));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(false, original.IsPrime.isPrime(1));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(true, original.IsPrime.isPrime(5));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(true, original.IsPrime.isPrime(11));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(true, original.IsPrime.isPrime(17));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(false, original.IsPrime.isPrime(85));
	}

	@Test
	public void test12() throws Exception {
		assertEquals(false, original.IsPrime.isPrime(77));
	}

	@Test
	public void test13() throws Exception {
		assertEquals(false, original.IsPrime.isPrime(255379));
	}

	
}