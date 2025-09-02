package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MaxElement.
*/
class MaxElementTest {
    @Test
    void testMaxElement() {
        List<Integer> l = new ArrayList<>();
        l.add(1);
        l.add(2);
        l.add(3);
        assertEquals(3, MaxElement.maxElement(l));
    }
}