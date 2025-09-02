package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GreatestCommonDivisorTest {
    @Test
	public void test1() throws Exception {
		assertEquals(1, original.GreatestCommonDivisor.greatestCommonDivisor(3, 5));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(5, original.GreatestCommonDivisor.greatestCommonDivisor(25, 15));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(1, original.GreatestCommonDivisor.greatestCommonDivisor(3, 7));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(5, original.GreatestCommonDivisor.greatestCommonDivisor(10, 15));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(7, original.GreatestCommonDivisor.greatestCommonDivisor(49, 14));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(12, original.GreatestCommonDivisor.greatestCommonDivisor(144, 60));
	}

	
}