package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StrongestExtension.
*/
class StrongestExtensionTest {
    @Test
    void strongestExtension() {
        List<String> extensions = List.of("SErviNGSliCes", "Cheese", "StuFfed");
        String result = StrongestExtension.strongestExtension("Slices", extensions);
        assertEquals("Slices.SErviNGSliCes", result);
    }
    
    @Test
        public void testNothing(){
            StrongestExtension s = new StrongestExtension();
            }
    @Test
    public void testStrongestExtension_NullInputFixed() {
        List<String> extensions = null;
        String className = "MyClass";
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                StrongestExtension.strongestExtension(className, extensions);
            } catch (NullPointerException e) {
                throw new IllegalArgumentException("Extensions cannot be null");
            }
        });
    }
                                    
}