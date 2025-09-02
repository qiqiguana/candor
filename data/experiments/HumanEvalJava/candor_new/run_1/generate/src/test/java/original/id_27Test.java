package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FlipCase.
*/
class FlipCaseTest {
    @Test
    void testFlipCase_withMixedCaseString_shouldReturnCorrectlyFlippedString() {
        // Arrange
        String input = "Hello!";
        String expectedOutput = "hELLO!";
        
        // Act
        String actualOutput = FlipCase.flipCase(input);
        
        // Assert
        assertEquals(expectedOutput, actualOutput);
    }
    
    @Test
        public void testNothing(){
            FlipCase s = new FlipCase();
            }
                                    
}