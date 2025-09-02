package original;

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
        public void testNothing(){
            MakeAPile s = new MakeAPile();
            }
    @Test
    public void testMakeAPileWithOddN() {
        int n = 3;
        List<Integer> expected = List.of(3, 5, 7);
        assertEquals(expected, MakeAPile.makeAPile(n));
    }
    @Test
    public void testMakeAPileWithEvenN() {
        int n = 4;
        List<Integer> expected = List.of(4, 6, 8, 10);
        assertEquals(expected, MakeAPile.makeAPile(n));
    }
    @Test
    public void testMakeAPileWithN1() {
        int n = 1;
        List<Integer> expected = List.of(1);
        assertEquals(expected, MakeAPile.makeAPile(n));
    }
                                    
}