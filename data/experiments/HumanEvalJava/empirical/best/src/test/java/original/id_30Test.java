package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GetPositiveTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(2, 5, 6), original.GetPositive.getPositive(Arrays.asList(-1, 2, -4, 5, 6)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(5, 3, 2, 3, 9, 123, 1), original.GetPositive.getPositive(Arrays.asList(5, 3, -5, 2, -3, 3, 9, 0, 123, 1, -10)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(4, 5, 6), original.GetPositive.getPositive(Arrays.asList(-1, -2, 4, 5, 6)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(5, 3, 2, 3, 3, 9, 123, 1), original.GetPositive.getPositive(Arrays.asList(5, 3, -5, 2, 3, 3, 9, 0, 123, 1, -10)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(), original.GetPositive.getPositive(Arrays.asList(-1, -2)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(), original.GetPositive.getPositive(Arrays.asList()));
	}

	
}