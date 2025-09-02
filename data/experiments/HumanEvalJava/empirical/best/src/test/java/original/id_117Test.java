package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SelectWordsTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList("little"), original.SelectWords.selectWords("Mary had a little lamb", 4));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList("Mary", "lamb"), original.SelectWords.selectWords("Mary had a little lamb", 3));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(), original.SelectWords.selectWords("simple white space", 2));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList("world"), original.SelectWords.selectWords("Hello world", 4));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList("Uncle"), original.SelectWords.selectWords("Uncle sam", 3));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(), original.SelectWords.selectWords("", 4));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Arrays.asList("b", "c", "d", "f"), original.SelectWords.selectWords("a b c d e f", 1));
	}

	
}