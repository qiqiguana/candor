package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnyIntTest {
    @Test
	public void test1() throws Exception {
		assertEquals(true, original.AnyInt.anyInt(2, 3, 1));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(false, original.AnyInt.anyInt(2.5, 2, 3));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(false, original.AnyInt.anyInt(1.5, 5, 3.5));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(false, original.AnyInt.anyInt(2, 6, 2));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.AnyInt.anyInt(4, 2, 2));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(false, original.AnyInt.anyInt(2.2, 2.2, 2.2));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(true, original.AnyInt.anyInt(-4, 6, 2));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(true, original.AnyInt.anyInt(2, 1, 1));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(true, original.AnyInt.anyInt(3, 4, 7));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(false, original.AnyInt.anyInt(3.0, 4, 7));
	}

	
}