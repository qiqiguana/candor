package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AntiShuffle.
*/
class AntiShuffleTest {
    @Test
    void testAntiShuffle_EmptyString_ReturnsEmptyString() {
        // Arrange
        String input = "";
        String expected = "";
        
        // Act
        String result = AntiShuffle.antiShuffle(input);
        
        // Assert
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            AntiShuffle s = new AntiShuffle();
            }
                                    
}