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
    void testUniqueDigitsWithNoEvenDigitNumbers() {
        List<Integer> input = new ArrayList<>();
        input.add(15);
        input.add(33);
        input.add(1422);
        input.add(1);
        List<Object> result = UniqueDigits.uniqueDigits(input);
        Collections.sort(result, new Comparator<Object>() {
            public int compare(Object a, Object b) {
                return (Integer) a - (Integer) b;
            }
        });
        assertArrayEquals(new Integer[]{1, 15, 33}, result.toArray(new Integer[0]));
    }
}