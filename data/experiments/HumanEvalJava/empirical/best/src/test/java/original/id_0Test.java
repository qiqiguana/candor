package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HasCloseElementsTest {
    @Test
	public void test1() throws Exception {
		assertEquals(false, original.HasCloseElements.hasCloseElements(Arrays.asList(1.0, 2.0, 3.0), 0.5));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(true, original.HasCloseElements.hasCloseElements(Arrays.asList(1.0, 2.8, 3.0, 4.0, 5.0, 2.0), 0.3));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(true, original.HasCloseElements.hasCloseElements(Arrays.asList(1.0, 2.0, 3.9, 4.0, 5.0, 2.2), 0.3));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(false, original.HasCloseElements.hasCloseElements(Arrays.asList(1.0, 2.0, 3.9, 4.0, 5.0, 2.2), 0.05));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.HasCloseElements.hasCloseElements(Arrays.asList(1.0, 2.0, 5.9, 4.0, 5.0), 0.95));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(false, original.HasCloseElements.hasCloseElements(Arrays.asList(1.0, 2.0, 5.9, 4.0, 5.0), 0.8));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(true, original.HasCloseElements.hasCloseElements(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 2.0), 0.1));
	}

	@Test
	public void test8() throws Exception {
		assertEquals(true, original.HasCloseElements.hasCloseElements(Arrays.asList(1.1, 2.2, 3.1, 4.1, 5.1), 1.0));
	}

	@Test
	public void test9() throws Exception {
		assertEquals(false, original.HasCloseElements.hasCloseElements(Arrays.asList(1.1, 2.2, 3.1, 4.1, 5.1), 0.5));
	}

	
}