package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountNums.
*/
class CountNumsTest {

@Test
void testCountNumsWithPositiveNumbers() {
    List<Object> arr = List.of(1, 2, 3);
    int result = CountNums.countNums(arr);
    assertEquals(3, result);
}
}