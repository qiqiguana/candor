package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringSequence.
*/
class StringSequenceTest {
    @Test
    void testStringSequence_Range0_5_ReturnsCorrectSequence() {
        // Arrange and Act
        String result = StringSequence.stringSequence(5);
        // Assert
        assertEquals("0 1 2 3 4 5", result);
    }
    
    @Test
        public void testNothing(){
            StringSequence s = new StringSequence();
            }
                                    
}