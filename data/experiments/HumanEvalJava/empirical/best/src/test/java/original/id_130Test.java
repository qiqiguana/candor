package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TriTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(1, 3, 2.0, 8.0), original.Tri.tri(3));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(1, 3, 2.0, 8.0, 3.0), original.Tri.tri(4));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(1, 3, 2.0, 8.0, 3.0, 15.0), original.Tri.tri(5));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(1, 3, 2.0, 8.0, 3.0, 15.0, 4.0), original.Tri.tri(6));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(1, 3, 2.0, 8.0, 3.0, 15.0, 4.0, 24.0), original.Tri.tri(7));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(1, 3, 2.0, 8.0, 3.0, 15.0, 4.0, 24.0, 5.0), original.Tri.tri(8));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Arrays.asList(1, 3, 2.0, 8.0, 3.0, 15.0, 4.0, 24.0, 5.0, 35.0), original.Tri.tri(9));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(Arrays.asList(1, 3, 2.0, 8.0, 3.0, 15.0, 4.0, 24.0, 5.0, 35.0, 6.0, 48.0, 7.0, 63.0, 8.0, 80.0, 9.0, 99.0, 10.0, 120.0, 11.0), original.Tri.tri(20));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(Arrays.asList(1), original.Tri.tri(0));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(Arrays.asList(1, 3), original.Tri.tri(1));
	}

	
}