package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solve.
*/
class SolveTest {
    @Test
    void testSolve_ContainsLetters() {
        String input = "#a@C";
        String expected = "#A@c";
        assertEquals(expected, Solve.solve(input));
    }
    
    @Test
        public void testNothing(){
            Solve s = new Solve();
            }
    @Test
    public void test_ReverseCaseOfLetters() {
        String input = "#a@C";
        String expectedOutput = "#A@c";
        assertEquals(expectedOutput, Solve.solve(input));
    }
    @Test
    public void test_ReverseStringWithoutLetters() {
        String input = "1234";
        String expectedOutput = "4321";
        assertEquals(expectedOutput, Solve.solve(input));
    }
    @Test
    public void test_EmptyInput() {
        String input = "";
        String expectedOutput = "";
        assertEquals(expectedOutput, Solve.solve(input));
    }
    @Test
    public void test_SingleCharacterLetter() {
        String input = "a";
        String expectedOutput = "A";
        assertEquals(expectedOutput, Solve.solve(input));
    }
    @Test
    public void test_SingleCharacterNonLetter() {
        String input = "1";
        String expectedOutput = "1";
        assertEquals(expectedOutput, Solve.solve(input));
    }
                                    
}