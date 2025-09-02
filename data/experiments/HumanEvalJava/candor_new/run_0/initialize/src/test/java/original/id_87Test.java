package original;

import java.util.ArrayList;

import java.util.Collections;

import java.util.Comparator;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of GetRow.
*/
class GetRowTest {

    @Test
    public void testGetRow1() {
        List<Object> lst = new ArrayList<>();
        lst.add(new ArrayList<>(List.of(1,2,3,4,5,6)));
        lst.add(new ArrayList<>(List.of(1,2,3,4,1,6)));
        lst.add(new ArrayList<>(List.of(1,2,3,4,5,1)));
        List<Object> expected = new ArrayList<>();
        expected.add(List.of(0, 0));
        expected.add(List.of(1, 4));
        expected.add(List.of(1, 0));
        expected.add(List.of(2, 5));
        expected.add(List.of(2, 0));
        assertEquals(expected, GetRow.getRow(lst, 1));
    }
}