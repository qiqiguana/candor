package original;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    
    @Test
        public void testNothing(){
            CheckDictCase s = new CheckDictCase();
            }
    @Test
    public void testCheckDictCase_NonMapInput() {
        Object dict = 5;
        assertFalse(CheckDictCase.checkDictCase(dict));
    }
    @Test
    public void testCheckDictCase_MixedKeyTypes_Fixed_2() {
        Map<Object, String> dict = new HashMap<>();
        dict.put("a", "apple");
        dict.put(5, "banana");
        Object result = CheckDictCase.checkDictCase(dict);
        assertFalse((Boolean)result);
    }
    @Test
    public void testCheckDictCase_MixedCaseKeys() {
        Map<String, String> dict = new HashMap<>();
        dict.put("a", "apple");
        dict.put("B", "banana");
        assertFalse(CheckDictCase.checkDictCase(dict));
    }
    @Test
    public void test_checkDictCase_with_empty_dictionary() {
        Map<Object, Object> dict = new HashMap<>();
        assertFalse(CheckDictCase.checkDictCase(dict));
    }
    @Test
    public void test_checkDictCase_with_all_keys_in_upper_case() {
        Map<Object, Object> dict = Map.of("A", "apple", "B", "banana");
        assertFalse(CheckDictCase.checkDictCase(dict));
    }
                                    
}