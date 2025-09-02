package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CompareOne.
*/
class CompareOneTest {

@Test
void test_compareOne_returnLargerVariable() {
Object result = CompareOne.compareOne("5,1", "6");
assertEquals("6", result);
}
}