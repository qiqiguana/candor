package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ByLengthTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList("Eight", "Five", "Four", "Three", "Two", "Two", "One", "One"), original.ByLength.byLength(Arrays.asList(2, 1, 1, 4, 5, 8, 2, 3)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(), original.ByLength.byLength(Arrays.asList()));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList("One"), original.ByLength.byLength(Arrays.asList(1, -1, 55)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList("Three", "Two", "One"), original.ByLength.byLength(Arrays.asList(1, -1, 3, 2)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList("Nine", "Eight", "Four"), original.ByLength.byLength(Arrays.asList(9, 4, 8)));
	}

	
}