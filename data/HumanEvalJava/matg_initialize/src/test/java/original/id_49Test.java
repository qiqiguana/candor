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
}
