package oracle1;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AllPrefixes.
*/
class AllPrefixesTest {
	@Test
    void testAllPrefixesEmptyString() {
        List<Object> actual = AllPrefixes.allPrefixes("");
        assertTrue(actual.isEmpty());
    }
 
 @Test
     void testNothing(){
         AllPrefixes s = new AllPrefixes();
         }
 @Test
 public void testNullInput() {
 	assertThrows(NullPointerException.class, () -> AllPrefixes.allPrefixes(null));
 }
 @Test
 public void testEmptyString() {
     List<Object> result = AllPrefixes.allPrefixes("");
     assertTrue(result.isEmpty());
 }
 @Test
 public void testSingleCharacterString() {
     List<Object> result = AllPrefixes.allPrefixes("a");
     assertEquals(1, result.size());
     assertEquals("a", result.get(0));
 }
 @Test
 public void testShortString() {
     List<Object> result = AllPrefixes.allPrefixes("abc");
     assertEquals(3, result.size());
     assertEquals("a", result.get(0));
     assertEquals("ab", result.get(1));
     assertEquals("abc", result.get(2));
 }
 @Test
 public void testLongString() {
     List<Object> result = AllPrefixes.allPrefixes("abcdefghijklmnopqrstuvwxyz");
     assertEquals(26, result.size());
     for (int i = 0; i < 26; i++) {
         String expected = "abcdefghijklmnopqrstuvwxyz".substring(0, i + 1);
         assertEquals(expected, result.get(i));
     }
 }
 @Test
 public void testNullString() {
     assertThrows(NullPointerException.class, () -> AllPrefixes.allPrefixes(null));
 }
 @Test
 void testAllPrefixes_SingleCharacterString() {
     List<Object> expected = new ArrayList<>();
     expected.add("a");
     assertEquals(expected, AllPrefixes.allPrefixes("a"));
 }
                                 
}