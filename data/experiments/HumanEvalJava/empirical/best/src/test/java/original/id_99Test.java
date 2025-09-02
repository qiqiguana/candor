package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClosestIntegerTest {
    @Test
	public void test1() throws Exception {
		assertEquals(10, original.ClosestInteger.closestInteger("10"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(15, original.ClosestInteger.closestInteger("14.5"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(-16, original.ClosestInteger.closestInteger("-15.5"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(15, original.ClosestInteger.closestInteger("15.3"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(0, original.ClosestInteger.closestInteger("0"));
	}

	
}