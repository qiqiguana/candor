package original;

import java.util.ArrayList;

import java.util.Collections;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ByLength.
*/
class ByLengthTest {

@Test
void testByLength_SingleElementArray_ReturnsExpectedResult() {
    List<Object> arr = new ArrayList<>();
    arr.add(5);
    List<Object> result = ByLength.byLength(arr);
    assertEquals("Five", result.get(0));
}
}