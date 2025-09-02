package original;

import java.util.Arrays;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SplitWordsTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList("Hello", "world!"), original.SplitWords.splitWords("Hello world!"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList("Hello", "world!"), original.SplitWords.splitWords("Hello,world!"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList("Hello", "world,!"), original.SplitWords.splitWords("Hello world,!"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList("Hello,Hello,world", "!"), original.SplitWords.splitWords("Hello,Hello,world !"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(3, original.SplitWords.splitWords("abcdef"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(2, original.SplitWords.splitWords("aaabb"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(1, original.SplitWords.splitWords("aaaBb"));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(0, original.SplitWords.splitWords(""));
	}

	
}