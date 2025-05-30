package original;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Maximum1.
*/
class Maximum1Test {

@Test
void testMaximum_EmptyList_ReturnsEmptyList() {
        List<Integer> arr = new ArrayList<>();
        int k = 3;
        List<Object> result = Maximum1.maximum(arr, k);
        assertEquals(0, result.size());
    }

@Test
    public void testNothing(){
        Maximum1 s = new Maximum1();
        }
@Test
public void testEmptyArrayFixed() {
    List<Integer> arr = new ArrayList<>();
    int k = 0;
    assertEquals(java.util.Collections.emptyList(), Maximum1.maximum(arr, k));
}
@Test
public void testKEqualsZero() {
    List<Integer> arr = Arrays.asList(1, 2, 3);
    int k = 0;
    assertEquals(Collections.emptyList(), Maximum1.maximum(arr, k));
}
@Test
public void testNLessThanOrEqualToK() {
    List<Integer> arr = Arrays.asList(4, -4, 4);
    int k = 3;
    List<Object> expected = Arrays.asList(-4, 4, 4);
    assertEquals(expected, Maximum1.maximum(arr, k));
}
@Test
public void testNGreaterThanKFixed() {
    List<Integer> arr = Arrays.asList(1, 2, 3, -23, 243, -400, 0);
    int k = 7;
    List<Object> expected = Arrays.asList(-400, -23, 0, 1, 2, 3, 243);
    assertEquals(expected, Maximum1.maximum(arr, k));
}
@Test
public void testDuplicateMaximumValues_1() {
    List<Integer> arr = Arrays.asList(4, -4, 4);
    int k = 2;
    List<Object> expected = Arrays.asList(4, 4);
    assertEquals(expected, Maximum1.maximum(arr, k));
}
                                
}