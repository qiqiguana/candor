package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ParseNestedParens.
*/
class ParseNestedParensTest {

@Test
void testParseNestedParens1() {
String input = "(()()) ((())) () ((())()())";
List<Integer> expected = new ArrayList<Integer>();
expected.add(2);
expected.add(3);
expected.add(1);
expected.add(3);
assertEquals(expected, ParseNestedParens.parseNestedParens(input));
}
}