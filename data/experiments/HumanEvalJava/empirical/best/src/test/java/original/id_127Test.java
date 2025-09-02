package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntersectionTest {
    @Test
	public void test1() throws Exception {
		assertEquals("NO", original.Intersection.intersection(Arrays.asList(1, 2), Arrays.asList(2, 3)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("NO", original.Intersection.intersection(Arrays.asList(-1, 1), Arrays.asList(0, 4)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("YES", original.Intersection.intersection(Arrays.asList(-3, -1), Arrays.asList(-5, 5)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("YES", original.Intersection.intersection(Arrays.asList(-2, 2), Arrays.asList(-4, 0)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals("NO", original.Intersection.intersection(Arrays.asList(-11, 2), Arrays.asList(-1, -1)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals("NO", original.Intersection.intersection(Arrays.asList(1, 2), Arrays.asList(3, 5)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals("NO", original.Intersection.intersection(Arrays.asList(1, 2), Arrays.asList(1, 2)));
	}

	@Test
	public void test8() throws Exception {
		assertEquals("NO", original.Intersection.intersection(Arrays.asList(-2, -2), Arrays.asList(-3, -2)));
	}

	
}