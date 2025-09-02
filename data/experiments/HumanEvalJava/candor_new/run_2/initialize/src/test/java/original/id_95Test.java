package original;

import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CheckDictCase.
*/
class CheckDictCaseTest {
    @Test
    void testCheckDictCase_AllKeysLowerCase() {
        Map<String, String> dict = Map.of("a", "apple", "b", "banana");
        assertTrue(CheckDictCase.checkDictCase(dict));
    }
}