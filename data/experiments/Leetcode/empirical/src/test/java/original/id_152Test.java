package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompareTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(0, 0, 0, 0, 3, 3), original.Compare.compare(Arrays.asList(1, 2, 3, 4, 5, 1), Arrays.asList(1, 2, 3, 4, 2, -2)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(0, 0, 0, 0, 0, 0), original.Compare.compare(Arrays.asList(0, 0, 0, 0, 0, 0), Arrays.asList(0, 0, 0, 0, 0, 0)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(2, 4, 6), original.Compare.compare(Arrays.asList(1, 2, 3), Arrays.asList(-1, -2, -3)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(2, 0, 0, 1), original.Compare.compare(Arrays.asList(1, 2, 3, 5), Arrays.asList(-1, 2, 3, 4)));
	}

	
}