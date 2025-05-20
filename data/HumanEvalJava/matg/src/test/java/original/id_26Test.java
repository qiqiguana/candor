package original;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of RemoveDuplicates.
*/
class RemoveDuplicatesTest {
    @Test
    void removeDuplicates_should_ReturnEmptyList_WhenInputIsEmpty() {
        List<Object> numbers = new ArrayList<>();
        List<Object> result = RemoveDuplicates.removeDuplicates(numbers);
        assertTrue(result.isEmpty());
    }
    
    @Test
        void testNothing(){
            RemoveDuplicates s = new RemoveDuplicates();
            }
    @Test
    void RemoveDuplicates_NullList_ThrowsNullPointerException() {
        List<Object> numbers = null;
        assertThrows(NullPointerException.class, () -> RemoveDuplicates.removeDuplicates(numbers));
    }
    @Test
    void RemoveDuplicates_EmptyList_ReturnsEmptyList_1() {
        List<Object> numbers = new ArrayList<>();
        assertEquals(java.util.Collections.emptyList(), RemoveDuplicates.removeDuplicates(numbers));
    }
    @Test
    void RemoveDuplicates_NoDuplicates_ReturnsOriginalList_1() {
        java.util.List<java.lang.Object> numbers = java.util.Arrays.asList(1, 2, 3, 4);
        java.util.List<java.lang.Object> expectedNumbers = java.util.Arrays.asList(1, 2, 3, 4);
        org.junit.jupiter.api.Assertions.assertEquals(expectedNumbers, original.RemoveDuplicates.removeDuplicates(numbers));
    }
    @Test
    void RemoveDuplicates_MultipleDuplicates_ReturnsListWithoutDuplicates_2() {
        List<Object> numbers = Arrays.asList(1, 2, 3, 2, 4, 3);
        assertEquals(Arrays.asList(1, 4), RemoveDuplicates.removeDuplicates(numbers));
    }
                                    
}