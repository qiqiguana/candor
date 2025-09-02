package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IncrListTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(2, 3, 4), original.IncrList.incrList(Arrays.asList(1, 2, 3)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(6, 4, 6, 3, 4, 4, 10, 1, 124), original.IncrList.incrList(Arrays.asList(5, 3, 5, 2, 3, 3, 9, 0, 123)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(), original.IncrList.incrList(Arrays.asList()));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(4, 3, 2), original.IncrList.incrList(Arrays.asList(3, 2, 1)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(Arrays.asList(6, 3, 6, 3, 4, 4, 10, 1, 124), original.IncrList.incrList(Arrays.asList(5, 2, 5, 2, 3, 3, 9, 0, 123)));
	}

	
}