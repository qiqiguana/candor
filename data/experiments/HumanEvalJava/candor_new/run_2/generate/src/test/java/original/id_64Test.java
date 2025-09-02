package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of VowelsCount.
*/
class VowelsCountTest {

    @Test
    void testVowelsCount_withWordContainingVowelsAndYAtTheEnd_returnsCorrectCount() {
        // Arrange
        String word = "ACEDY";
        int expectedCount = 3;

        // Act
        int actualCount = VowelsCount.vowelsCount(word);

        // Assert
        assertEquals(expectedCount, actualCount);
    }
}