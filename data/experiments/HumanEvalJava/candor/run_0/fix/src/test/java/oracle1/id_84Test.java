package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solve1.
*/
class Solve1Test {
    @Test
    void testSolve() {
        assertEquals("110", Solve1.solve(150));
    }
    
    @Test
     void testNothing(){
         Solve1 s = new Solve1();
         }
    @Test
    public void testPositiveNumber() {
        String result = Solve1.solve(1000);
        assertEquals("1", result);
    }
    @Test
    public void testZero() {
        String result = Solve1.solve(0);
        assertEquals("0", result);
    }
    @Test
    public void testSingleDigitNumber() {
        String result = Solve1.solve(5);
        assertEquals("101", result);
    }
    @Test
    public void testEdgeCaseMinValue() {
        String result = Solve1.solve(0);
        assertEquals("0", result);
    }
    @Test
    public void testLargeNumberCorrected() {
        int number = 10000;
        String[] digits = Integer.toBinaryString(number).split("");
        int sum = 0;
        for (String digit : digits) {
            sum += Integer.parseInt(digit);
        }
        assertEquals(String.valueOf(sum), Solve1.solve(number));
    }
                                  
}