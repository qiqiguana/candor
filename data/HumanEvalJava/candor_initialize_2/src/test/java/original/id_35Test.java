package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MaxElement.
*/
class MaxElementTest {

    @Test
    void testMaxElement1() {
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(3);
        list.add(-5);
        list.add(2);
        list.add(-3);
        list.add(3);
        list.add(9);
        list.add(0);
        list.add(124);
        list.add(1);
        list.add(-10);
        assertEquals(124, MaxElement.maxElement(list));
    }
    
    @Test
        public void testNothing(){
            MaxElement s = new MaxElement();
            }
                                    
}