package original;

import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CheckDictCase.
*/
class CheckDictCaseTest {
    @Test
    void testCheckDictCaseAllLower() {
        Map<String, String> dict = Map.of("p", "pineapple", "b", "banana");
        assertTrue(CheckDictCase.checkDictCase(dict));
    }
}