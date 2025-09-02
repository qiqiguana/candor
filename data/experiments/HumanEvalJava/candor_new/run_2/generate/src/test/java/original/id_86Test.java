package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AntiShuffle.
*/
class AntiShuffleTest {

    @Test
    void testAntiShuffle_SortCharactersInEachWord() {
        // Arrange
        String input = "Hello World!!!";
        String expected = "Hello !!!Wdlor";
        
        // Act
        String result = AntiShuffle.antiShuffle(input);
        
        // Assert
        assertEquals(expected, result);
    }
}