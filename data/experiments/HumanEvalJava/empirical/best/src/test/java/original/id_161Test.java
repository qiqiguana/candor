package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolveTest {
    @Test
	public void test1() throws Exception {
		assertEquals("aSdF", original.Solve.solve("AsDf"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("4321", original.Solve.solve("1234"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("AB", original.Solve.solve("ab"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("#A@c", original.Solve.solve("#a@C"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals("#aSDFw^45", original.Solve.solve("#AsdfW^45"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals("2@6#", original.Solve.solve("#6@2"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals("#$A^d", original.Solve.solve("#$a^D"));
	}

	@Test
	public void test8() throws Exception {
		assertEquals("#CCC", original.Solve.solve("#ccc"));
	}

	
}