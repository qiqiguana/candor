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
    void test_uniqueDigits_OnlyOddDigits() {
        List<Integer> x = new ArrayList<>();
        x.add(15);
        x.add(33);
        x.add(1422);
        x.add(1);
        List<Object> result = UniqueDigits.uniqueDigits(x);
        assertEquals(3, result.size());
    }

}