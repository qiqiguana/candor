package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringXor.
*/
class StringXorTest {
    @Test
    void test_stringXor_withDifferentInputs_shouldReturnCorrectResult() {
        // Given
        String a = "010";
        String b = "110";

        // When
        String result = StringXor.stringXor(a, b);

        // Then
        assertEquals("100", result);
    }
    
    @Test
        public void testNothing(){
            StringXor s = new StringXor();
            }
                                    
}