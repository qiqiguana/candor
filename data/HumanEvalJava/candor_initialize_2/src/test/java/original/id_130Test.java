package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Tri.
*/
class TriTest {
    @Test
    void testTri() {
        List<Number> result = Tri.tri(3);
        assertEquals(Arrays.asList(1, 3, 2.0, 8.0), result);
    }
    @Test
    public void testTribonacciSequenceWithN0() {
        List<Number> result = Tri.tri(0);
        assertEquals(Arrays.asList(1), result);
    }
    @Test
    public void testTribonacciSequenceWithN1() {
        List<Number> result = Tri.tri(1);
        assertEquals(Arrays.asList(1, 3), result);
    }
    @Test
    public void testTribonacciSequenceWithN2() {
        List<Number> result = Tri.tri(2);
        assertEquals(Arrays.asList(1, 3, 2.0), result);
    }
    @Test
    public void testTribonacciSequenceWithN3() {
        List<Number> result = Tri.tri(3);
        assertEquals(Arrays.asList(1, 3, 2.0, 8.0), result);
    }
    @Test
    public void testTriNZero() {
        List<Number> result = Tri.tri(0);
        assertEquals(1, result.size());
        assertEquals(1.0, result.get(0).doubleValue(), 0);
    }
    @Test
    public void testTriNThree() {
        List<Number> result = Tri.tri(3);
        assertEquals(4, result.size());
    }
    @Test
    public void TestTriWithZeroInput() {
        int n = 0;
        List<Number> result = Arrays.asList(1);
        assertEquals(result, Tri.tri(n));
    }
    @Test
    public void TestTriWithOneInput() {
    	int n = 1;
    	List<Number> result = Arrays.asList(1, 3);
    	assertEquals(result, Tri.tri(n));
    }
    @Test
    public void TestTriWithEvenInput2() {
        int n = 2;
        List<Number> result = Arrays.asList(1, 3, 2.0);
        assertEquals(result, Tri.tri(n));
    }
    @Test
    public void TestTriWithOddInput() {
        int n = 3;
        List<Number> result = Arrays.asList(1, 3, 2.0, 8.0);
        assertEquals(result, Tri.tri(n));
    }
    @Test
    public void TestTriWithLargeInputCorrected2() {
        int n = 10;
        List<Number> result = Tri.tri(n);
        assertEquals(11, result.size());
        for (int i = 0; i < result.size(); i++) {
            if (i == 0) {
                assertEquals(1.0, result.get(i).doubleValue(), 0.01);
            } else if (i == 1) {
                assertEquals(3.0, result.get(i).doubleValue(), 0.01);
            } else if (i % 2 == 0) {
                assertEquals((double) (i / 2 + 1), result.get(i).doubleValue(), 0.01);
            } else {
                double expectedValue = result.get(i - 1).doubleValue() + result.get(i - 2).doubleValue() + result.get(i + 1).doubleValue();
                assertEquals(expectedValue, result.get(i).doubleValue(), 0.01);
            }
        }
    }
}