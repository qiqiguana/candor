package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilterIntegersTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(5), original.FilterIntegers.filterIntegers(Arrays.asList("a", 3.14, 5)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(1, 2, 3), original.FilterIntegers.filterIntegers(Arrays.asList(1, 2, 3, "abc", Map.of(), Arrays.asList())));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(), original.FilterIntegers.filterIntegers(Arrays.asList()));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(4, 9), original.FilterIntegers.filterIntegers(Arrays.asList(4, Map.of(), Arrays.asList(), 23.2, 9, "adasd")));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(3, 3, 3), original.FilterIntegers.filterIntegers(Arrays.asList(3, "c", 3, 3, "a", "b")));
	}

	
}