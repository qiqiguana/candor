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
    void testEvenOddPalindrome1() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(3);
        assertEquals(2, result.size());
        assertEquals(1, (int)result.get(0)); // even
        assertEquals(2, (int)result.get(1)); // odd
    }
}
