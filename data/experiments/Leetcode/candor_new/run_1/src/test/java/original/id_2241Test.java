package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ATM.
*/
class ATMTest {
    @Test
    void testWithdraw_InsufficientFunds_ReturnsMinusOne() {
        // Arrange
        ATM atm = new ATM();
        int[] banknotesCount = {0, 0, 0, 0, 1};
        atm.deposit(banknotesCount);
        int amount = 501;

        // Act
        int[] result = atm.withdraw(amount);

        // Assert
        assertEquals(-1, result[0]);
    }
}