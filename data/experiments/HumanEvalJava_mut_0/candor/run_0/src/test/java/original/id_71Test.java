package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TriangleArea1.
*/
class TriangleArea1Test {
	@Test
	void testTriangleArea_IncorrectInput_ReturnsMinusOne() {
		// Arrange and Act
		Number result = TriangleArea1.triangleArea(2, 4, 7);
		// Assert
		assertEquals(-1, result);
	}
}
