package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CorrectBracketing1.
*/
class CorrectBracketing1Test {
    @Test
    void correctBracketing_EmptyString_ReturnsTrue() {
        // Arrange
        String brackets = "";
        Boolean expectedResult = true;
        
        // Act
        Boolean actualResult = CorrectBracketing1.correctBracketing(brackets);
        
        // Assert
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
        public void testNothing(){
            CorrectBracketing1 s = new CorrectBracketing1();
            }
    @Test
    public void testCorrectBracketingSingleOpeningBracket() {
        assertFalse(CorrectBracketing1.correctBracketing("(")); }
    @Test
    public void testCorrectBracketingSingleClosingBracket() {
        assertFalse(CorrectBracketing1.correctBracketing(")")); }
                                    
}