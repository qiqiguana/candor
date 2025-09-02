package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SameCharsTest {
    @Test
	public void test1() throws Exception {
		assertEquals(false, original.SameChars.sameChars("abcd", "dddddddabce"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(true, original.SameChars.sameChars("eabcdzzzz", "dddzzzzzzzddeddabc"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(true, original.SameChars.sameChars("abcd", "dddddddabc"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(true, original.SameChars.sameChars("dddddddabc", "abcd"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(false, original.SameChars.sameChars("eabcd", "dddddddabc"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(false, original.SameChars.sameChars("abcd", "dddddddabcf"));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(false, original.SameChars.sameChars("eabcdzzzz", "dddzzzzzzzddddabc"));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(false, original.SameChars.sameChars("aabb", "aaccc"));
	}

	
}