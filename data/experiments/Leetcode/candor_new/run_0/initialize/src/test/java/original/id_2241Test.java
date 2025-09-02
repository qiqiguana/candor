package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ATM.
*/
class ATMTest {

    @Test
    void testWithdraw() {
        // arrange
        ATM atm = new ATM();
        int amount = 1000;
        boolean result;
        // act
        int[] withdrawResult = atm.withdraw(amount);
        // assert
        assertNotNull(withdrawResult);
    }
}
