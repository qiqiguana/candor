package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of UniqueDigits.
*/
class UniqueDigitsTest {

    @Test
    void testUniqueDigits_NoEvenDigits_ReturnsSortedNumbers() {
        // Arrange
        List<Integer> input = new ArrayList<>();
        input.add(15);
        input.add(33);
        input.add(1422);
        input.add(1);
        List<Object> expected = new ArrayList<>();
        expected.add(1);
        expected.add(15);
        expected.add(33);

        // Act
        List<Object> result = UniqueDigits.uniqueDigits(input);

        // Assert
        assertEquals(expected, result);
    }
}