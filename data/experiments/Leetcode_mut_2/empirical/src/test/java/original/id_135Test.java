package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CanArrangeTest {
    @Test
	public void test1() throws Exception {
		assertEquals(3, original.CanArrange.canArrange(Arrays.asList(1, 2, 4, 3, 5)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(-1, original.CanArrange.canArrange(Arrays.asList(1, 2, 4, 5)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(2, original.CanArrange.canArrange(Arrays.asList(1, 4, 2, 5, 6, 7, 8, 9, 10)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(4, original.CanArrange.canArrange(Arrays.asList(4, 8, 5, 7, 3)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(-1, original.CanArrange.canArrange(Arrays.asList()));
	}

	
}