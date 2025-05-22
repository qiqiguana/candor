package oracle1;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of TotalMatch.
*/
class TotalMatchTest {

    @Test
    void testTotalMatch_DifferentLengthStrings_ReturnFirstList() {
        List<Object> list1 = List.of("hi", "admin");
        List<Object> list2 = List.of("hI", "Hi");
        assertEquals(list2, TotalMatch.totalMatch(list1, list2));
    }
    
    @Test
        void testNothing(){
            TotalMatch s = new TotalMatch();
            }
    @Test
    public void testTotalMatchWithEmptyLists() {
        java.util.List<java.lang.Object> lst1 = new java.util.ArrayList<>();
        java.util.List<java.lang.Object> lst2 = new java.util.ArrayList<>();
        org.junit.jupiter.api.Assertions.assertEquals(lst1, TotalMatch.totalMatch(lst1, lst2));
    }
    @Test
    public void testTotalMatchWithNonStringElements1() {
        List<java.lang.Object> lst1 = java.util.Arrays.asList("hi", "123");
        List<java.lang.Object> lst2 = java.util.Arrays.asList("hI", "Hi");
        assertEquals(lst2, oracle1.TotalMatch.totalMatch(lst1, lst2));
    }
                                    
}