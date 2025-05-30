package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
* Test class of Median.
*/
class MedianTest {
    @Test
    void testMedian() {
        List<Integer> l = new ArrayList<>();
        l.add(3);
        l.add(1);
        l.add(2);
        l.add(4);
        l.add(5);
        assertEquals(3, Median.median(l));
    }
    
    @Test
        void testNothing(){
            Median s = new Median();
            }
    @Test
    public void PositiveTest_TwoElementsList() {
        List<Integer> input = Arrays.asList(6, 5);
        assertEquals(Double.valueOf(5.5), Median.median(input));
    }
    @Test
    public void EvenNumberOfElements_1() {
        List<Integer> input = Arrays.asList(1, 2, 3, 4);
        assertEquals(2.5, (double) Median.median(input), 0.01);
    }
    @Test
    public void EdgeCaseTest_NegativeNumbers() {
        List<Integer> input = Arrays.asList(-1, -2, -3, -4, -5);
        assertEquals(-3.0, Median.median(input).doubleValue(), 0.001);
    }
                                    
}