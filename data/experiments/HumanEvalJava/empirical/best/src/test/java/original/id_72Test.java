package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WillItFlyTest {
    @Test
	public void test1() throws Exception {
		assertEquals(true, original.WillItFly.willItFly(Arrays.asList(3, 2, 3), 9));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(false, original.WillItFly.willItFly(Arrays.asList(1, 2), 5));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(true, original.WillItFly.willItFly(Arrays.asList(3), 5));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(false, original.WillItFly.willItFly(Arrays.asList(3, 2, 3), 1));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(false, original.WillItFly.willItFly(Arrays.asList(1, 2, 3), 6));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(true, original.WillItFly.willItFly(Arrays.asList(5), 5));
	}

	
}