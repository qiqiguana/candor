package original;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StrongestExtension.
*/
class StrongestExtensionTest {
    @Test
    void testStrongestExtension_SimpleCase_ReturnsExpectedResult() {
        List<String> extensions = List.of("AA", "Be", "CC");
        String actual = StrongestExtension.strongestExtension("my_class", extensions);
        assertEquals("my_class.AA", actual);
    }
    
    @Test
        public void testNothing(){
            StrongestExtension s = new StrongestExtension();
            }
    @Test
    public void testStrongestExtension_MultipleExtensions() {
        String className = "MultiExt";
        java.util.List<String> extensions = java.util.Arrays.asList("EXT1", "EXT2");
        String expected = "MultiExt.EXT1";
        assertEquals(expected, StrongestExtension.strongestExtension(className, extensions));
    }
    @Test
    public void testStrongestExtension_SingleExtension() {
        String className = "SingleExt";
        List<String> extensions = Arrays.asList("EXTENSION");
        String expected = "SingleExt.EXTENSION";
        assertEquals(expected, StrongestExtension.strongestExtension(className, extensions));
    }
    @Test
    public void testExtension_OnlyUppercase() {
        String className = "UppercaseExt";
        List<String> extensions = Arrays.asList("ALLUPPER");
        String expected = "UppercaseExt.ALLUPPER";
        assertEquals(expected, StrongestExtension.strongestExtension(className, extensions));
    }
    @Test
    public void testExtension_OnlyLowercase() {
        String className = "LowercaseExt";
        List<String> extensions = Arrays.asList("alllower");
        String expected = "LowercaseExt.alllower";
        assertEquals(expected, StrongestExtension.strongestExtension(className, extensions));
    }
                                    
}