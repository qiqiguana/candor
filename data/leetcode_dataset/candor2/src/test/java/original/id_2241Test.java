package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ATM.
*/
class ATMTest {
    @Test
    void testWithdrawSuccess() {
        // Arrange
        ATM atm = new ATM();
        atm.deposit(new int[] { 0, 0, 1, 0, 0 });
        int amountToWithdraw = 100;
        int[] expectedBanknotes = new int[] { 0, 0, 1, 0, 0 };

        // Act
        int[] actualBanknotes = atm.withdraw(amountToWithdraw);

        // Assert
        assertArrayEquals(expectedBanknotes, actualBanknotes);
    }
}