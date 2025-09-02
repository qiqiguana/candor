package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Exchange.
*/
class ExchangeTest {
    @Test
    void testExchange()
    {
        List<Integer> lst1 = List.of(5, 7, 3);
        List<Integer> lst2 = List.of(2, 6, 4);
        String expected = "YES";
        String result = Exchange.exchange(lst1, lst2);
        assertEquals(expected, result);
    }
    
    @Test
        public void testNothing(){
            Exchange s = new Exchange();
            }
                                    
}