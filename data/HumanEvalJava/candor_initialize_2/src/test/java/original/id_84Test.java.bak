package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solve1.
*/
class Solve1Test {
    @Test
    void testSolveBinarySum() {
        assertEquals("110", Solve1.solve(150));
    }
    @Test
    public void TestSolveMethodWithPositiveNumber() {
        String result = Solve1.solve(150);
        assertEquals("110", result);
    }
    @Test
    public void TestSolveMethodWithZero() {
        String result = Solve1.solve(0);
        assertEquals("0", result);
    }
    @Test
    public void TestSolveMethodWithLargeNumber() {
        String result = Solve1.solve(10000);
        assertEquals("1", result);
    }
    @Test
    public void TestSolveMethodWithSmallNumber() {
        String result = Solve1.solve(9);
        assertEquals("1001", result);
    }
    @Test
    public void testPositiveNumber() {
       String result = Solve1.solve(1000);
       assertEquals("1", result);
    }
    @Test
    public void testSmallPositiveNumber() {
       String result = Solve1.solve(5);
       assertEquals("101", result);
    }
    @Test
    public void testZero() {
       String result = Solve1.solve(0);
       assertEquals("0", result);
    }
    @Test
    public void testLargePositiveNumberCorrected() {
       String result = Solve1.solve(9999);
       assertEquals("100100", result);
    }
    @Test
    public void testSingleDigitNumber() {
       String result = Solve1.solve(5);
       assertEquals("101", result);
    }
    @Test
    public void testLargeNumberCorrected() {
        String result = Solve1.solve(9999);
        assertEquals("100100", result);
    }
    @Test
    public void testZeroFixed() {
        String result = Solve1.solve(0);
        assertEquals("0", Integer.toBinaryString(result.isEmpty() ? 0 : Integer.parseInt(result, 2)));
    }
}