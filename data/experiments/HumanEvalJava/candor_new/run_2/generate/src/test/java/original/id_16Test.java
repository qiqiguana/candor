package original;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountDistinctCharacters.
*/
class CountDistinctCharactersTest {
    @Test
    void countDistinctCharacters_DistinctChars_ReturnsCount() {
        // Arrange
        String input = "abcde";
        int expectedCount = 5;

        // Act
        int actualCount = CountDistinctCharacters.countDistinctCharacters(input);

        // Assert
        assertEquals(expectedCount, actualCount);
    }
    
    @Test
        public void testNothing(){
            CountDistinctCharacters s = new CountDistinctCharacters();
            }
                                    
}