package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ValidDate.
*/
class ValidDateTest {

    @Test
    void testValidDate() {
        String date = "03-11-2000";
        boolean expectedResult = true;
        boolean actualResult = ValidDate.validDate(date);
        assertEquals(expectedResult, actualResult);
    }
}