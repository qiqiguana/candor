package original;

import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CheckDictCase.
*/
class CheckDictCaseTest {
    @Test
    void checkDictCase_AllKeysAreStringsInLowerCase_ReturnsTrue() {
        Map<String, String> dict = Map.of("a", "apple", "b", "banana");
        assertTrue(CheckDictCase.checkDictCase(dict));
    }
    
    @Test
        public void testNothing(){
            CheckDictCase s = new CheckDictCase();
            }
    @Test
    public void test_null_input() {
        Object dict = null;
        assertFalse(CheckDictCase.checkDictCase(dict));
    }
                                    
}