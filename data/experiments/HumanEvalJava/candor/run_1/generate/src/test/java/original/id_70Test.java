package original;

import java.util.Arrays;
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
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, StrangeSortList.strangeSortList(input));
    }
    
    @Test
        public void testNothing(){
            StrangeSortList s = new StrangeSortList();
            }
    @Test
    public void TestStrangeSortListWithEmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expectedOutput = new ArrayList<>();
        assertEquals(expectedOutput, StrangeSortList.strangeSortList(input));
    }
    @Test
    public void TestStrangeSortListWithPositiveNumbers() {
        List<Object> input = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        List<Object> expectedOutput = new ArrayList<>(Arrays.asList(1, 4, 2, 3));
        assertEquals(expectedOutput, StrangeSortList.strangeSortList(input));
    }
    @Test
    public void TestStrangeSortListWithNegativeNumbers() {
        List<Object> input = new ArrayList<>(Arrays.asList(-5, -2, -8, -1));
        List<Object> expectedOutput = new ArrayList<>(Arrays.asList(-8, -1, -5, -2));
        assertEquals(expectedOutput, StrangeSortList.strangeSortList(input));
    }
    @Test
    public void TestStrangeSortListWithSingleElement() {
        List<Object> input = new ArrayList<>(java.util.Arrays.asList(111111));
        List<Object> expectedOutput = new ArrayList<>(java.util.Arrays.asList(111111));
        assertEquals(expectedOutput, StrangeSortList.strangeSortList(input));
    }
    @Test
    public void TestStrangeSortListWithNonIntegerValues() {
        List<Object> input = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<Object> expectedOutput = new ArrayList<>();
        assertEquals(expectedOutput, StrangeSortList.strangeSortList(input));
    }
    @Test
    public void TestStrangeSortListWithDuplicateValuesCorrected() {
        List<Object> input = new ArrayList<>(Arrays.asList(1, 2, 2, 3));
        List<Object> expectedOutput = new ArrayList<>(Arrays.asList(1, 3, 2, 2));
        assertEquals(expectedOutput, StrangeSortList.strangeSortList(input));
    }
    @Test
    public void TestStrangeSortListWithNegativeAndPositiveValuesCorrected() {
        List<Object> input = new ArrayList<>(Arrays.asList(-5, -2, 8, 1));
        List<Object> expectedOutput = new ArrayList<>(Arrays.asList(-5, 8, -2, 1));
        assertEquals(expectedOutput, StrangeSortList.strangeSortList(input));
    }
                                    
}