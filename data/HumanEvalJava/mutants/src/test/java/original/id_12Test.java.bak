package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
/**
* Test class of Longest.
*/
class LongestTest {
    @Test
    void testLongest_ReturnsFirstString_WhenMultipleStringsOfSameLength() {
        // Arrange
        List<Object> input = new ArrayList<>();
        input.add("abc");
        input.add("xyz");
        input.add("def");
        String expectedResult = "abc";

        // Act
        String result = Longest.longest(input);

        // Assert
        assertEquals(expectedResult, result);
    }
    
    @Test
        public void testNothing(){
            Longest s = new Longest();
            }
    @Test
    public void testEmptyList() {
        List<Object> input = new ArrayList<>();
        assertNull(Longest.longest(input));
    }
    @Test
    public void testSingleElementList() {
        List<Object> input = new ArrayList<>();
        input.add("a");
        assertEquals("a", Longest.longest(input));
    }
    @Test
    public void testMultipleElementsSameLength() {
        List<Object> input = new ArrayList<>();
        input.add("a");
        input.add("b");
        input.add("c");
        assertEquals("a", Longest.longest(input));
    }
    @Test
    public void testMultipleElementsDifferentLengths() {
        List<Object> input = new ArrayList<>();
        input.add("a");
        input.add("bb");
        input.add("ccc");
        assertEquals("ccc", Longest.longest(input));
    }
    @Test
    public void testEdgeCaseVeryLongString() {
        List<Object> input = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append('a');
        }
        input.add(sb.toString());
        assertEquals(sb.toString(), Longest.longest(input));
    }
                                    
}