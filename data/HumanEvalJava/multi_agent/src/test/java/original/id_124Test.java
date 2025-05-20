package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ValidDate.
*/
class ValidDateTest {

@Test
void testValidDate_EmptyString_ReturnsFalse() {
    String date = "";
    boolean result = ValidDate.validDate(date);
    assertFalse(result);
}
}