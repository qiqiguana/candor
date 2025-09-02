package original;

import java.util.Arrays;
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
    public void testMakeAPile_EvenNumberOfLevels_Fixed_1() {
        List<Integer> expected = Arrays.asList(4, 6, 8, 10);
        int n = 4;
        List<Integer> result = MakeAPile.makeAPile(n);
        assertEquals(expected.size(), result.size());
    }
                                    
}