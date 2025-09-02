package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordsInSentenceTest {
    @Test
	public void test1() throws Exception {
		assertEquals("is", original.WordsInSentence.wordsInSentence("This is a test"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("go for", original.WordsInSentence.wordsInSentence("lets go for swimming"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("there is no place", original.WordsInSentence.wordsInSentence("there is no place available here"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("Hi am Hussein", original.WordsInSentence.wordsInSentence("Hi I am Hussein"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals("go for it", original.WordsInSentence.wordsInSentence("go for it"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals("", original.WordsInSentence.wordsInSentence("here"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals("is", original.WordsInSentence.wordsInSentence("here is"));
	}

	
}