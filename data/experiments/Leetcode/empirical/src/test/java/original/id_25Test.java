package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactorizeTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(2, 2, 2), original.Factorize.factorize(8));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(5, 5), original.Factorize.factorize(25));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(2, 5, 7), original.Factorize.factorize(70));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(2), original.Factorize.factorize(2));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(2, 2), original.Factorize.factorize(4));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(2, 2, 2), original.Factorize.factorize(8));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(Arrays.asList(3, 19), original.Factorize.factorize(57));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(Arrays.asList(3, 3, 19, 19), original.Factorize.factorize(3249));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(Arrays.asList(3, 3, 3, 19, 19, 19), original.Factorize.factorize(185193));
	}

	@Test
	public void test10() throws Exception {
		assertEquals(Arrays.asList(3, 19, 19, 19), original.Factorize.factorize(20577));
	}

	@Test
	public void test11() throws Exception {
		assertEquals(Arrays.asList(2, 3, 3), original.Factorize.factorize(18));
	}

	
}