package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IsMultiplyPrimeTest {
    @Test
	public void test1() throws Exception {
		assertEquals(false, original.IsMultiplyPrime.isMultiplyPrime(5));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(true, original.IsMultiplyPrime.isMultiplyPrime(30));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(true, original.IsMultiplyPrime.isMultiplyPrime(8));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(false, original.IsMultiplyPrime.isMultiplyPrime(10));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.IsMultiplyPrime.isMultiplyPrime(125));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(true, original.IsMultiplyPrime.isMultiplyPrime(105));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(false, original.IsMultiplyPrime.isMultiplyPrime(126));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(false, original.IsMultiplyPrime.isMultiplyPrime(729));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(false, original.IsMultiplyPrime.isMultiplyPrime(891));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(true, original.IsMultiplyPrime.isMultiplyPrime(1001));
	}

	
}