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
        List<Integer> result = Eat.eat(number, need, remaining);
        assertEquals(2, result.size());
        assertEquals(11, result.get(0));
        assertEquals(4, result.get(1));
    }
    
    @Test
        public void testNothing(){
            Eat s = new Eat();
            }
    @Test
    public void testEatMethod_NeedGreaterThanRemaining() {
        List<Integer> actual = Eat.eat(2, 11, 5);
        List<Integer> expected = Arrays.asList(7, 0);
        assertEquals(expected, actual);
    }
                                    
}