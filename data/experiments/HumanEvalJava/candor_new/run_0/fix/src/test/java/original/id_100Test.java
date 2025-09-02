package original;

import java.util.ArrayList;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MakeAPile.
*/
class MakeAPileTest {
    @Test
    void test_makeAPile_WithOddNumber_ShouldReturnCorrectList() {
        // Arrange
        int n = 3;
        List<Integer> expected = new ArrayList<>(List.of(3, 5, 7));

        // Act
        List<Integer> result = MakeAPile.makeAPile(n);

        // Assert
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            MakeAPile s = new MakeAPile();
            }
    @Test
    public void testMakeAPile_EvenLevels() {
        List<Integer> result = MakeAPile.makeAPile(8);
        assertEquals(List.of(8, 10, 12, 14, 16, 18, 20, 22), result);
    }
                                    
}