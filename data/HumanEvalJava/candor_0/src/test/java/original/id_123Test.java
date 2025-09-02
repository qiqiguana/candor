package original;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetOddCollatz.
*/
class GetOddCollatzTest {
    @Test
    void testGetOddCollatz() {
        int n = 5;
        List<Integer> result = GetOddCollatz.getOddCollatz(n);
        assertTrue(result.contains(1));
        assertTrue(result.contains(5));
        assertEquals(2, result.size());
    }
    
    @Test
        public void testNothing(){
            GetOddCollatz s = new GetOddCollatz();
            }
    @Test
    public void TestGetOddCollatz_HappyPath_1() {
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        assertEquals(expected, GetOddCollatz.getOddCollatz(1));
    }
    @Test
    public void TestGetOddCollatz_HappyPath_5() {
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(5);
        assertEquals(expected, GetOddCollatz.getOddCollatz(5));
    }
    @Test
    public void TestGetOddCollatz_HappyPath_14() {
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(5);
        expected.add(7);
        expected.add(11);
        expected.add(13);
        expected.add(17);
        assertEquals(expected, GetOddCollatz.getOddCollatz(14));
    }
                                    
}