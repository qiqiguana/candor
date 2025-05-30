package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Modp.
*/
class ModpTest {

@Test
void test_modp_with_n_equal_to_3_and_p_equal_to_5() {
  int n = 3;
  int p = 5;
  assertEquals(3, Modp.modp(n, p));
}

@Test
    public void testNothing(){
        Modp s = new Modp();
        }
@Test
public void test_modp_valid_inputs() {
    int n = 3;
    int p = 5;
    int expected_result = 3;
    assertEquals(expected_result, Modp.modp(n, p));
}
@Test
public void test_modp_large_inputs() {
    int n = 1101;
    int p = 101;
    int expected_result = 2;
    assertEquals(expected_result, Modp.modp(n, p));
}
@Test
public void test_modp_n_zero() {
    int n = 0;
    int p = 101;
    int expected_result = 1;
    assertEquals(expected_result, Modp.modp(n, p));
}
@Test
public void test_modp_p_two() {
    int n = 3;
    int p = 2;
    int expected_result = 0;
    assertEquals(expected_result, Modp.modp(n, p));
}
                                
}