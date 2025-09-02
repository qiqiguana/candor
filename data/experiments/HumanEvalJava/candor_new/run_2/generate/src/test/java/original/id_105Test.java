package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ByLength.
*/
class ByLengthTest {
    @Test
    void testByLength_withValidInput_shouldReturnCorrectResult() {
        // Arrange
        List<Object> input = new ArrayList<>();
        input.add(2);
        input.add(1);
        input.add(1);
        input.add(4);
        input.add(5);
        input.add(8);
        input.add(2);
        input.add(3);
        List<Object> expected = new ArrayList<>();
        expected.add("Eight");
        expected.add("Five");
        expected.add("Four");
        expected.add("Three");
        expected.add("Two");
        expected.add("Two");
        expected.add("One");
        expected.add("One");

        // Act
        List<Object> actual = ByLength.byLength(input);

        // Assert
        assertEquals(expected, actual);
    }
}