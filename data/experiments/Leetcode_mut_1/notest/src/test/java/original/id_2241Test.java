package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ATM.
*/
class ATMTest {
    @Test
    void testWithdraw() {
        // Arrange
        ATM atm = new ATM();
        int[] banknotesCount = {1, 2, 3, 4, 5};
        atm.deposit(banknotesCount);
        // Act
        int[] result = atm.withdraw(200);
        // Assert
        assertArrayEquals(new int[]{0, 0, 0, 1, 0}, result);
    }
}