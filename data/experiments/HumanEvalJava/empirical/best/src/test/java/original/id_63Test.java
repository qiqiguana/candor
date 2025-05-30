package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FibfibTest {
    @Test
	public void test1() throws Exception {
		assertEquals(1, original.Fibfib.fibfib(2));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(0, original.Fibfib.fibfib(1));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(4, original.Fibfib.fibfib(5));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(24, original.Fibfib.fibfib(8));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(81, original.Fibfib.fibfib(10));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(274, original.Fibfib.fibfib(12));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(927, original.Fibfib.fibfib(14));
	}

	
}