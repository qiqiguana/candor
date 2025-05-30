package oracle1;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of Exchange.
*/
class ExchangeTest {
    @Test
    void test_exchange_uneven_numbers_can_be_exchanged_with_even_from_second_list() {
        List<Integer> firstList = List.of(1, 2, 3, 4);
        List<Integer> secondList = List.of(2, 6, 4);
        String result = Exchange.exchange(firstList, secondList);
        assertEquals("YES", result);
    }
    
    @Test
        void testNothing(){
            Exchange s = new Exchange();
            }
    @Test
    void testExchange1(){
    	List<Integer> list1 = Arrays.asList(1, 2, 3, 4);
    	List<Integer> list2 = Arrays.asList(1, 2, 3, 4);
    	assertEquals("YES", Exchange.exchange(list1, list2));
    }
    @Test
    public void test_exchange_happy_path_corrected() {
        List<Integer> lst1 = Arrays.asList(1, 2, 3, 4);
        List<Integer> lst2 = Arrays.asList(1, 2, 3, 4);
        String result = oracle1.Exchange.exchange(lst1, lst2);
        assertEquals("YES", result);
    }
    @Test public void test_exchange_even_numbers_in_both_lists_1() { List<Integer> lst1 = Arrays.asList(100, 200); List<Integer> lst2 = Arrays.asList(200, 200); String result = Exchange.exchange(lst1, lst2); assertEquals("YES", result); }
    @Test
    public void test_exchange_odd_number_in_list1_no_even_numbers_in_list2() {
        List<Integer> lst1 = Arrays.asList(3, 4);
        List<Integer> lst2 = Arrays.asList(1, 3);
        String result = Exchange.exchange(lst1, lst2);
        assertEquals("NO", result);
    }
                                    
}