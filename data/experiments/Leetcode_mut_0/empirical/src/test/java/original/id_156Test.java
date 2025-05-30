package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntToMiniRomanTest {
    @Test
	public void test1() throws Exception {
		assertEquals("xix", original.IntToMiniRoman.intToMiniRoman(19));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("clii", original.IntToMiniRoman.intToMiniRoman(152));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("ccli", original.IntToMiniRoman.intToMiniRoman(251));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("cdxxvi", original.IntToMiniRoman.intToMiniRoman(426));
	}

	@Test
	public void test5() throws Exception {
		assertEquals("d", original.IntToMiniRoman.intToMiniRoman(500));
	}

	@Test
	public void test6() throws Exception {
		assertEquals("i", original.IntToMiniRoman.intToMiniRoman(1));
	}

	@Test
	public void test7() throws Exception {
		assertEquals("iv", original.IntToMiniRoman.intToMiniRoman(4));
	}

	@Test
	public void test8() throws Exception {
		assertEquals("xliii", original.IntToMiniRoman.intToMiniRoman(43));
	}

	@Test
	public void test9() throws Exception {
		assertEquals("xc", original.IntToMiniRoman.intToMiniRoman(90));
	}

	@Test
	public void test10() throws Exception {
		assertEquals("xciv", original.IntToMiniRoman.intToMiniRoman(94));
	}

	@Test
	public void test11() throws Exception {
		assertEquals("dxxxii", original.IntToMiniRoman.intToMiniRoman(532));
	}

	@Test
	public void test12() throws Exception {
		assertEquals("cm", original.IntToMiniRoman.intToMiniRoman(900));
	}

	@Test
	public void test13() throws Exception {
		assertEquals("cmxciv", original.IntToMiniRoman.intToMiniRoman(994));
	}

	@Test
	public void test14() throws Exception {
		assertEquals("m", original.IntToMiniRoman.intToMiniRoman(1000));
	}

	
}