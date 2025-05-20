package original;

import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solve1.
*/
class Solve1Test {
    @Test
    void testSolve() {
        int input = 1000;
        String expected = "1";
        String actual = Solve1.solve(input);
        assertEquals(expected, actual);
    }
    @Test
    void test_solve_with_single_digit() {
        assertEquals("101", Solve1.solve(5));
    }
    @Test
    void test_solve_with_edge_case_9() {
        assertEquals("1001", Solve1.solve(9));
    }
    public void test_solve_with_max_value() { int n = 10000; String result = Solve1.solve(n); assertEquals("1110000110000", result); }
    public void test_solve_with_zero() { int n = 0; String result = Solve1.solve(n); assertEquals("0", result); }
    public void test_solve_with_small_number() { int n = 147; String result = Solve1.solve(n); assertEquals("1100", result); }
    public void test_solve_with_large_number() { int n = 150; String result = Solve1.solve(n); assertEquals("110", result); }
    public void test_solve_with_positive_number() { int n = 1000; String result = Solve1.solve(n); assertEquals("1", result); }
}