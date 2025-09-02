package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Simplify.
*/
class SimplifyTest {
    @Test
    void test_simplify() {
        assertTrue(Simplify.simplify("1/5", "5/1"));
    }
    
    @Test
        public void testNothing(){
            Simplify s = new Simplify();
            }
    @Test
    public void testFalseResultForNonWholeNumberProduct() {
        String[] inputs = {"1/2", "3/4"};
        boolean result = original.Simplify.simplify(inputs[0], inputs[1]);
        assertFalse(result);
    }
                                    
}