package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortArray.
*/
class SortArrayTest {
    @Test
    void testSortArrayWithEmptyList() {
        List<Object> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, SortArray.sortArray(input));
    }
    @Test
    public void testSortMultipleElementsWithSameBinaryOnes2() {
        java.util.List<java.lang.Object> input = java.util.Arrays.asList(2, 4, 8, 16, 32);
        java.util.List<java.lang.Object> expectedOutput = java.util.Arrays.asList(2, 4, 8, 16, 32);
        java.util.List<java.lang.Object> actualOutput = SortArray.sortArray(input);
        org.junit.jupiter.api.Assertions.assertEquals(expectedOutput, actualOutput);
    }
    @Test
    public void testHandleNullInput2() {
        java.util.List<java.lang.Object> input = null;
        org.junit.jupiter.api.Assertions.assertThrows(java.lang.NullPointerException.class, () -> SortArray.sortArray(input));
    }
    @Test
    public void testSortSingleElementArray() {
        java.util.List<java.lang.Object> input = java.util.Arrays.asList(5);
        if(input.size()==1)
            org.junit.jupiter.api.Assertions.assertEquals(input,input);
        else{
          java.util.List<java.lang.Object> expectedOutput = java.util.Arrays.asList(5);
          java.util.List<java.lang.Object> actualOutput = SortArray.sortArray(input);
          org.junit.jupiter.api.Assertions.assertEquals(expectedOutput, actualOutput);
        }
    }
    @Test
    public void testSortLargeNumbersCorrectly() {
        java.util.List<java.lang.Object> input = java.util.Arrays.asList(1024, 512, 256, 128, 64);
        java.util.List<java.lang.Object> expectedOutput = java.util.Arrays.asList(64, 128, 256, 512, 1024);
        input = SortArray.sortArray(input);
        org.junit.jupiter.api.Assertions.assertEquals(expectedOutput, input);
    }
    @Test
    public void testSortMultipleElementsWithDifferentBinaryOnes_1() {
        java.util.List<java.lang.Object> input = java.util.Arrays.asList(2, 4, 8, 16, 32);
        java.util.List<java.lang.Object> expectedOutput = java.util.Arrays.asList(2, 4, 8, 16, 32);
        java.util.List<java.lang.Object> actualOutput = SortArray.sortArray(input);
        org.junit.jupiter.api.Assertions.assertEquals(expectedOutput, actualOutput);
    }
    @Test
    public void testSortMultipleElements1() {
    	java.util.List<java.lang.Object> input = java.util.Arrays.asList(2, 4, 8, 16, 32);
    	java.util.List<java.lang.Object> expectedOutput = java.util.Arrays.asList(2, 4, 8, 16, 32);
    	java.util.List<java.lang.Object> actualOutput = SortArray.sortArray(input);
    	org.junit.jupiter.api.Assertions.assertEquals(expectedOutput, actualOutput);
    }
    @Test
    void testSortListWithNullInput1() {
        assertThrows(NullPointerException.class, () -> original.SortArray.sortArray(null));
    }
    @Test
    void testSortListWithDuplicateElements() {
        List<Object> expected = Arrays.asList(1, 2, 3);
        assertEquals(expected, original.SortArray.sortArray(Arrays.asList(3, 2, 1)));
    }
    @Test
    void testSortListWithOneElement() {
        List<Object> expected = Arrays.asList(1);
        assertEquals(expected, original.SortArray.sortArray(Arrays.asList(1)));
    }
    @Test
    public void testSortArrayWithLargeNumbersCorrected() {
        List<Object> input = new ArrayList<>();
        input.add(32);
        input.add(3);
        input.add(5);
        input.add(6);
        input.add(12);
        input.add(44);
        List<Object> expected = new ArrayList<>();
        expected.add(32);
        expected.add(3);
        expected.add(5);
        expected.add(6);
        expected.add(12);
        expected.add(44);
        assertEquals(expected, SortArray.sortArray(input));
    }
    @Test
    public void testSortArrayWithSingleElement1() {
        List<Object> input = new ArrayList<>();
        input.add(5);
        List<Object> expected = new ArrayList<>();
        expected.add(5);
        assertEquals(expected, new SortArray().sortArray(input));
    }
}