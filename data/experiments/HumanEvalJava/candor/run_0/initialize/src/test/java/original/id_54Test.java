package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SameChars.
*/
class SameCharsTest {
    @Test
    void testSameChars_ReturnsFalse_WhenInputStringsHaveDifferentCharacters() {
        // Arrange
        String s0 = "abcd";
        String s1 = "dddddddabce";

        // Act
        Boolean result = SameChars.sameChars(s0, s1);

        // Assert
        assertFalse(result);
    }
    
    @Test
        public void testNothing(){
            SameChars s = new SameChars();
            }
    @Test
    void testSameChars_EmptyStrings_ReturnTrue() {
        // Given
        String[] input = {"", ""};
        
        // When
        Boolean result = SameChars.sameChars(input[0], input[1]);
        
        // Then
        assertTrue(result);
    }
                                    
}