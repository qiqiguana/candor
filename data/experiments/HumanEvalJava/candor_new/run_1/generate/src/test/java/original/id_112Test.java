package original;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ReverseDelete.
*/
class ReverseDeleteTest {
    @Test
    void testReverseDelete_Palindrome_ReturnTrue() {
        List<Object> result = ReverseDelete.reverseDelete("abcdedcba", "ab");
        assertTrue((Boolean) result.get(1));
    }
    
    @Test
        public void testNothing(){
            ReverseDelete s = new ReverseDelete();
            }
    @Test
    public void testReverseDeleteWithNonPalindromeResult() {
        String[] input = {"abcdef", "b"};
        List<Object> expectedResult = Arrays.asList("acdef", false);
        List<Object> actualResult = ReverseDelete.reverseDelete(input[0], input[1]);
        assertEquals(expectedResult, actualResult);
    }
                                    
}