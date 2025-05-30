package oracle1;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of BelowZero.
*/
class BelowZeroTest {

@Test
void testBelowZero_BalanceNeverFallsBelowZero_ReturnsFalse() {
    List<Object> operations = new ArrayList<>();
    operations.add(1);
    operations.add(2);
    operations.add(3);
    assertFalse(BelowZero.belowZero(operations));
}

@Test
    void testNothing(){
        BelowZero s = new BelowZero();
        }
@Test
public void testEmptyList() {
    List<Object> operations = new ArrayList<>();
    assertFalse(BelowZero.belowZero(operations));
}
@Test
public void testNullInput() {
    List<Object> operations = null;
    assertThrows(NullPointerException.class, () -> BelowZero.belowZero(operations));
}
@Test
public void testSinglePositiveOperationFixed() {
    java.util.List<java.lang.Object> operations = java.util.Arrays.asList(1);
    assertFalse(oracle1.BelowZero.belowZero(operations));
}
@Test
public void testMixedOperationsWithBalanceBelowZeroCorrected_1() {
    List<Object> operations = Arrays.asList(1, -3.0, -1);
    assertTrue(BelowZero.belowZero(operations));
}
@Test
public void testMixedOperationsWithBalanceBelowZeroCorrected() {
    List<Object> operations = Arrays.asList(1, -3, -1);
    assertTrue(BelowZero.belowZero(operations));
}
@Test
public void testInvalidInputFixed() {
    List<Object> operations = Arrays.asList("string");
    assertThrows(IllegalArgumentException.class, () -> BelowZero.belowZero(operations));
}
                                
}