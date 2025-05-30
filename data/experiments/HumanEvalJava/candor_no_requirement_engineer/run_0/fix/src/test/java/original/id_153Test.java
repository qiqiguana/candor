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
    void testStrongestExtension_WhenExtensionsHaveSameStrength_ReturnFirstInList() {
        // Arrange
        String className = "my_class";
        List<String> extensions = List.of("AA", "Be", "CC");

        // Act
        String result = StrongestExtension.strongestExtension(className, extensions);

        // Assert
        assertEquals("my_class.AA", result);
    }
    
    @Test
        void testNothing(){
            StrongestExtension s = new StrongestExtension();
            }
    @Test
    public void testSingleExtension_UppercaseLetters() {
        String className = "MyClass";
        List<String> extensions = Arrays.asList("SErviNGSliCes");
        String expected = "MyClass.SErviNGSliCes";
        assertEquals(expected, StrongestExtension.strongestExtension(className, extensions));
    }
    @Test
    public void testSingleExtension_LowercaseLetters_1() {
        String className = "MyClass";
        List<String> extensions = Arrays.asList("seRvInGsLiCeS");
        String expected = "MyClass.seRvInGsLiCeS";
        assertEquals(expected, StrongestExtension.strongestExtension(className, extensions));
    }
                                    
}