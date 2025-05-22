package oracle1;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CheckDictCase.
*/
class CheckDictCaseTest {
    @Test
    void testCheckDictCase_AllLowerKeys_ReturnsTrue() {
        Map<String, String> dict = Map.of("a", "apple", "b", "banana");
        assertTrue(CheckDictCase.checkDictCase(dict));
    }
    
    @Test
        void testNothing(){
            CheckDictCase s = new CheckDictCase();
            }
    @Test
    public void TestNullInput() {
    	assertFalse(CheckDictCase.checkDictCase(null));
    }
    @Test
    public void TestNonMapInput() {
    	assertFalse(CheckDictCase.checkDictCase("apple"));
    }
    @Test
    public void TestAllLowercaseKeys2() {
        java.util.HashMap<String, String> map = new java.util.HashMap<>();
        map.put("a", "apple");
        map.put("b", "banana");
        assertTrue(CheckDictCase.checkDictCase(map));
    }
    @Test
    public void testNullInput() {
        assertThrows(NullPointerException.class, () -> CheckDictCase.checkDictCase(null));
    }
    @Test
    public void testNonMapInput() {
    	String str = "Hello";
    	assertFalse(CheckDictCase.checkDictCase(str));
    }
    @Test
    void testEmptyDictionaryCorrected() {
        Object dict = new java.util.HashMap<>();
        Boolean result = CheckDictCase.checkDictCase(dict);
        org.junit.jupiter.api.Assertions.assertFalse(result);
    }
    @Test
    public void testEmptyDictionary() {
        java.util.Map<java.lang.String, java.lang.Object> dict = new java.util.HashMap<>();
        assertFalse(CheckDictCase.checkDictCase(dict));
    }
    @Test
    public void testAllLowercaseKeys() {
        Map<String, String> dict = new HashMap<>();
        dict.put("a", "apple");
        dict.put("b", "banana");
        assertTrue(CheckDictCase.checkDictCase(dict));
    }
    @Test
    public void testAllUppercaseKeysFixed() {
        Map<String, String> dict = new HashMap<>();
        dict.put("STATE", "NC");
        dict.put("ZIP", "12345");
        assertTrue(CheckDictCase.checkDictCase(dict));
    }
    @Test
    public void testMixedCaseKeys() {
        Map<String, String> dict = new HashMap<>();
        dict.put("a", "apple");
        dict.put("A", "banana");
        dict.put("B", "baz");
        assertFalse(CheckDictCase.checkDictCase(dict));
    }
    @Test
    public void test_checkDictCase_allLower1() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "apple");
        map.put("b", "banana");
        assertTrue(CheckDictCase.checkDictCase(map));
    }
    @Test
    public void test_checkDictCase_allUpper2() {
        Map<String, String> map = new HashMap<>();
        map.put("A", "apple");
        map.put("B", "banana");
        assertTrue(CheckDictCase.checkDictCase(map));
    }
    @Test
    public void test_checkDictCase_mixed3() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "apple");
        map.put("A", "banana");
        map.put("B", "banana");
        assertFalse(CheckDictCase.checkDictCase(map));
    }
    @Test
    public void test_checkDictCase_nonString4() {
        Map<Object, String> map = new HashMap<>();
        map.put("a", "apple");
        map.put(1, "banana");
        assertFalse(CheckDictCase.checkDictCase(map));
    }
    @Test
    void testEmptyDictionary1() {
        Map<String, String> dict = new HashMap<>();
        assertFalse(CheckDictCase.checkDictCase(dict));
    }
                                    
}