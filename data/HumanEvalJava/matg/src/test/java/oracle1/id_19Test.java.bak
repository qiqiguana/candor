package oracle1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of SortNumbers.
*/
class SortNumbersTest {
    @Test
    void testSortNumbers_SingleNumber_ReturnsSameNumber() {
        String input = "three";
        String expectedOutput = "three";
        assertEquals(expectedOutput, SortNumbers.sortNumbers(input));
    }
    
    @Test
        void testNothing(){
            SortNumbers s = new SortNumbers();
            }
    @Test
    public void TestEmptyString() {
    	assertEquals("", SortNumbers.sortNumbers(""));
    }
    @Test
    public void TestSingleNumber() {
    	assertEquals("three", SortNumbers.sortNumbers("three"));
    }
    @Test
    public void TestDuplicateNumbers() {
    	assertEquals("five five seven", SortNumbers.sortNumbers("seven five five"));
    }
    @Test
    public void TestInvalidNumber1() {
    	assertNotEquals("ten", SortNumbers.sortNumbers("ten"));
    }
    @Test
    public void TestEdgeCaseNumbers() {
    	assertEquals("zero nine", SortNumbers.sortNumbers("nine zero"));
    }
    @Test
    public void TestLongInputString() {
    	assertEquals("zero one two three four five six seven eight nine", SortNumbers.sortNumbers("nine eight seven six five four three two one zero"));
    }
                                    
}