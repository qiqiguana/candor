package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FileNameCheck.
*/
class FileNameCheckTest {
    @Test
    void testFileNameCheck_DotCount() {
        // Arrange and Act
        String result = FileNameCheck.fileNameCheck("example.txt");
        // Assert
        assertEquals("Yes", result);
    }
}