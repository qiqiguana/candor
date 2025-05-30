package original;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SpecialfilterTest {
    @Test
	public void test1() throws Exception {
		assertEquals(0, original.Specialfilter.specialfilter(Arrays.asList(5, -2, 1, -5)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(1, original.Specialfilter.specialfilter(Arrays.asList(15, -73, 14, -15)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(2, original.Specialfilter.specialfilter(Arrays.asList(33, -2, -3, 45, 21, 109)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(4, original.Specialfilter.specialfilter(Arrays.asList(43, -12, 93, 125, 121, 109)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(3, original.Specialfilter.specialfilter(Arrays.asList(71, -2, -33, 75, 21, 19)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(0, original.Specialfilter.specialfilter(Arrays.asList(1)));
	}

	@Test
	public void test7() throws Exception {
		assertEquals(0, original.Specialfilter.specialfilter(Arrays.asList()));
	}

	
}