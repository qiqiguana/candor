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
        assertEquals(Arrays.asList(11,4),result);
    }
    
    @Test
        public void testNothing(){
            Eat s = new Eat();
            }
    @Test
    public void Eat_test_Need_is_greater_than_remaining_carrots() {
        List<Integer> result = Eat.eat(4, 5, 1);
        assertEquals(result, Arrays.asList(5, 0));
    }
                                    
}