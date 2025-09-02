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
    void testMakeAPile_OddInput_ReturnsCorrectList() {
        // Arrange
        int n = 3;
        List<Integer> expected = new ArrayList<>(List.of(3, 5, 7));

        // Act
        List<Integer> actual = MakeAPile.makeAPile(n);

        // Assert
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            MakeAPile s = new MakeAPile();
            }
    @Test
    public void MakeAPileTestForEvenN() {
        List<Integer> result = new ArrayList<>();
        result.add(4);
        result.add(6);
        result.add(8);
        result.add(10);
        assertEquals(result, MakeAPile.makeAPile(4));
    }
                                    
}