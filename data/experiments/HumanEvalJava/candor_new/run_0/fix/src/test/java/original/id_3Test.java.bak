package original;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of BelowZero.
*/
class BelowZeroTest {
    @Test
    void testBelowZero_ReturnsTrue_WhenBalanceFallsBelowZero() {
        List<Object> operations = List.of(1, 2, -4, 5);
        assertTrue(BelowZero.belowZero(operations));
    }
    
    @Test
        public void testNothing(){
            BelowZero s = new BelowZero();
            }
    @Test
    public void testBelowZeroClassStaticMethodCall2() {
        java.util.List<java.lang.Object> operations = new java.util.ArrayList<>();
        Boolean result = original.BelowZero.belowZero(operations);
        assertTrue(result == null || !result);
    }
    @Test
    public void testInvalidOperationTypeFixed3() {
        List<Object> operations = new ArrayList<>();
        operations.add(1);
        operations.add("invalid");
        assertThrows(IllegalArgumentException.class, () -> BelowZero.belowZero(operations));
    }
    @Test
    public void testDoubleOperation() {
        List<Object> operations = new ArrayList<>();
        operations.add(1.5);
        assertFalse(BelowZero.belowZero(operations));
    }
                                    
}