package original;


import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FruitDistributionTest {
    @Test
	public void test1() throws Exception {
		assertEquals(8, original.FruitDistribution.fruitDistribution("5 apples and 6 oranges", 19));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(10, original.FruitDistribution.fruitDistribution("5 apples and 6 oranges", 21));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(2, original.FruitDistribution.fruitDistribution("0 apples and 1 oranges", 3));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(2, original.FruitDistribution.fruitDistribution("1 apples and 0 oranges", 3));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(95, original.FruitDistribution.fruitDistribution("2 apples and 3 oranges", 100));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(0, original.FruitDistribution.fruitDistribution("2 apples and 3 oranges", 5));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(19, original.FruitDistribution.fruitDistribution("1 apples and 100 oranges", 120));
	}

	
}