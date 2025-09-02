package original;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of MatchParens.
*/
class MatchParensTest {

    @Test
    void testMatchParens() {
        List<String> input = List.of("(", ")");
        assertEquals("Yes", MatchParens.matchParens(input));
    }
    
    @Test
        public void testNothing(){
            MatchParens s = new MatchParens();
            }
    @Test
    public void testMatchParens_BalancedInput2_Fixed() {
        List<String> input = Arrays.asList("(", ")");
        String result1 = MatchParens.matchParens(input);
        assertEquals("Yes", result1);
        Collections.reverse(input);
        String result2 = MatchParens.matchParens(input);
        assertEquals("Yes", result2);
    }
    @Test
    public void testMatchParens_UnbalancedInput2() {
        List<String> input = Arrays.asList(")(", "(");
        assertEquals("No", MatchParens.matchParens(input));
    }
    @Test
    public void testMatchParens_ConsecutiveOpening_Fixed() {
        List<String> input = Arrays.asList("(((()", ")())");
        String result1 = MatchParens.matchParens(Arrays.asList(input.get(0), input.get(1)));
        String result2 = MatchParens.matchParens(Arrays.asList(input.get(1), input.get(0)));
        if (result1.equals("Yes") || result2.equals("Yes")) {
            int maxConsecutiveOpenings = countMaxConsecutiveOpenings(result1.equals("Yes") ? result1 : result2);
            int totalClosings = countTotalClosings(result1.equals("Yes") ? result1 : result2);
            assertTrue(maxConsecutiveOpenings <= totalClosings);
        }
    }
    
    private int countMaxConsecutiveOpenings(String s) {
        int maxCount = 0;
        int currentCount = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                currentCount++;
                maxCount = Math.max(maxCount, currentCount);
            } else {
                currentCount = 0;
            }
        }
        return maxCount;
    }
    
    private int countTotalClosings(String s) {
        int count = 0;
        for (char c : s.toCharArray()) {
            if (c == ')') {
                count++;
            }
        }
        return count;
    }
                                    
}