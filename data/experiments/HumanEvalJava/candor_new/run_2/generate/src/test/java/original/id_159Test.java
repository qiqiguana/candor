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
        assertEquals(result.get(0).intValue(), 11);
        assertEquals(result.get(1).intValue(), 4);
    }
}