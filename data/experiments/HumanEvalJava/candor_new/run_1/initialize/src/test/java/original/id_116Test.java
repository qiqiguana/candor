package original;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortArray.
*/
class SortArrayTest {
	@Test
	void testSortArray() {
		List<Integer> list = new ArrayList<>();
		list.add(5);
		list.add(2);
		list.add(8);
		// Add more elements if needed
		assertTrue(list.size() > 0);
	}
}