package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SumProductTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(10, 24), original.SumProduct.sumProduct(Arrays.asList(1, 2, 3, 4)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(0, 1), original.SumProduct.sumProduct(Arrays.asList()));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(3, 1), original.SumProduct.sumProduct(Arrays.asList(1, 1, 1)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(100, 0), original.SumProduct.sumProduct(Arrays.asList(100, 0)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(15, 105), original.SumProduct.sumProduct(Arrays.asList(3, 5, 7)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(Arrays.asList(10, 10), original.SumProduct.sumProduct(Arrays.asList(10)));
	}

	
}