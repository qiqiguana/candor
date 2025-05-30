package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddElementsTest {
    @Test
	public void test1() throws Exception {
		assertEquals(-4, original.AddElements.addElements(Arrays.asList(1, -2, -3, 41, 57, 76, 87, 88, 99), 3));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(0, original.AddElements.addElements(Arrays.asList(111, 121, 3, 4000, 5, 6), 2));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(125, original.AddElements.addElements(Arrays.asList(11, 21, 3, 90, 5, 6, 7, 8, 9), 4));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(24, original.AddElements.addElements(Arrays.asList(111, 21, 3, 4000, 5, 6, 7, 8, 9), 4));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(1, original.AddElements.addElements(Arrays.asList(1), 1));
	}

	
}