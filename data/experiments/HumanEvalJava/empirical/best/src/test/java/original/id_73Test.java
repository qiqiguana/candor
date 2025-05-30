package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SmallestChangeTest {
    @Test
	public void test1() throws Exception {
		assertEquals(4, original.SmallestChange.smallestChange(Arrays.asList(1, 2, 3, 5, 4, 7, 9, 6)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(1, original.SmallestChange.smallestChange(Arrays.asList(1, 2, 3, 4, 3, 2, 2)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(1, original.SmallestChange.smallestChange(Arrays.asList(1, 4, 2)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(1, original.SmallestChange.smallestChange(Arrays.asList(1, 4, 4, 2)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(0, original.SmallestChange.smallestChange(Arrays.asList(1, 2, 3, 2, 1)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(0, original.SmallestChange.smallestChange(Arrays.asList(3, 1, 1, 3)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(0, original.SmallestChange.smallestChange(Arrays.asList(1)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(1, original.SmallestChange.smallestChange(Arrays.asList(0, 1)));
	}

	
}