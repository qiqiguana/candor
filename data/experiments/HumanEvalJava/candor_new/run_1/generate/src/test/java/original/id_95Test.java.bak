package original;

import java.util.HashMap;
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
    
    @Test
        public void testNothing(){
            CheckDictCase s = new CheckDictCase();
            }
    @Test
    public void testCheckDictCaseWithMixedCaseKeys() {
        Map<String, String> dict = Map.of("a", "apple", "A", "banana");
        assertFalse(CheckDictCase.checkDictCase(dict));
    }
    @Test
    public void testCheckDictCaseWithNonMapInput() {
        String str = "hello";
        assertFalse(CheckDictCase.checkDictCase(str));
    }
    @Test
    public void testNonStringKeyImmediateReturn() {
        Map<Object, String> dict = new HashMap<>();
        dict.put(1, "value");
        Object originalDict = dict;
        assertFalse(CheckDictCase.checkDictCase(originalDict));
    }
    @Test
    public void testAllUppercaseKeys() {
        Map<String, String> dict = new HashMap<>();
        dict.put("A", "apple");
        dict.put("B", "banana");
        assertTrue(CheckDictCase.checkDictCase(dict));
    }
                                    
}