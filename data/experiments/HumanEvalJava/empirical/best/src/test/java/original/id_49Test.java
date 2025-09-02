package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ModpTest {
    @Test
	public void test1() throws Exception {
		assertEquals(3, original.Modp.modp(3, 5));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(2, original.Modp.modp(1101, 101));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(1, original.Modp.modp(0, 101));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(8, original.Modp.modp(3, 11));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(1, original.Modp.modp(100, 101));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(4, original.Modp.modp(30, 5));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(3, original.Modp.modp(31, 5));
	}

	
}