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
    @Test
    public void testDepositSingleTypeOfBanknote() {
        ATM atm = new ATM();
        int[] banknotes = {1, 0, 0, 0, 0};
        try {
            atm.deposit(banknotes);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }
    @Test
    public void test_deposit_withdraw() {
        ATM s = new ATM();
        int[] banknotesCount = {0, 1, 2, 3, 4};
        s.deposit(banknotesCount);
        int[] result = s.withdraw(100);
        assertArrayEquals(new int[]{0, 0, 1, 0, 0}, result);
    }
}