package oracle1;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SortThird.
*/
class SortThirdTest {

    @Test
    void testSortThird() {
        List<Integer> input = new ArrayList<>(List.of(5, 6, 3, 4, 8, 9, 2));
        List<Integer> expectedOutput = new ArrayList<>(List.of(2, 6, 3, 4, 8, 9, 5));
        assertEquals(expectedOutput, SortThird.sortThird(input));
    }
    
    @Test
        void testNothing(){
            SortThird s = new SortThird();
            }
    @Test
    public void testSortThird_EmptyList() {
        List<Integer> l = new ArrayList<>();
        assertEquals(new ArrayList<>(), SortThird.sortThird(l));
    }
    @Test
    public void testSortThird_NullPointerException() {
        List<Integer> l = null;
        assertThrows(NullPointerException.class, () -> SortThird.sortThird(l));
    }
    @Test
    public void testSortThird_SingleElementList() {
        List<Integer> l = Collections.singletonList(1);
        assertEquals(Collections.singletonList(1), SortThird.sortThird(l));
    }
                                    

}