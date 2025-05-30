package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of OddCount.
*/
class OddCountTest {
    @Test
    void testOddCount_OddAndEvenNumbers_ReturnsCorrectString() {
        List<String> input = new ArrayList<>();
        input.add("1234567");
        List<String> expected = new ArrayList<>();
        expected.add("the number of odd elements 4n the str4ng 4 of the 4nput.");
        assertEquals(expected, OddCount.oddCount(input));
    }
    
    @Test
        void testNothing(){
            OddCount s = new OddCount();
            }
    @Test
    public void testOddCountWithEmptyInput() {
        List<String> input = new ArrayList<>();
        List<String> expectedOutput = new ArrayList<>();
        assertEquals(expectedOutput, OddCount.oddCount(input));
    }
    @Test
    public void testOddCountWithNullInput() {
        assertThrows(NullPointerException.class, () -> OddCount.oddCount(null));
    }
                                    
}