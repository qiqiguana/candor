package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsBored.
*/
class IsBoredTest {
    @Test
    void isBored_ReturnsZero_WhenNoSentencesStartWithI() {
        // Arrange
        String input = "Hello world";

        // Act
        int result = IsBored.isBored(input);

        // Assert
        assertEquals(0, result);
    }
    
    @Test
        public void testNothing(){
            IsBored s = new IsBored();
            }
    @Test
    public void testEmptyString() {
        int result = IsBored.isBored("");
        assertEquals(0, result);
    }
    @Test
    public void testIAtBeginningOfSentence() {
        int result = IsBored.isBored("I am bored");
        assertEquals(1, result);
    }
                                    
}