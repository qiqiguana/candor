package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordsStringTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList("Hi", "my", "name", "is", "John"), original.WordsString.wordsString("Hi, my name is John"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList("One", "two", "three", "four", "five", "six"), original.WordsString.wordsString("One, two, three, four, five, six"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList("Hi", "my", "name"), original.WordsString.wordsString("Hi, my name"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList("One", "two", "three", "four", "five", "six"), original.WordsString.wordsString("One,, two, three, four, five, six,"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(), original.WordsString.wordsString(""));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList("ahmed", "gamal"), original.WordsString.wordsString("ahmed     , gamal"));
	}

	
}