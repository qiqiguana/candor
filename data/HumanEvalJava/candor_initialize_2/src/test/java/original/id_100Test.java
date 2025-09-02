package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MakeAPile.
*/
class MakeAPileTest {
    @Test
    void test_makeAPile_withOddNumber() {
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
    public void testConstructor() {
        new MakeAPile();
    }
    @Test
    void testMakeAPile_Even() {
        List<Integer> expected = new ArrayList<>();
        expected.add(8);
        expected.add(10);
        expected.add(12);
        expected.add(14);
        expected.add(16);
        expected.add(18);
        expected.add(20);
        expected.add(22);
        assertEquals(expected, MakeAPile.makeAPile(8));
    }
                                    
}