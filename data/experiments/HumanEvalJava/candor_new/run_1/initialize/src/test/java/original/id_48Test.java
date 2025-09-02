package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IsPalindrome.
*/
class IsPalindromeTest {

    @Test
    void test_isPalindrome_onEmptyString() {
        // Arrange & Act
        Boolean result = IsPalindrome.isPalindrome("");
        // Assert
        assertTrue(result);
    }
}