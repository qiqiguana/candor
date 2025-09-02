package original;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Add.
*/
class AddTest {
    @Test
    void testAddTwoPositiveNumbers() {
        assertEquals(5, Add.add(2, 3));
    }
    
    @Test
        public void testNothing(){
            Add s = new Add();
            }
    @Test
    void testAddPositiveNumbers1() {
        Add myAdd = new Add();
        assertEquals(30, myAdd.add(10, 20));
    }
                                    
}