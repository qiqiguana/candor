package original;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of TotalMatch.
*/
class TotalMatchTest {
    @Test
    void testTotalMatch_ReturnsListWithLessChars() {
        List<Object> list1 = List.of("hi", "admin");
        List<Object> list2 = List.of("hI", "Hi");
        assertEquals(list2, TotalMatch.totalMatch(list1, list2));
    }
    
    @Test
        public void testNothing(){
            TotalMatch s = new TotalMatch();
            }
    @Test
    public void testTotalMatchEmptyListsWithReturnFixed() {
        List<java.lang.Object> lst1 = new java.util.ArrayList<>();
        List<java.lang.Object> lst2 = new java.util.ArrayList<>();
        assertEquals(new java.util.ArrayList<>(), TotalMatch.totalMatch(lst1, lst2));
    }
    @Test
    public void testTotalMatchWithNullElementsInLst12() {
        List<java.lang.Object> lst1 = new java.util.ArrayList<>(java.util.Arrays.asList(null, "hi"));
        List<java.lang.Object> lst2 = new java.util.ArrayList<>(java.util.Arrays.asList("hI", "Hi"));
        Object result = original.TotalMatch.totalMatch(lst1, lst2);
        if (result instanceof java.util.List) {
            for (Object value : (java.util.List<?>) result) {
                if (value != null && !(value instanceof String)) {
                    org.junit.jupiter.api.Assertions.fail();
                }
            }
        } else {
            org.junit.jupiter.api.Assertions.fail();
        }
    }
                                    
}