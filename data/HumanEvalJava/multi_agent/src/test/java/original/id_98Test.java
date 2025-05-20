package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountUpper.
*/
class CountUpperTest {
    @Test
    void testCountUpper_EvenIndices_UppercaseVowels_ReturnsCorrectCount() {
        // Arrange
        String input = "aBCdEf";
        int expectedResult = 1;

        // Act
        int actualResult = CountUpper.countUpper(input);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}
