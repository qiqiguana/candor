package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StrongestExtension.
*/
class StrongestExtensionTest {

    @Test
    void testStrongestExtension() {
        String className = "Slices";
        List<String> extensions = List.of("SErviNGSliCes", "Cheese", "StuFfed");
        String expected = "Slices.SErviNGSliCes";
        assertEquals(expected, StrongestExtension.strongestExtension(className, extensions));
    }
    
    @Test
        public void testNothing(){
            StrongestExtension s = new StrongestExtension();
            }
    @Test
    public void TestExtensionWithNoLetters1() {
        String className = "MyClass";
        List<String> extensions = Arrays.asList("123", "456");
        assertEquals("MyClass.123", StrongestExtension.strongestExtension(className, extensions));
    }
                                    
}