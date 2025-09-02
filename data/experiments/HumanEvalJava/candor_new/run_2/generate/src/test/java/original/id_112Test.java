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
    void testReverseDelete() {
        List<Object> result = ReverseDelete.reverseDelete("abcdedcba", "ab");
        assertEquals(true, result.get(1));
    }
    
    @Test
        public void testNothing(){
            ReverseDelete s = new ReverseDelete();
            }
    @Test
    public void testReverseDeleteNoCharactersToDeleteButResultNotPalindrome() {
        String[] input = {"abcdef", ""};
        List<Object> expectedResult = Arrays.asList("abcdef", false);
        assertEquals(expectedResult, ReverseDelete.reverseDelete(input[0], input[1]));
    }
                                    
}