package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Simplify.
*/
class SimplifyTest {

@Test
void testSimplify() {
    // arrange
    String x = "1/2";
    String n = "3/4";
    Boolean expectedResult = true;
    // act
    Boolean actualResult = Simplify.simplify(x, n);
    // assert
    assertEquals(expectedResult, actualResult);
}
}