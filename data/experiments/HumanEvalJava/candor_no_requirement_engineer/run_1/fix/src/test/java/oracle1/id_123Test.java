package oracle1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of GetOddCollatz.
*/
class GetOddCollatzTest {

    @Test
    void testGetOddCollatz_SimpleNumber_ReturnsCorrectList() {
        List<Integer> result = GetOddCollatz.getOddCollatz(5);
        assertEquals(List.of(1, 5), result);
    }
    
    @Test
     void testNothing(){
         GetOddCollatz s = new GetOddCollatz();
         }
    @Test
    public void getOddCollatz_HappyPath_OddNumber() {
        int n = 5;
        List<Integer> expected_result = new ArrayList<>();
        expected_result.add(1);
        expected_result.add(5);
        Collections.sort(expected_result);
        assertEquals(expected_result, GetOddCollatz.getOddCollatz(n));
    }
    @Test
    public void getOddCollatz_HappyPath_EvenNumber() {
        int n = 14;
        List<Integer> expected_result = new ArrayList<>();
        expected_result.add(1);
        expected_result.add(5);
        expected_result.add(7);
        expected_result.add(11);
        expected_result.add(13);
        expected_result.add(17);
        Collections.sort(expected_result);
        assertEquals(expected_result, GetOddCollatz.getOddCollatz(n));
    }
                                  
}