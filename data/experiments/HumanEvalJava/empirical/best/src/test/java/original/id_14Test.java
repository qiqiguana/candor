package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AllPrefixesTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList("a", "ab", "abc"), original.AllPrefixes.allPrefixes("abc"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(), original.AllPrefixes.allPrefixes(""));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList("a", "as", "asd", "asdf", "asdfg", "asdfgh"), original.AllPrefixes.allPrefixes("asdfgh"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList("W", "WW", "WWW"), original.AllPrefixes.allPrefixes("WWW"));
	}

	
}