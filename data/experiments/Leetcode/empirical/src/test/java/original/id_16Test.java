package original;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CountDistinctCharactersTest {
    @Test
	public void test1() throws Exception {
		assertEquals(3, original.CountDistinctCharacters.countDistinctCharacters("xyzXYZ"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(4, original.CountDistinctCharacters.countDistinctCharacters("Jerry"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(0, original.CountDistinctCharacters.countDistinctCharacters(""));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(5, original.CountDistinctCharacters.countDistinctCharacters("abcde"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(5, original.CountDistinctCharacters.countDistinctCharacters("abcdecadeCADE"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(1, original.CountDistinctCharacters.countDistinctCharacters("aaaaAAAAaaaa"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(5, original.CountDistinctCharacters.countDistinctCharacters("Jerry jERRY JeRRRY"));
	}

	
}