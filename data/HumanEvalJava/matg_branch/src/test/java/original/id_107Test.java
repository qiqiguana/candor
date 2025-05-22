package original;

import java.util.ArrayList;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of EvenOddPalindrome.
*/
class EvenOddPalindromeTest {
    @Test
    void testEvenOddPalindrome() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(3);
        assertEquals(result.get(0).intValue(), 1);
    }
}
