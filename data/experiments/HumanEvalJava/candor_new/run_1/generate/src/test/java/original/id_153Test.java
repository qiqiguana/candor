package original;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StrongestExtension.
*/
class StrongestExtensionTest {
    @Test
    void testStrongestExtension() {
        List<String> extensions = List.of("SErviNGSliCes", "Cheese", "StuFfed");
        String result = StrongestExtension.strongestExtension("Slices", extensions);
        assertEquals("Slices.SErviNGSliCes", result);
    }
    
    @Test
        public void testNothing(){
            StrongestExtension s = new StrongestExtension();
            }
                                    
}