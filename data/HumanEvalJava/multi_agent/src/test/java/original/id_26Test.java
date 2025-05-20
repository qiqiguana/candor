package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveDuplicates.
*/
class RemoveDuplicatesTest {
	@Test
	void testRemoveDuplicateShouldReturnListWithoutDuplicates() {
	    List<Object> numbers = new ArrayList<>();
	    numbers.add(1);
	    numbers.add(2);
	    numbers.add(3);
	    numbers.add(2);
	    numbers.add(4);
	    List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
	    assertEquals(3, result.size());
	}
}