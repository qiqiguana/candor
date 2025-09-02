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
    void testCompare() {
        List<Integer> game = new ArrayList<>();
        game.add(1);
        game.add(2);
        game.add(3);
        game.add(4);
        game.add(5);
        game.add(1);
        
        List<Integer> guess = new ArrayList<>();
        guess.add(1);
        guess.add(2);
        guess.add(3);
        guess.add(4);
        guess.add(2);
        guess.add(-2);
        
        List<Integer> expected = new ArrayList<>();
        expected.add(0);
        expected.add(0);
        expected.add(0);
        expected.add(0);
        expected.add(3);
        expected.add(3);
        
        assertEquals(expected, Compare.compare(game, guess));
    }
}