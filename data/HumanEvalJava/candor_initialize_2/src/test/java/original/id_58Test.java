package original;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Common.
*/
class CommonTest {

    @Test
    void test_common_should_ReturnSortedUniqueCommonElements() {
        // arrange
        List<Integer> l1 = new ArrayList<>(Arrays.asList(4, 3, 2, 8));
        List<Object> l2 = new ArrayList<>(Arrays.asList(3, 2, 4));
        List<Object> expectedList = Arrays.asList(2, 3, 4);

        // act
        List<Object> result = Common.common(l1, l2);

        // assert
        assertEquals(expectedList, result);
    }
    
    @Test
        public void testNothing(){
            Common s = new Common();
            }
    @Test
    public void testCommonClassInitialization() {
        assertDoesNotThrow(() -> new Common());
    }
    @Test
    public void testCommonMethodWithNonIntegerValuesInL21() {
        List<Integer> l1 = Arrays.asList(1, 4, 3, 34, 653, 2, 5);
        List<Object> l2 = Arrays.asList(5, 'a', 1, 5, 9, 653, 121);
        List<Object> expected = Arrays.asList(1, 5, 653);
        assertEquals(expected, Common.common(l1, l2));
    }
    @Test
    public void testCommonMethodWithDuplicateValuesInL21() {
        List<Integer> l1 = Arrays.asList(1, 4, 3, 34, 653, 2, 5);
        List<Object> l2 = Arrays.asList(5, 5, 1, 5, 9, 653, 121);
        List<Object> expected = Arrays.asList(1, 5, 653);
        assertEquals(expected, Common.common(l1, l2));
    }
    @Test
    public void testCommonMethodWithEmptyL21() {
        List<Integer> l1 = Arrays.asList(1, 4, 3, 34, 653, 2, 5);
        List<Object> l2 = new ArrayList<>();
        List<Object> expected = new ArrayList<>();
        assertEquals(expected, Common.common(l1, l2));
    }
    @Test
    public void testCommonMethodWithL2ContainingJavaLangObjectInstances() {
        List<Integer> l1 = Arrays.asList(1, 4, 3, 34, 653, 2, 5);
        List<java.lang.Object> l2 = new ArrayList<>();
        l2.add(new java.lang.Object());
        l2.add(5);
        l2.add(new java.lang.Object());
        l2.add(1);
        l2.add(new java.lang.Object());
        l2.add(653);
        List<java.lang.Object> expected = Arrays.asList(1, 5, 653);
        assertEquals(expected, Common.common(l1, l2));
    }
                                    
}