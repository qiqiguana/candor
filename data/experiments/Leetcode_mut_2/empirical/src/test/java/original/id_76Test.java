package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IsSimplePowerTest {
    @Test
	public void test1() throws Exception {
		assertEquals(true, original.IsSimplePower.isSimplePower(16, 2));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(false, original.IsSimplePower.isSimplePower(143214, 16));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(true, original.IsSimplePower.isSimplePower(4, 2));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(true, original.IsSimplePower.isSimplePower(9, 3));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.IsSimplePower.isSimplePower(16, 4));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(false, original.IsSimplePower.isSimplePower(24, 2));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(false, original.IsSimplePower.isSimplePower(128, 4));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(false, original.IsSimplePower.isSimplePower(12, 6));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(true, original.IsSimplePower.isSimplePower(1, 1));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(true, original.IsSimplePower.isSimplePower(1, 12));
	}

	
}