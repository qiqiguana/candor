package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilterBySubstringTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(), original.FilterBySubstring.filterBySubstring(Arrays.asList(), "a"));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList("abc", "bacd", "array"), original.FilterBySubstring.filterBySubstring(Arrays.asList("abc", "bacd", "cde", "array"), "a"));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(), original.FilterBySubstring.filterBySubstring(Arrays.asList(), "john"));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList("xxx", "xxxAAA", "xxx"), original.FilterBySubstring.filterBySubstring(Arrays.asList("xxx", "asd", "xxy", "john doe", "xxxAAA", "xxx"), "xxx"));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList("xxx", "aaaxxy", "xxxAAA", "xxx"), original.FilterBySubstring.filterBySubstring(Arrays.asList("xxx", "asd", "aaaxxy", "john doe", "xxxAAA", "xxx"), "xx"));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList("grunt", "prune"), original.FilterBySubstring.filterBySubstring(Arrays.asList("grunt", "trumpet", "prune", "gruesome"), "run"));
	}

	
}