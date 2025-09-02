package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of LargestSmallestIntegers.
*/
class LargestSmallestIntegersTest {
	@Test
	void testLargestSmallestIntegers_NoNegativeOrPositive() {
		List<Object> input = new ArrayList<>();
		input.add(0);
		List<Integer> result = LargestSmallestIntegers.largestSmallestIntegers(input);
		assertEquals(null, result.get(0));
		assertEquals(null, result.get(1));
	}
}