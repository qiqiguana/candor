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
    void testGetRow_EmptyList_ReturnsEmptyList() {
        List<Object> input = new ArrayList<>();
        int x = 1;
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, GetRow.getRow(input, x));
    }
}