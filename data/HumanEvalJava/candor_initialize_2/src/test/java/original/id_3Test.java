package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of BelowZero.
*/
class BelowZeroTest {
    @Test
    void testBelowZero_BalanceNeverGoesBelowZero() {
        List<Object> operations = new ArrayList<>();
        operations.add(1);
        operations.add(2);
        operations.add(3);
        assertFalse(BelowZero.belowZero(operations));
    }
    
    @Test
        public void testNothing(){
            BelowZero s = new BelowZero();
            }
    @Test
    public void testEmptyList() {
        List<Object> operations = new ArrayList<>();
        assertFalse(BelowZero.belowZero(operations));
    }
    @Test
    public void testOperationsWithInvalidInputFixed() {
        List<Object> operations = null;
        assertThrows(NullPointerException.class, () -> BelowZero.belowZero(operations));
    }
    @Test
    public void Positive_Test_Below_Zero() {
    	List<Object> operations = new ArrayList<>();
    	operations.add(1);
    	operations.add(2);
    	operations.add(-4);
    	operations.add(5);
    	assertTrue(BelowZero.belowZero(operations));
    }
    @Test
    public void Negative_Test_No_Operations() {
    	List<Object> operations = new ArrayList<>();
    	assertFalse(BelowZero.belowZero(operations));
    }
    @Test
    public void Edge_Case_Test_Single_Deposit() {
    	List<Object> operations = new ArrayList<>();
    	operations.add(10);
    	assertFalse(BelowZero.belowZero(operations));
    }
    @Test
    public void Edge_Case_Test_Single_Withdrawal() {
    	List<Object> operations = new ArrayList<>();
    	operations.add("a");
    	assertThrows(IllegalArgumentException.class, () -> BelowZero.belowZero(operations));
    }
    @Test
    public void Specific_Functionality_Test_Multiple_Deposits_And_Withdrawals() {
    	List<Object> operations = new ArrayList<>();
    	operations.add(1);
    	operations.add(2);
    	operations.add(-3);
    	operations.add(4);
    	operations.add(-5);
    	assertTrue(BelowZero.belowZero(operations));
    }
    @Test
    public void Specific_Functionality_Test_Balance_Never_Falls_Below_Zero() {
    	List<Object> operations = new ArrayList<>();
    	operations.add(10);
    	operations.add(20);
    	operations.add(30);
    	assertFalse(BelowZero.belowZero(operations));
    }
    @Test
    public void testBelowZeroWithPositiveBalance() {
    	List<Object> operations = List.of(1.0, 2.0, 3.0);
    	assertFalse(BelowZero.belowZero(operations));
    }
                                    
}