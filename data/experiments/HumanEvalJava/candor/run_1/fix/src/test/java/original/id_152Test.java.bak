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
        List<Integer> game = new ArrayList<>(List.of(1, 2, 3));
        List<Integer> guess = new ArrayList<>(List.of(-1, -2, -3));
        assertEquals(List.of(2, 4, 6), Compare.compare(game, guess));
    }
    
    @Test
        void testNothing(){
            Compare s = new Compare();
            }
    @Test
    public void compare_EqualLengthArrays() {
        List<Integer> game = new ArrayList<>(List.of(1, 2, 3));
        List<Integer> guess = new ArrayList<>(List.of(1, 2, 3));
        assertEquals(List.of(0, 0, 0), Compare.compare(game, guess));
    }
    @Test
    public void compare_DifferentLengthArrays() {
        List<Integer> game = new ArrayList<>(List.of(1, 2, 3));
        List<Integer> guess = new ArrayList<>(List.of(1, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> Compare.compare(game, guess));
    }
    @Test
    public void compare_NullInputArrays() {
        List<Integer> game = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> Compare.compare(null, game));
    }
    @Test
    public void compare_EmptyArrays() {
        List<Integer> game = new ArrayList<>();
        assertEquals(new ArrayList<>(), Compare.compare(game, game));
    }
                                    
}