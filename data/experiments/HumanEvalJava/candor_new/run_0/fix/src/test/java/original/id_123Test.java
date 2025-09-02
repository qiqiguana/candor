package original;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetOddCollatz.
*/
class GetOddCollatzTest {
    @Test
    void testGetOddCollatz_SimpleCase() {
        // Arrange
        int n = 5;
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(5);
        Collections.sort(expected);
        // Act
        List<Integer> result = GetOddCollatz.getOddCollatz(n);
        // Assert
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            GetOddCollatz s = new GetOddCollatz();
            }
                                    
}