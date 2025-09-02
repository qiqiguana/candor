package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Longest.
*/
class LongestTest {
    @Test
    void testLongest_EmptyList_ReturnsNull() {
        List<Object> strings = new ArrayList<>();
        String result = Longest.longest(strings);
        assertNull(result);
    }
    
    @Test
        public void testNothing(){
            Longest s = new Longest();
            }
    @Test
    public void test_Longest_string_in_a_list_with_single_element_1() {
        java.util.List<java.lang.Object> input = java.util.Arrays.asList("a");
        String expected = "a";
        org.junit.jupiter.api.Assertions.assertNotNull(Longest.longest(input));
        org.junit.jupiter.api.Assertions.assertEquals(expected, Longest.longest(input));
    }
    @Test
    public void testLongestWithTwoStringsOfSameLengthAndThirdShorterString3() {
        java.util.List<java.lang.Object> input = java.util.Arrays.asList("aa", "bb", "c");
        String expected = "aa";
        String actual = original.Longest.longest(input);
        org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
    }
                                    
}