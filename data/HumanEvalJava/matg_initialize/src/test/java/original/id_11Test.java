package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringXor.
*/
class StringXorTest {

    @Test
    void testStringXor_SameLengthStrings_ReturnsCorrectResult() {
        // Arrange
        String a = "010";
        String b = "110";
        String expectedResult = "100";

        // Act
        String actualResult = StringXor.stringXor(a, b);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}