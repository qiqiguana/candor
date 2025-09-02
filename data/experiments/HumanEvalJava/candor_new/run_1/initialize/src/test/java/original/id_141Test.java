package original;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FileNameCheck.
*/
class FileNameCheckTest {
	@Test
	void testFileNameWithMoreThanThreeDigits() {
	    String fileName = "example1234.txt";
	    assertEquals("No", FileNameCheck.fileNameCheck(fileName));
	}
}