package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of SeparateParenGroups.
*/
class SeparateParenGroupsTest {

    @Test
    void separateParenGroups_SimpleCase_GroupsSeparated() {
        String input = "(()) () ((()))";
        List<String> expected = new ArrayList<>();
        expected.add("(())");
        expected.add("()");
        expected.add("((()))");
        assertEquals(expected, SeparateParenGroups.separateParenGroups(input));
    }
}