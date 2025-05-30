package original;

import java.util.*;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommonTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(1, 5, 653), original.Common.common(Arrays.asList(1, 4, 3, 34, 653, 2, 5), Arrays.asList(5, 7, 1, 5, 9, 653, 121)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(2, 3), original.Common.common(Arrays.asList(5, 3, 2, 8), Arrays.asList(3, 2)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(2, 3, 4), original.Common.common(Arrays.asList(4, 3, 2, 8), Arrays.asList(3, 2, 4)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(), original.Common.common(Arrays.asList(4, 3, 2, 8), Arrays.asList()));
	}

	
}