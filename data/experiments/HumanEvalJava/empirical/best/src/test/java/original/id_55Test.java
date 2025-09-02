package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FibTest {
    @Test
	public void test1() throws Exception {
		assertEquals(55, original.Fib.fib(10));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(1, original.Fib.fib(1));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(21, original.Fib.fib(8));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(89, original.Fib.fib(11));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(144, original.Fib.fib(12));
	}

	
}