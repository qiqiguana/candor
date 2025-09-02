package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of UniqueDigits.
*/
class UniqueDigitsTest {
    @Test
    void testUniqueDigits() {
        List<Integer> input = new ArrayList<>(List.of(15, 33, 1422, 1));
        List<Object> expected = new ArrayList<>(List.of(1, 15, 33));
        Comparator comparator = (o1, o2) -> ((Integer)o1).compareTo((Integer)o2);
        Collections.sort(expected,comparator);
        assertEquals(expected, UniqueDigits.uniqueDigits(input));
    }
    
    @Test
        public void testNothing(){
            UniqueDigits s = new UniqueDigits();
            }
                                    
}