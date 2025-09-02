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
        List<Integer> result = Eat.eat(5, 6, 10);
        assertEquals(2, result.size());
        assertEquals(11, (int)result.get(0));
        assertEquals(4, (int)result.get(1));
    }
    
    @Test
        public void testNothing(){
            Eat s = new Eat();
            }
    @Test
    public void eat_NeedLessThanOrEqualToRemaining() {
        List<Integer> result = Eat.eat(5, 6, 10);
        assertEquals(Arrays.asList(11, 4), result);
    }
    @Test
    public void eat_NeedGreaterThanRemaining() {
        List<Integer> result = Eat.eat(2, 11, 5);
        assertEquals(Arrays.asList(7, 0), result);
    }
                                    
}