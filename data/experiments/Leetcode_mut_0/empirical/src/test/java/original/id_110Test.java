package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExchangeTest {
    @Test
	public void test1() throws Exception {
		assertEquals("YES", original.Exchange.exchange(Arrays.asList(1, 2, 3, 4), Arrays.asList(1, 2, 3, 4)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals("NO", original.Exchange.exchange(Arrays.asList(1, 2, 3, 4), Arrays.asList(1, 5, 3, 4)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals("YES", original.Exchange.exchange(Arrays.asList(1, 2, 3, 4), Arrays.asList(2, 1, 4, 3)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals("YES", original.Exchange.exchange(Arrays.asList(5, 7, 3), Arrays.asList(2, 6, 4)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals("NO", original.Exchange.exchange(Arrays.asList(5, 7, 3), Arrays.asList(2, 6, 3)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals("NO", original.Exchange.exchange(Arrays.asList(3, 2, 6, 1, 8, 9), Arrays.asList(3, 5, 5, 1, 1, 1)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals("YES", original.Exchange.exchange(Arrays.asList(100, 200), Arrays.asList(200, 200)));
	}

	
}