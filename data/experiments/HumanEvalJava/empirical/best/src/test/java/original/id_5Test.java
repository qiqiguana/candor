package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntersperseTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(), original.Intersperse.intersperse(Arrays.asList(), 4));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(1, 4, 2, 4, 3), original.Intersperse.intersperse(Arrays.asList(1, 2, 3), 4));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(), original.Intersperse.intersperse(Arrays.asList(), 7));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(5, 8, 6, 8, 3, 8, 2), original.Intersperse.intersperse(Arrays.asList(5, 6, 3, 2), 8));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(2, 2, 2, 2, 2), original.Intersperse.intersperse(Arrays.asList(2, 2, 2), 2));
	}

	
}