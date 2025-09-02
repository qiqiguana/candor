package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Modp.
*/
class ModpTest {
	@Test
	void testModpPowerOfTwo() {
		// Test case: n = 3, p = 5
		int result = Modp.modp(3, 5);
		assertEquals(3, result);
	}
}