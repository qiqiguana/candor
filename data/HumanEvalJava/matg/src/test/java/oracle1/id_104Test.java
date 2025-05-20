package oracle1;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.Collections;

import java.util.Comparator;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of UniqueDigits.
*/
class UniqueDigitsTest {
    @Test
    void testUniqueDigits_HasEvenDigit_ReturnsEmptyList() {
        List<Integer> input = new ArrayList<>();
        input.add(152);
        input.add(323);
        input.add(1422);
        input.add(10);
        List<Object> expected = Collections.emptyList();
        assertEquals(expected, UniqueDigits.uniqueDigits(input));
    }
    
    @Test
        public void testNothing(){
            UniqueDigits s = new UniqueDigits();
            }
    @Test
    public void testUniqueDigits_EmptyList() {
        List<Integer> input = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, UniqueDigits.uniqueDigits(input));
    }
    @Test
    public void testUniqueDigits_NullInput() {
        assertThrows(NullPointerException.class, () -> UniqueDigits.uniqueDigits(null));
    }
    @Test
    public void testUniqueDigits_ListWithUniqueDigits() {
        List<Integer> input = Arrays.asList(15, 33, 1422, 1);
        List<Object> expected = Arrays.asList(1, 15, 33);
        assertEquals(expected, UniqueDigits.uniqueDigits(input));
    }
                                    
}