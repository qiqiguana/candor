package original;

import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SmallestChange.
*/
class SmallestChangeTest {
    @Test
    void testSmallestChange_SimplePalindrome_ReturnsZero() {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 2, 1));
        assertEquals(0, SmallestChange.smallestChange(list));
    }
    
    @Test
        public void testNothing(){
            SmallestChange s = new SmallestChange();
            }
    @Test
    public void testSmallestChangeWithNonSymmetricArrayFixed1() {
        int[] input = new int[] {1, 2, 3};
        List<Integer> list = Arrays.stream(input).boxed().collect(Collectors.toList());
        int result = SmallestChange.smallestChange(list);
        assertEquals(1, result);
    }
                                    
}