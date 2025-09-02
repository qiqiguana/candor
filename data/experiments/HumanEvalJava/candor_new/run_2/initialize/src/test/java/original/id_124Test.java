package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ValidDate.
*/
class ValidDateTest {

    @Test
    void testValidDateFormat() {
        String date = "03-11-2000";
        assertTrue(ValidDate.validDate(date));
    }
}