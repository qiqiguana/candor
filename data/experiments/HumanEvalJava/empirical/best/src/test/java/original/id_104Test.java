package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UniqueDigitsTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(1, 15, 33), original.UniqueDigits.uniqueDigits(Arrays.asList(15, 33, 1422, 1)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(), original.UniqueDigits.uniqueDigits(Arrays.asList(152, 323, 1422, 10)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(111, 151), original.UniqueDigits.uniqueDigits(Arrays.asList(12345, 2033, 111, 151)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(31, 135), original.UniqueDigits.uniqueDigits(Arrays.asList(135, 103, 31)));
	}

	
}