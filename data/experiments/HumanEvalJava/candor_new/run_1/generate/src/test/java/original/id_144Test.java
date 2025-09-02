package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Simplify.
*/
class SimplifyTest {
    @Test
    void testSimplify() {
        String x = "1/5";
        String n = "5/1";
        Boolean result = Simplify.simplify(x, n);
        assertTrue(result);
    }
    
    @Test
        public void testNothing(){
            Simplify s = new Simplify();
            }
    @Test
    public void testSimplifyMethodFalseResult() {
        String[] input = {"1/6", "2/1"};
        Boolean expectedResult = Boolean.FALSE;
        assertEquals(expectedResult, Simplify.simplify(input[0], input[1]));
    }
                                    
}