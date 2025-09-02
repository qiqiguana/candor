package original;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of IsNested.
*/
class IsNestedTest {
    @Test
    void test_isNested() {
        assertEquals(true, IsNested.isNested("[[]]"));
    }
    
    @Test
        public void testNothing(){
            IsNested s = new IsNested();
            }
    @Test
    public void test_is_nested_with_single_pair() {
        assertTrue(IsNested.isNested("[[]]") );
        assertFalse(IsNested.isNested("[][]") );
        assertFalse(IsNested.isNested("[]") );
    }
    @Test
    public void test_single_bracket() {
        assertFalse(IsNested.isNested("["));
    }
                                    
}