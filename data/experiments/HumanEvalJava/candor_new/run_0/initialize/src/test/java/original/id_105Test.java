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
void testByLength_EmptyArray_ReturnsEmptyArray() {
// Arrange
List<Object> input = new ArrayList<>();
List<Object> expectedOutput = new ArrayList<>();

// Act
List<Object> actualOutput = ByLength.byLength(input);

// Assert
assertIterableEquals(expectedOutput, actualOutput);
}
}