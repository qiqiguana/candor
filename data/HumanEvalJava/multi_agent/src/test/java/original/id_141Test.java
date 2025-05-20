package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FileNameCheck.
*/
class FileNameCheckTest {
    @Test
    void testFileNameCheck_WithMoreThanThreeDigits_ReturnsNo() {
        String fileName = "example1234.txt";
        String result = FileNameCheck.fileNameCheck(fileName);
        assertEquals("No", result);
    }
}