package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of StrangeSortList.
*/
class StrangeSortListTest {
    @Test
    void testStrangeSortList_EmptyList_ReturnsEmptyList() {
        // Arrange
        List<Object> input = new ArrayList<>();
        
        // Act
        List<Object> result = StrangeSortList.strangeSortList(input);
        
        // Assert
        assertTrue(result.isEmpty());
    }
    
    @Test
        public void testNothing(){
            StrangeSortList s = new StrangeSortList();
            }
    @Test
    void testStrangeSortListWithSingleElement2() {
        List<Object> result = StrangeSortList.strangeSortList(Collections.singletonList(1));
        assertEquals(Collections.singletonList(1), result);
    }
    @Test
    void testStrangeSortListWithDuplicateElements_2() {
        java.util.List<java.lang.Object> result = StrangeSortList.strangeSortList(java.util.Arrays.asList(1, 2, 2, 3));
        java.util.Map<java.lang.Integer, java.lang.Integer> frequencyMap = new java.util.HashMap<>();
        for (Object value : result) {
            if (value instanceof Integer) {
                frequencyMap.put((Integer) value, frequencyMap.getOrDefault((Integer) value, 0) + 1);
            }
        }
        assertEquals(2, frequencyMap.get(2).intValue());
    }
    @Test
    void testStrangeSortListWithNullElements() {
        List<java.lang.Object> input = java.util.Arrays.asList(1, null, 3, null, 5);
        List<java.lang.Object> expected = java.util.Arrays.asList(1, 5, 3);
        assertEquals(expected, original.StrangeSortList.strangeSortList(input));
    }
                                    
}