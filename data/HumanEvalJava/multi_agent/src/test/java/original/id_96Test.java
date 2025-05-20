package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountUpTo.
*/
class CountUpToTest {

@Test
void testCountUpTo_WhenInputIs5_ReturnsListOfPrimesLessThan5() {
        // Arrange
        int input = 5;
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(3);

        // Act
        List<Object> result = CountUpTo.countUpTo(input);

        // Assert
        assertEquals(expected, result);
    }
}