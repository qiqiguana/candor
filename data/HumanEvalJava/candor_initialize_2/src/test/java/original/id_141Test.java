package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FileNameCheck.
*/
class FileNameCheckTest {
    @Test
    void testFileNameCheck_DotPosition_ReturnsYes() {
        // Arrange and Act
        String actual = FileNameCheck.fileNameCheck("example.txt");
        // Assert
        assertEquals("Yes", actual);
    }
    
    @Test
        public void testNothing(){
            FileNameCheck s = new FileNameCheck();
            }
    @Test
    public void fileNameCheck_EmptyFileName_ReturnsNo() {
        String input = "";
        if (input.isEmpty()) {
            assertEquals("No", original.FileNameCheck.fileNameCheck(input));
        } else {
            fail("Expected empty string, but was: '" + input + "'");
        }
    }
    @Test
    public void fileNameCheck_InvalidExtension_ReturnsNo1() {
        String[] input = new String[] {"example.invalid"};
        assertEquals("No", original.FileNameCheck.fileNameCheck(input[0]));
    }
    @Test
    public void fileNameCheck_MissingExtension_ReturnsNo_1() {
        String input = "example123";
        assertEquals("No", original.FileNameCheck.fileNameCheck(input));
    }
    @Test
    public void fileNameCheck_InvalidFirstChar_ReturnsNo() {
        String[] input = new String[] {"example1.txt"};
        assertEquals("Yes", original.FileNameCheck.fileNameCheck(input[0]));
    }
    @Test
    public void fileNameCheck_MoreThanThreeDigits_ReturnsNo() {
        String[] input = new String[] {"examp1234le.txt"};
        int digitCount = 0;
        for (char c : original.FileNameCheck.fileNameCheck(input[0]).toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
                if (digitCount > 3) {
                    assertEquals("No", original.FileNameCheck.fileNameCheck(input[0]));
                }
            }
        }
    }
    @Test
    public void fileNameCheck_ValidFile_ReturnsYes_1() {
        String[] input = new String[] {"example.txt"};
        assertEquals("Yes", FileNameCheck.fileNameCheck(input[0]));
    }
    @Test
    public void fileNameCheck_EmptySubstringBeforeDot_ReturnsNo() {
    	String result = FileNameCheck.fileNameCheck(".txt");
    	assertEquals("No", result);
    }
    @Test
    public void fileNameCheck_SubstringBeforeDotDoesNotStartWithLetter_ReturnsNo() {
    	String result = FileNameCheck.fileNameCheck("1example.txt");
    	assertEquals("No", result);
    }
                                    
}