package original;

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
    void testUniqueDigits_NoEvenDigits_ReturnSortedNumbers() {
        List<Integer> input = new ArrayList<>();
        input.add(15);
        input.add(33);
        input.add(1422);
        input.add(1);
        List<Object> expectedOutput = new ArrayList<>();
        expectedOutput.add(1);
        expectedOutput.add(15);
        expectedOutput.add(33);

        Collections.sort(expectedOutput, new Comparator<Object>() {
            public int compare(Object a, Object b) {
                return (Integer) a - (Integer) b;
            }
        });

        List<Object> result = UniqueDigits.uniqueDigits(input);

        assertEquals(expectedOutput, result);
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
    public void testUniqueDigits_OnlyOddNumbers() {
        List<Integer> input = new ArrayList<>(Arrays.asList(15, 33, 111));
        List<Object> expected = new ArrayList<>(Arrays.asList(15, 33, 111));
        assertEquals(expected, UniqueDigits.uniqueDigits(input));
    }
                                    
}