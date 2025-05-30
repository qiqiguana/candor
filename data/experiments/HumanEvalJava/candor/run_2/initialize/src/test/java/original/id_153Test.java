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
        String className = "my_class";
        List<String> extensions = List.of("AA", "Be", "CC");
        String expected = "my_class.AA";
        String actual = StrongestExtension.strongestExtension(className, extensions);
        assertEquals(expected, actual);
    }
    
    @Test
        public void testNothing(){
            StrongestExtension s = new StrongestExtension();
            }
    @Test
    public void testStrongestExtensionWithNullInput() {
        assertThrows(NullPointerException.class, () -> StrongestExtension.strongestExtension(null, null));
    }
                                    
}