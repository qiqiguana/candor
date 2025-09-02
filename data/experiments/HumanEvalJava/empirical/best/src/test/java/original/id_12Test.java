package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LongestTest {
    @Test
	public void test1() throws Exception {
		assertEquals("a", original.Longest.longest(Arrays.asList("a", "b", "c")));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("ccc", original.Longest.longest(Arrays.asList("a", "bb", "ccc")));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(null, original.Longest.longest(Arrays.asList()));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("x", original.Longest.longest(Arrays.asList("x", "y", "z")));
	}

	@Test
	public void test5() throws Exception {
		assertEquals("zzzz", original.Longest.longest(Arrays.asList("x", "yyy", "zzzz", "www", "kkkk", "abc")));
	}

	
}