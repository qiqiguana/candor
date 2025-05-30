package original;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SmallestChange.
*/
class SmallestChangeTest {
    @Test
    void testSmallestChange_PalindromicArray_ReturnsZero() {
        // Arrange
        List<Integer> palindromicArray = List.of(1, 2, 3, 2, 1);

        // Act
        int result = SmallestChange.smallestChange(palindromicArray);

        // Assert
        assertEquals(0, result);
    }
    
    @Test
        public void testNothing(){
            SmallestChange s = new SmallestChange();
            }
    @Test
    public void testSmallestChange_NullInput() {
        assertThrows(NullPointerException.class, () -> SmallestChange.smallestChange(null));
    }
    @Test
    public void testSmallestChange_PalindromicArray_2() {
        List<Integer> arr = Arrays.asList(1, 2, 3, 4, 5, 4, 3, 2, 1);
        assertEquals(0, SmallestChange.smallestChange(arr));
    }
    @Test
    public void testSmallestChange_NonPalindromicArray() {
        List<Integer> arr = Arrays.asList(1, 2, 3, 5, 4, 7, 9, 6);
        assertEquals(4, SmallestChange.smallestChange(arr));
    }
    @Test
    public void testSmallestChange_SingleElementArray() {
        List<Integer> arr = Arrays.asList(1);
        assertEquals(0, SmallestChange.smallestChange(arr));
    }
    @Test
    public void testSmallestChange_TwoElementArray() {
        List<Integer> arr = Arrays.asList(0, 1);
        assertEquals(1, SmallestChange.smallestChange(arr));
    }
    @Test
    public void testSmallestChange_EmptyArray() {
        List<Integer> arr = Collections.emptyList();
        assertEquals(0, SmallestChange.smallestChange(arr));
    }
                                    
}