package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringSequence.
*/
class StringSequenceTest {
    @Test
    void testStringSequence_shouldReturnNumbersFrom0ToN() {
        // Given
        int n = 5;
        // When
        String result = StringSequence.stringSequence(n);
        // Then
        assertEquals("0 1 2 3 4 5", result);
    }
    
    @Test
        public void testNothing(){
            StringSequence s = new StringSequence();
            }
                                    
}