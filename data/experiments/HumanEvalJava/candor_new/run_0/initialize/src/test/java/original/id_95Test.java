package original;

import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CheckDictCase.
*/
class CheckDictCaseTest {
    @Test
    void testCheckDictCase_AllKeysLowerCase_ReturnsTrue() {
        Map<String, String> dict = Map.of("apple", "fruit", "banana", "fruit");
        assertTrue(CheckDictCase.checkDictCase(dict));
    }
}