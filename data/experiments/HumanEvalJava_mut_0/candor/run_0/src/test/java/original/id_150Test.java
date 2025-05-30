package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of XOrY.
*/
class XOrYTest {
	@Test
	void testXOrY_PrimeNumber_ReturnsX() {
		// Arrange
		int n = 5; // A prime number
		int x = 10;
		int y = 20;
		// Act
		int result = XOrY.xOrY(n, x, y);
		// Assert
		assertEquals(x, result);
	}
}