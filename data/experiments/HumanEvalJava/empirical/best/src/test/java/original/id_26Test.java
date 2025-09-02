package original;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RemoveDuplicatesTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(1, 3, 4), original.RemoveDuplicates.removeDuplicates(Arrays.asList(1, 2, 3, 2, 4)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(), original.RemoveDuplicates.removeDuplicates(Arrays.asList()));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(1, 2, 3, 4), original.RemoveDuplicates.removeDuplicates(Arrays.asList(1, 2, 3, 4)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(1, 4, 5), original.RemoveDuplicates.removeDuplicates(Arrays.asList(1, 2, 3, 2, 4, 3, 5)));
	}

	
}