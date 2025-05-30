package original;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Eat.
*/
class EatTest {
    @Test
    void testEat() {
        int number = 5;
        int need = 6;
        int remaining = 10;
        List<Integer> expected = Arrays.asList(11, 4);
        assertEquals(expected, Eat.eat(number, need, remaining));
    }
    
    @Test
        void testNothing(){
            Eat s = new Eat();
            }
    @Test
    public void testEatMethod_EnoughRemainingCarrots() {
        List<Integer> result = Eat.eat(5, 6, 10);
        assertEquals(Arrays.asList(11, 4), result);
    }
    @Test
    public void testEatMethod_NotEnoughRemainingCarrots() {
        List<Integer> result = Eat.eat(2, 11, 5);
        assertEquals(Arrays.asList(7, 0), result);
    }
    @Test
    public void testEatMethod_ZeroNeed() {
        List<Integer> result = Eat.eat(5, 0, 10);
        assertEquals(Arrays.asList(5, 10), result);
    }
    @Test
    public void testEatMethod_ZeroRemainingCarrots() {
        List<Integer> result = Eat.eat(4, 5, 0);
        assertEquals(Arrays.asList(4, 0), result);
    }
                                    
}