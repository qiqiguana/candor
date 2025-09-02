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
    void testSeparateParenGroups_SimpleCase() {
        String parenString = "(()()) ((())) () ((())()())";
        List<String> expected = new ArrayList<>();
        expected.add("(()())");
        expected.add("((()))");
        expected.add("()");
        expected.add("((())()())");
        List<String> result = SeparateParenGroups.separateParenGroups(parenString);
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            SeparateParenGroups s = new SeparateParenGroups();
            }
                                    
}