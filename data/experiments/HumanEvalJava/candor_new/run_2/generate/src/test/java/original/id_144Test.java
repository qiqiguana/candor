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
        assertTrue(Simplify.simplify(x, n));
    }
    
    @Test
        public void testNothing(){
            Simplify s = new Simplify();
            }
    @Test
    public void TestUncoveredBranchNonZeroRemainder() {
        // Given
        String x = "1/5";
        String n = "2/3";
        // When
        boolean result = Simplify.simplify(x, n);
        // Then
        assertFalse(result);
    }
                                    
}