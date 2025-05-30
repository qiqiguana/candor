package oracle1;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Skjkasdkd.
*/
class SkjkasdkdTest {
    @Test
    void test_skjkasdkd_singlePrime() {
        List<Integer> numbers = List.of(8191);
        int result = Skjkasdkd.skjkasdkd(numbers);
        assertEquals(19, result);
    }
    
    @Test
        void testNothing(){
            Skjkasdkd s = new Skjkasdkd();
            }
    @Test
    public void testPrimeSumSinglePrimeList() {
        List<Integer> lst = Arrays.asList(7);
        assertEquals(7, Skjkasdkd.skjkasdkd(lst));
    }
    @Test
    public void testPrimeSumSinglePrimeList2() {
        List<Integer> lst = Arrays.asList(7);
        assertEquals(7, Skjkasdkd.skjkasdkd(lst));
    }
    @Test
    public void testPrimeSumSinglePrimeList_1() {
        List<Integer> lst = Arrays.asList(7);
        assertEquals(7, Skjkasdkd.skjkasdkd(lst));
    }
    @Test
    void testSkjkasdkd() {
        List<Integer> lst = Arrays.asList(0,3,2,1,3,5,7,4,5,5,5,2,181,32,4,32,3,2,32,324,4,3);
        int result = Skjkasdkd.skjkasdkd(lst);
        assertEquals(10, result);
    }
                                    
}