package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GetOddCollatzTest {
    @Test
	public void test1() throws Exception {
		assertEquals(Arrays.asList(1, 5, 7, 11, 13, 17), original.GetOddCollatz.getOddCollatz(14));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(Arrays.asList(1, 5), original.GetOddCollatz.getOddCollatz(5));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(Arrays.asList(1, 3, 5), original.GetOddCollatz.getOddCollatz(12));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(Arrays.asList(1), original.GetOddCollatz.getOddCollatz(1));
	}

	
}