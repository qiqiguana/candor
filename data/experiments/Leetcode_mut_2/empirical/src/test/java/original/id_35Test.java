package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MaxElementTest {
    @Test
	public void test1() throws Exception {
		assertEquals(3, original.MaxElement.maxElement(Arrays.asList(1, 2, 3)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(123, original.MaxElement.maxElement(Arrays.asList(5, 3, -5, 2, -3, 3, 9, 0, 123, 1, -10)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(3, original.MaxElement.maxElement(Arrays.asList(1, 2, 3)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(124, original.MaxElement.maxElement(Arrays.asList(5, 3, -5, 2, -3, 3, 9, 0, 124, 1, -10)));
	}

	
}