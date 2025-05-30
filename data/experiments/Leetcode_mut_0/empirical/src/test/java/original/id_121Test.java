package original;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    @Test
	public void test1() throws Exception {
		assertEquals(9, original.Solution.solution(Arrays.asList(3, 3, 3, 3, 3)));
	}

	@Test
	public void test2() throws Exception {
		assertEquals(0, original.Solution.solution(Arrays.asList(30, 13, 24, 321)));
	}

	@Test
	public void test3() throws Exception {
		assertEquals(5, original.Solution.solution(Arrays.asList(5, 9)));
	}

	@Test
	public void test4() throws Exception {
		assertEquals(0, original.Solution.solution(Arrays.asList(2, 4, 8)));
	}

	@Test
	public void test5() throws Exception {
		assertEquals(23, original.Solution.solution(Arrays.asList(30, 13, 23, 32)));
	}

	@Test
	public void test6() throws Exception {
		assertEquals(3, original.Solution.solution(Arrays.asList(3, 13, 2, 9)));
	}

	
}