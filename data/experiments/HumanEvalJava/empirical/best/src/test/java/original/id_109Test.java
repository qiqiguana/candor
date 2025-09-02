package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MoveOneBallTest {
    @Test
	public void test1() throws Exception {
		assertEquals(true, original.MoveOneBall.moveOneBall(Arrays.asList(3, 4, 5, 1, 2)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(true, original.MoveOneBall.moveOneBall(Arrays.asList(3, 5, 10, 1, 2)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(false, original.MoveOneBall.moveOneBall(Arrays.asList(4, 3, 1, 2)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(false, original.MoveOneBall.moveOneBall(Arrays.asList(3, 5, 4, 1, 2)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(true, original.MoveOneBall.moveOneBall(Arrays.asList()));
	}

	
}