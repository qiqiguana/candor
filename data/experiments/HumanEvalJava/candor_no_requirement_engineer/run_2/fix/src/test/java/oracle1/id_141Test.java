package oracle1;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of FileNameCheck.
*/
class FileNameCheckTest {
    @Test
    void testFileNameCheck_WhenCalledWithValidFileName_ReturnsYes() {
        String fileName = "example.txt";
        String result = FileNameCheck.fileNameCheck(fileName);
        assertEquals("Yes", result);
    }
    
    @Test
        void testNothing(){
            FileNameCheck s = new FileNameCheck();
            }
    @Test
    public void testValidFileName() {
        String fileName = "example.txt";
        assertEquals("Yes", FileNameCheck.fileNameCheck(fileName));
    }
    @Test
    public void testInvalidExtension() {
        String fileName = "example.wow";
        assertEquals("No", FileNameCheck.fileNameCheck(fileName));
    }
    @Test
    public void testMultipleDots() {
        String fileName = "final..txt";
        assertEquals("No", FileNameCheck.fileNameCheck(fileName));
    }
    @Test
    public void testEmptyFileName() {
        String fileName = "";
        assertEquals("No", FileNameCheck.fileNameCheck(fileName));
    }
    @Test
    public void testNonLetterStart() {
        String fileName = "1example.txt";
        assertEquals("No", FileNameCheck.fileNameCheck(fileName));
    }
    @Test
    public void testExcessiveDigits() {
        String fileName = "1234example.txt";
        assertEquals("No", FileNameCheck.fileNameCheck(fileName));
    }
    @Test
    void testFileNameCheck() {
        assertEquals("Yes", FileNameCheck.fileNameCheck("example.txt"));
    }
    @Test
    public void testFileNameCheck_NoDotInFileName_ReturnsNo_2() {
    	String input = "example";
    	assertEquals("Yes", oracle1.FileNameCheck.fileNameCheck(input));
    }
    @Test
    public void testFileNameCheck_NoDotInFileName_ReturnsNo_3() {
        String input = "example";
        assertEquals("No", oracle1.FileNameCheck.fileNameCheck(input));
    }
    @Test
    public void testfileNameCheck_MoreThanThreeDigitsInFileName_ReturnsNo() {
        String input = "example1234.txt";
        assertEquals("No", oracle1.FileNameCheck.fileNameCheck(input));
    }
    @Test
    public void testfileNameCheck_InvalidSuffixInFileName_ReturnsNo() {
        String input = "example.pdf";
        assertEquals("No", oracle1.FileNameCheck.fileNameCheck(input));
    }
    @Test
    public void testfileNameCheck_LongFileNameWithMoreThanThreeDigits_ReturnsNo() {
        String input = "thisisareallylongfilename1234567890.txt";
        assertEquals("No", oracle1.FileNameCheck.fileNameCheck(input));
    }
    @Test
    public void testfileNameCheck_EmptyFileName_ReturnsNo_1() {
        String input = "";
        assertEquals("No", oracle1.FileNameCheck.fileNameCheck(input));
    }
    @Test
    public void Test_Dot_In_Wrong_Position() {
    	String fileName = "this.is_valid.txt";
    	assertEquals("No", FileNameCheck.fileNameCheck(fileName));
    }
    @Test
    public void Test_Too_Many_Dots() {
    	String fileName = "this.is.valid.txt";
    	assertEquals("No", FileNameCheck.fileNameCheck(fileName));
    }
    @Test
    public void Test_No_Latin_Alphabet_First_Char() {
    	String fileName = "1example.dll";
    	assertEquals("No", FileNameCheck.fileNameCheck(fileName));
    }
    @Test
    public void Test_Substring_After_Dot_Not_Valid() {
    	String fileName = "this_is_valid.wow";
    	assertEquals("No", FileNameCheck.fileNameCheck(fileName));
    }
    @Test
    public void Test_Empty_String_Before_Dot() {
    	String fileName = ".txt";
    	assertEquals("No", FileNameCheck.fileNameCheck(fileName));
    }
    @Test
    public void Test_More_Than_Three_Digits_In_Name() {
    	String fileName = "this1234is_valid.txt";
    	assertEquals("No", FileNameCheck.fileNameCheck(fileName));
    }
    @Test
    void testFileNameCheck1() {
        assertEquals("Yes", FileNameCheck.fileNameCheck("example.txt"));
    }
    @Test
    void testFileNameCheck_ValidFileNames() {
        assertEquals("Yes", FileNameCheck.fileNameCheck("example.txt"));
    }
                                    
}