package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

/**
* Test class of CountDistinctCharacters.
*/
class CountDistinctCharactersTest {
    @Test
    void testCountDistinctCharacters_withMultipleUpperCaseAndLowerCaseLetters_ReturnsUniqueCharacterCount() {
        // Arrange
        String input = "Jerry jERRY JeRRRY";
        int expected = 5;
        // Act
        int actual = CountDistinctCharacters.countDistinctCharacters(input);
        // Assert
        assertEquals(expected, actual);
    }
    
    @Test
     void testNothing(){
         CountDistinctCharacters s = new CountDistinctCharacters();
         }
    @Test
    public void testEmptyString() {
        assertEquals(0, CountDistinctCharacters.countDistinctCharacters(""));
    }
    @Test
    public void testSingleCharacter() {
        assertEquals(1, CountDistinctCharacters.countDistinctCharacters("a"));
    }
    @Test
    public void testDuplicateCharacters() {
        assertEquals(1, CountDistinctCharacters.countDistinctCharacters("aaaAAA"));
    }
    @Test
    public void testMultipleDistinctCharacters() {
        assertEquals(5, CountDistinctCharacters.countDistinctCharacters("abcde"));
    }
    @Test
    public void testSpecialCharacters() {
        assertEquals(10, CountDistinctCharacters.countDistinctCharacters("!@#$%^&*()"));
    }
    @Test
    public void testNullString() {
        assertThrows(NullPointerException.class, () -> CountDistinctCharacters.countDistinctCharacters(null));
    }
                                  
}