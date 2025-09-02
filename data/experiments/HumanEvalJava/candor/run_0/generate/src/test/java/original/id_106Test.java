package original;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of F.
*/
class FTest {
    @Test
    void test_f_should_return_list_of_size_n() {
        // Arrange and Act
        List<Integer> result = F.f(5);
        // Assert
        assertEquals(5, result.size());
    }
    
    @Test
        public void testNothing(){
            F s = new F();
            }
    @Test
    public void testFSmallInput() {
        int n = 5;
        List<Integer> expected = Arrays.asList(1, 2, 6, 24, 15);
        assertEquals(expected, F.f(n));
    }
                                    
}