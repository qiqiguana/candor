package original;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of NextSmallest.
*/
class NextSmallestTest {
    @Test
    void test_nextSmallest_withMultipleElements(){
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(2,NextSmallest.nextSmallest(list));
    }
}