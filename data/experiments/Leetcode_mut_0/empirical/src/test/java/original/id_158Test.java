package original;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FindMaxTest {
    @Test
	public void test1() throws Exception {
		assertEquals("string", original.FindMax.findMax(Arrays.asList("name", "of", "string")));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("enam", original.FindMax.findMax(Arrays.asList("name", "enam", "game")));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("aaaaaaa", original.FindMax.findMax(Arrays.asList("aaaaaaa", "bb", "cc")));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("abc", original.FindMax.findMax(Arrays.asList("abc", "cba")));
	}

	@Test
	public void test5() throws Exception {
		assertEquals("footbott", original.FindMax.findMax(Arrays.asList("play", "this", "game", "of", "footbott")));
	}

	@Test
	public void test6() throws Exception {
		assertEquals("gonna", original.FindMax.findMax(Arrays.asList("we", "are", "gonna", "rock")));
	}

	@Test
	public void test7() throws Exception {
		assertEquals("nation", original.FindMax.findMax(Arrays.asList("we", "are", "a", "mad", "nation")));
	}

	@Test
	public void test8() throws Exception {
		assertEquals("this", original.FindMax.findMax(Arrays.asList("this", "is", "a", "prrk")));
	}

	@Test
	public void test9() throws Exception {
		assertEquals("b", original.FindMax.findMax(Arrays.asList("b")));
	}

	@Test
	public void test10() throws Exception {
		assertEquals("play", original.FindMax.findMax(Arrays.asList("play", "play", "play")));
	}

	
}