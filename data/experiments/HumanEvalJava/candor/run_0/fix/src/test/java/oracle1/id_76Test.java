package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsSimplePower.
*/
class IsSimplePowerTest {
    @Test
    void testIsSimplePower_WhenXAndNAreBothOne_ReturnsTrue() {
        assertTrue(IsSimplePower.isSimplePower(1, 1));
    }
    
    @Test
        void testNothing(){
            IsSimplePower s = new IsSimplePower();
            }
    @Test
    public void test_Simple_Power_With_x_Equal_1_And_n_Equal_4() {
        assertEquals(true, IsSimplePower.isSimplePower(1, 4));
    }
    @Test
    public void test_Simple_Power_With_x_Equal_2_And_n_Equal_2() {
        assertEquals(true, IsSimplePower.isSimplePower(2, 2));
    }
    @Test
    public void test_Simple_Power_With_x_Equal_8_And_n_Equal_2() {
        assertEquals(true, IsSimplePower.isSimplePower(8, 2));
    }
    @Test
    public void test_Not_Simple_Power_With_x_Equal_3_And_n_Equal_2() {
        assertEquals(false, IsSimplePower.isSimplePower(3, 2));
    }
    @Test
    public void test_Not_Simple_Power_With_x_Equal_3_And_n_Equal_2_1() {
        assertEquals(false, IsSimplePower.isSimplePower(3, 2));
    }
    @Test
    public void test_Not_Simple_Power_With_x_Equal_3_And_n_Equal_2_2() {
        assertEquals(false, IsSimplePower.isSimplePower(3, 2));
    }
    @Test
    public void test_Not_Simple_Power_With_x_Equal_3_And_n_Equal_2_3() {
        assertEquals(false, IsSimplePower.isSimplePower(3, 2));
    }
    @Test
    public void test_Not_Simple_Power_With_x_Equal_3_And_n_Equal_2_4() {
        assertEquals(false, IsSimplePower.isSimplePower(3, 2));
    }
    @Test
    public void test_Edge_Case_With_x_Equal_Negative_1_And_n_Equal_5() {
        assertEquals(false, IsSimplePower.isSimplePower(-1, 5));
    }
    @Test
    public void test_Edge_Case_With_x_Equal_0_And_n_Not_Equal_0() {
        assertEquals(false, IsSimplePower.isSimplePower(0, 10));
    }
    @Test
    public void testIsSimplePower_1() {
        assertTrue(IsSimplePower.isSimplePower(1, 4));
    }
    @Test
    public void testIsSimplePower_2() {
        assertTrue(IsSimplePower.isSimplePower(2, 2));
    }
    @Test
    public void testIsSimplePower_3() {
        assertTrue(IsSimplePower.isSimplePower(8, 2));
    }
    @Test
    public void testIsNotSimplePower_1() {
        assertFalse(IsSimplePower.isSimplePower(3, 2));
    }
    @Test
    public void testIsSimplePower_4() {
        assertTrue(IsSimplePower.isSimplePower(1, 12));
    }
    @Test
    public void testEdgeCase_1() {
        assertFalse(IsSimplePower.isSimplePower(0, 2));
    }
    @Test
    void testIsSimplePower_X_Equal_To_1() {
        assertTrue(IsSimplePower.isSimplePower(1, 4));
    }
    @Test
    public void testIsSimplePower_x_1_n_any() {
        int x = 1;
        int n = 2;
        Boolean expected = true;
        Boolean actual = IsSimplePower.isSimplePower(x, n);
        assertEquals(expected, actual);
    }
    @Test
    public void testIsSimplePower_n_1_x_not_1() {
        int x = 2;
        int n = 1;
        Boolean expected = false;
        Boolean actual = IsSimplePower.isSimplePower(x, n);
        assertEquals(expected, actual);
    }
    @Test
    public void testIsSimplePower_x_n_p() {
        int x = 8;
        int n = 2;
        Boolean expected = true;
        Boolean actual = IsSimplePower.isSimplePower(x, n);
        assertEquals(expected, actual);
    }
    @Test
    public void testIsSimplePower_x_n_p_not_integer() {
        int x = 10;
        int n = 2;
        Boolean expected = false;
        Boolean actual = IsSimplePower.isSimplePower(x, n);
        assertEquals(expected, actual);
    }
    @Test
    public void testIsSimplePower_large_values() {
        int x = 1024;
        int n = 32;
        Boolean expected = true;
        Boolean actual = IsSimplePower.isSimplePower(x, n);
        assertEquals(expected, actual);
    }
    @Test
    void testIsSimplePower(){
        boolean result = IsSimplePower.isSimplePower(4, 2);
        assertTrue(result);
    }
                                    
}