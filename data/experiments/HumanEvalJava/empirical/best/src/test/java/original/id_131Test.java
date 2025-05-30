package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DigitsTest {
    @Test
	public void test1() throws Exception {
		assertEquals(5, original.Digits.digits(5));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(5, original.Digits.digits(54));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(1, original.Digits.digits(120));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(5, original.Digits.digits(5014));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(315, original.Digits.digits(98765));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(2625, original.Digits.digits(5576543));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(0, original.Digits.digits(2468));
	}

	
}