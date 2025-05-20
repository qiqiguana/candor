package original;

import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals; import java.util.List;
import org.junit.jupiter.api.Test; import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Tri.
*/
class TriTest {

@Test
void testTri_WhenNIsZero_ShouldReturnListWithOneElement() {
    List<Number> result = Tri.tri(0);
    assertEquals(1, result.size());
}
@Test
public void testTri_OddPosition() {
    List<Number> result = Tri.tri(1);
    assertEquals(Arrays.asList(1, 3), result);
}
@Test
public void testTriEmpty() {
    int n = 0;
    List<Number> expectedResult = Arrays.asList(1);
    assertEquals(expectedResult, Tri.tri(n));
}
@Test
void test_tri_n() {
    List<Number> result = Tri.tri(3);
    assertEquals(Arrays.asList(1, 3), result.subList(0,2));
}
@Test public void tri_0() { List<Number> result = Tri.tri(0); assertEquals(Arrays.asList(1), result); }
@Test public void tri_1() { List<Number> result = Tri.tri(1); assertEquals(Arrays.asList(1, 3), result); }
@Test public void tri_even() { List<Number> result = Tri.tri(2); assertEquals(Arrays.asList(1, 3, 2.0), result); }
@Test public void test_tri_n_zero() { List<Number> result = Tri.tri(0); assertEquals(Arrays.asList(1), result); }
@Test public void test_tri_n_one() { List<Number> result = Tri.tri(1); assertEquals(Arrays.asList(1, 3), result); }
public void tri_test_5() { List<Number> expected = Arrays.asList(1, 3, 2.0, 5.0, 13.0, 34.0, 89.0, 233.0, 610.0, 1597.0); assertEquals(expected, Tri.tri(10)); }
public void tri_test_4() { List<Number> expected = Arrays.asList(1, 3, 2.0, 5.0); assertEquals(expected, Tri.tri(3)); }
public void tri_test_3() { List<Number> expected = Arrays.asList(1, 3, 2.0); assertEquals(expected, Tri.tri(2)); }
public void tri_test_2() { List<Number> expected = Arrays.asList(1, 3); assertEquals(expected, Tri.tri(1)); }
public void tri_test_1() { List<Number> expected = Arrays.asList(1); assertEquals(expected, Tri.tri(0)); }
public void testTriWithNegativeInput() { List<Number> result = Tri.tri(-1); assertEquals(Arrays.asList(), result);}
public void testTriWithPositiveOddInput() { List<Number> result = Tri.tri(3); assertEquals(Arrays.asList(1, 3, 2.0), result);}
public void testTriWithPositiveEvenInput() { List<Number> result = Tri.tri(4); assertEquals(Arrays.asList(1, 3, 2.0, 8.0, 3.0), result);}
public void testTriWithZeroInput() { List<Number> result = Tri.tri(0); assertEquals(Arrays.asList(1), result);}
public void test_tri_negative() { assertThrows(Exception.class, () -> Tri.tri(-1)); }
public void test_tri_4() { List<Number> expected = Arrays.asList(1, 3, 2.0, 8.0, 15.0); assertEquals(expected, Tri.tri(4)); }
public void test_tri_3() { List<Number> expected = Arrays.asList(1, 3, 2.0, 8.0); assertEquals(expected, Tri.tri(3)); }
public void test_tri_2() { List<Number> expected = Arrays.asList(1, 3, 2.0); assertEquals(expected, Tri.tri(2)); }
public void test_tri_1() { List<Number> expected = Arrays.asList(1, 3); assertEquals(expected, Tri.tri(1)); }
public void test_tri_0() { List<Number> expected = Arrays.asList(1); assertEquals(expected, Tri.tri(0)); }
}