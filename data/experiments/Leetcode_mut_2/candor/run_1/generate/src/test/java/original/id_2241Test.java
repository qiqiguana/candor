package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ATM.
*/
class ATMTest {
    @Test
void testWithdrawWithExactMatch() {
        // Arrange
        ATM atm = new ATM();
        atm.deposit(new int[] { 1, 2, 3, 4, 5 });
        int amountToWithdraw = 250;

        // Act
int[] withdrawnNotes = atm.withdraw(amountToWithdraw);

        // Assert
assertEquals(1, withdrawnNotes[3]);
}
}