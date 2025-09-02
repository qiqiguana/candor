package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Factorize.
*/
class FactorizeTest {
	@Test
	void testFactorizeSmallPrimeNumber() {
		List<Integer> result = Factorize.factorize(2);
		assertEquals(List.of(2), result);
	}
}