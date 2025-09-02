package original;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SmallestChange.
*/
class SmallestChangeTest {
    @Test
    void testSmallestChange_SimplePalindrome_ReturnsZero() {
        List<Integer> arr = List.of(1, 2, 3, 2, 1);
        int result = SmallestChange.smallestChange(arr);
        assertEquals(0, result);
    }
    
    @Test
        public void testNothing(){
            SmallestChange s = new SmallestChange();
            }
    @Test
    public void testSmallestChangeMethodWithMismatchedElements() {
        List<Integer> arr = Arrays.asList(1, 2, 3, 5, 4, 7, 9, 6);
        int expected = 4;
        assertEquals(expected, SmallestChange.smallestChange(arr));
    }
                                    
}