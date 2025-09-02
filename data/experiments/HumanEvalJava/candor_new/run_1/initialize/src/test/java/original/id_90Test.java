package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of NextSmallest.
*/
class NextSmallestTest {

@Test
void testNextSmallest_withMultipleElements_ReturnsSecondSmallest() {
List<Object> input = List.of(1, 2, 3, 4, 5);
Integer expectedOutput = 2;
Integer actualOutput = NextSmallest.nextSmallest(input);
assertEquals(expectedOutput, actualOutput);
}
}