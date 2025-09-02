package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of DecimalToBinary.
*/
class DecimalToBinaryTest {
	@Test
	void testDecimalToBinary_15() {
		String expected = "db1111db";
		String actual = DecimalToBinary.decimalToBinary(15);
		assertEquals(expected, actual);
	}
}