package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CorrectBracketing.
*/
class CorrectBracketingTest {
    @Test
    void test_CorrectBracketing_should_ReturnFalse_When_OpeningBracketsAreMoreThanClosingOnes() {
        // Arrange
        String brackets = "<<>";

        // Act
        Boolean result = CorrectBracketing.correctBracketing(brackets);

        // Assert
        assertFalse(result);
    }
}