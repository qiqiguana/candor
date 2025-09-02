package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsHappy.
*/
class IsHappyTest {
    @Test
    void test_isHappy_ReturnsFalseForStringWithLessThan3Characters() {
        // Arrange
        String input = "a";
        Boolean expectedResult = false;

        // Act
        Boolean actualResult = IsHappy.isHappy(input);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}
