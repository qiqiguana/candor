package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FileNameCheck.
*/
class FileNameCheckTest {
    @Test
    void testFileNameCheck_DoesNotContainThreeDigits_ReturnsYes() {
        String result = FileNameCheck.fileNameCheck("example.txt");
        assertEquals("Yes", result);
    }
}