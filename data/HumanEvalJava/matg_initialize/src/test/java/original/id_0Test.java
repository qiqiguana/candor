package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of HasCloseElements.
*/
class HasCloseElementsTest {
    @Test
    void testHasCloseElements_ThresholdNotMet_ReturnsFalse() {
        // Arrange
        List<Double> numbers = new ArrayList<>(List.of(1.0, 2.8, 3.0, 4.0, 5.0));
        Double threshold = 0.3;
        Boolean expectedResult = false;

        // Act
        Boolean result = HasCloseElements.hasCloseElements(numbers, threshold);

        // Assert
        assertNotEquals(expectedResult, result);
    }
}