package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testTri() {
        List<Number> expected = Arrays.asList(1, 3);
        List<Number> actual = Tri.tri(1);
        assertEquals(expected, actual);
    }
    @Test
    public void testTriWithNZero() {
        int n = 0;
        List<Object> expectedResult = Arrays.asList(1);
        assertEquals(expectedResult, Tri.tri(n));
    }
    @Test
    public void testTriWithNOne() {
        int n = 1;
        List<Object> expectedResult = Arrays.asList(1, 3);
        assertEquals(expectedResult, Tri.tri(n));
    }
    @Test
    public void testTriWithNTwo() {
        int n = 2;
        List<Object> expectedResult = Arrays.asList(1, 3, 2.0);
        assertEquals(expectedResult, Tri.tri(n));
    }
    @Test
    public void testTriWithNThree() {
        int n = 3;
        List<Object> expectedResult = Arrays.asList(1, 3, 2.0, 8.0);
        assertEquals(expectedResult, Tri.tri(n));
    }
    @Test
    public void testTriWithNTenCorrected() {
        int n = 10;
        List<Object> expectedResult = Arrays.asList(1, 3, 2.0, 8.0, 3.0, 15.0, 4.0, 24.0, 5.0, 35.0, 6.0);
        assertEquals(expectedResult, Tri.tri(n));
    }
    @Test
    public void testTri_n_0() {
        int n = 0;
        List<Number> expected_result = Arrays.asList(1);
        assertEquals(expected_result, Tri.tri(n));
    }
    @Test
    public void testTri_n_1() {
        int n = 1;
        List<Number> expected_result = Arrays.asList(1, 3);
        assertEquals(expected_result, Tri.tri(n));
    }
    @Test
    public void testTri_n_2() {
        int n = 2;
        List<Number> expected_result = Arrays.asList(1, 3, 2.0);
        assertEquals(expected_result, Tri.tri(n));
    }
    @Test
    public void testTri_n_3() {
        int n = 3;
        List<Number> expected_result = Arrays.asList(1, 3, 2.0, 8.0);
        assertEquals(expected_result, Tri.tri(n));
    }
    @Test
    public void testTri_n_large() {
        int n = 100;
        List<Number> result = Tri.tri(n);
        assertNotNull(result);
    }
    @Test void testTriWithNTwo_2() { java.util.List<java.lang.Number> result = original.Tri.tri(2); assertEquals(java.util.Arrays.asList(1, 3, 2.0), result);}
}