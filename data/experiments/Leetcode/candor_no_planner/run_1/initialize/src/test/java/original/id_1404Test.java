package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1404.
*/
class Solution1404Test {
	@Test
	void testNumSteps_Simple() {
		Solution1404 solution = new Solution1404();
		assertEquals(6, solution.numSteps("1101"));
	}
}