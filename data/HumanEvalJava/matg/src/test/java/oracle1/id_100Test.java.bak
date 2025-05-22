package oracle1;

import oracle1.MakeAPile;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MakeAPile.
*/
class MakeAPileTest {
    @Test
    void testMakeAPile_OddNumber() {
        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(5);
        expected.add(7);
        assertEquals(expected, MakeAPile.makeAPile(3));
    }
    
    @Test
        void testNothing(){
            MakeAPile s = new MakeAPile();
            }
    @Test
    public void testMakeAPile_ZeroNumberOfLevels() {
        int n = 0;
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, MakeAPile.makeAPile(n));
    }
    @Test
    public void testMakeAPile_OddNumberOfLevels_Fixed() {
        int n = 3;
        List<Integer> expected = Arrays.asList(3, 5, 7);
        assertEquals(expected, MakeAPile.makeAPile(n));
    }
    @Test
    public void testMakeAPile_OddNumberOfLevels_Fixed2() {
        int n = 3;
        List<Integer> expected = new ArrayList<>(Arrays.asList(3, 5, 7));
        assertEquals(expected, MakeAPile.makeAPile(n));
    }
    @Test
    public void testMakeAPile_EvenNumberOfLevels_1() {
        int n = 4;
        List<Integer> expected = new ArrayList<>(Arrays.asList(4, 6, 8, 10));
        assertEquals(expected, MakeAPile.makeAPile(n));
    }
                                    
}