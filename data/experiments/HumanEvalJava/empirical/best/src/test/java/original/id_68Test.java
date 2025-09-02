package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PluckTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(2, 1), original.Pluck.pluck(Arrays.asList(4, 2, 3)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(2, 1), original.Pluck.pluck(Arrays.asList(1, 2, 3)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(), original.Pluck.pluck(Arrays.asList()));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(0, 1), original.Pluck.pluck(Arrays.asList(5, 0, 3, 0, 4, 2)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(0, 3), original.Pluck.pluck(Arrays.asList(1, 2, 3, 0, 5, 3)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(4, 1), original.Pluck.pluck(Arrays.asList(5, 4, 8, 4, 8)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Arrays.asList(6, 1), original.Pluck.pluck(Arrays.asList(7, 6, 7, 1)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(Arrays.asList(), original.Pluck.pluck(Arrays.asList(7, 9, 7, 1)));
	}

	
}