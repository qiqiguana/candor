package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimplifyTest {
    @Test
	public void test1() throws Exception {
		assertEquals(true, original.Simplify.simplify("1/5", "5/1"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(false, original.Simplify.simplify("1/6", "2/1"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(true, original.Simplify.simplify("5/1", "3/1"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(false, original.Simplify.simplify("7/10", "10/2"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.Simplify.simplify("2/10", "50/10"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(true, original.Simplify.simplify("7/2", "4/2"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(true, original.Simplify.simplify("11/6", "6/1"));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(false, original.Simplify.simplify("2/3", "5/2"));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(false, original.Simplify.simplify("5/2", "3/5"));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(true, original.Simplify.simplify("2/4", "8/4"));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(true, original.Simplify.simplify("2/4", "4/2"));
	}

	@Test
	public void test12() throws Exception {
		assertEquals(true, original.Simplify.simplify("1/5", "5/1"));
	}

	@Test
	public void test13() throws Exception {
		assertEquals(false, original.Simplify.simplify("1/5", "1/5"));
	}

	
}