package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Compare.
*/
class CompareTest {
    @Test
    void testCompare_DifferentLengthInput_ThrowsNothing() {
        List<Integer> game = new ArrayList<>(List.of(1, 2, 3));
        List<Integer> guess = new ArrayList<>(List.of(-1, -2, -3));
        assertDoesNotThrow(() -> Compare.compare(game, guess));
    }
    
    @Test
        public void testNothing(){
            Compare s = new Compare();
            }
                                    
}