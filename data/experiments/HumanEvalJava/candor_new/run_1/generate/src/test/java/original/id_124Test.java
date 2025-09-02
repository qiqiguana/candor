package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ValidDate.
*/
class ValidDateTest {

    @Test
    void testValidDate() {
        String date = "03-11-2000";
        boolean expectedResult = true;
        boolean actualResult = ValidDate.validDate(date);
        assertEquals(expectedResult, actualResult);
    }
    
    @Test
        public void testNothing(){
            ValidDate s = new ValidDate();
            }
    @Test
    void testEmptyDateString() {
        assertFalse(ValidDate.validDate(""));
    }
    @Test
    void testDayOutOfRangeForFebruary() {
        assertFalse(ValidDate.validDate("02-30-2000"));
    }
    @Test
    void testDayOutOfRangeForFebruaryGreaterThanTwentyEight() {
        assertTrue(ValidDate.validDate("02-29-2000"));
    }
    @Test
    void testDayOutOfRangeForAprilLessThanOne1() {
        String date = "04-00-2000";
        boolean isValidDate = ValidDate.validDate(date);
        assertFalse(isValidDate);
    }
    @Test
    void testDayOutOfRangeForAprilGreaterThanThirty2() {
        assertFalse(ValidDate.validDate("04-31-2000"));
    }
    @Test
    void testDayOutOfRangeForMayLessThanOneFixed() {
        assertFalse(ValidDate.validDate("05-00-2000"));
    }
    @Test
    void testDayOutOfRangeForMayGreaterThanThirtyOne() {
        assertFalse(ValidDate.validDate("05-32-2000"));
    }
    @Test
    void testDayOutOfRangeForJuneLessThanOneFixed() {
        String dateString = "06-00-2000";
        assertFalse(ValidDate.validDate(dateString));
    }
    @Test
    public void testValidDate_InvalidFormat() {
        String date = "03/11/2000";
        assertFalse(ValidDate.validDate(date));
    }
    @Test
    public void testValidDate_MonthLessThan1() {
        String date = "00-11-2000";
        assertFalse(ValidDate.validDate(date));
    }
    @Test
    public void testValidDate_MonthGreaterThan12() {
        String date = "13-11-2000";
        assertFalse(ValidDate.validDate(date));
    }
    @Test
    public void testValidDateApril30Days() {
        String dateString = "04-30-2020";
        Boolean result = ValidDate.validDate(dateString);
        assertTrue(result);
    }
                                    
}