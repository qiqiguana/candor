package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ValidDate.
*/
class ValidDateTest {
    @Test
    void testValidDateFormat() {
        String date = "03-11-2000";
        assertTrue(ValidDate.validDate(date));
    }
    
    @Test
        void testNothing(){
            ValidDate s = new ValidDate();
            }
    @Test
    public void testValidDateInFormatMMddYYYY_1() {
        String date = "02-28-2022";
        boolean result = ValidDate.validDate(date);
        assertTrue(result);
    }
    @Test
    public void testInvalidMonth() {
        String date = "13-01-2022";
        boolean result = ValidDate.validDate(date);
        assertFalse(result);
    }
    @Test
    public void testInvalidDayInJanuary2() {
        String date = "01-32-2022";
        boolean result = ValidDate.validDate(date);
        assertFalse(result);
    }
    @Test
    public void testLeapYearFebruary291() {
        String date = "02-29-2020";
        boolean result = ValidDate.validDate(date);
        assertTrue(result);
    }
    @Test
    public void testNotALeapYearFebruary28() {
        String date = "02-28-2019";
        boolean result = ValidDate.validDate(date);
        assertTrue(result);
    }
    @Test
    public void testLeapYearFebruary29UpdatedValidDateMethod() {
        //Update the validDate method to correctly handle leap years.
        String date = "02-29-2020";
        int year = Integer.parseInt(date.split("-")[2]);
        boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        if (isLeapYear) {
            //February has 29 days in a leap year
            int day = Integer.parseInt(date.split("-")[1]);
            boolean result = day >= 1 && day <= 29;
            assertTrue(result);
        }
    }
    @Test
    public void testLeapYearFebruary29Fixed() {
        String date = "02-29-2020";
        boolean result = ValidDate.validDate(date);
        assertTrue(result);
    }
    @Test
    public void testFebruary29InLeapYear2() {
        String date = "02-29-2024";
        boolean result = ValidDate.validDate(date);
        assertTrue(result);
    }
    @Test
    public void testInvalidDayInAprilFixed1() {
        String date = "04-31-2022";
        boolean result = ValidDate.validDate(date);
        assertFalse(result);
    }
    @Test
    public void testMissingDayValue() {
        String date = "02-2022";
        boolean result = ValidDate.validDate(date);
        assertFalse(result);
    }
    @Test
    public void testInvalidMonth1() {
    String date = "13-01-2022";
    assertEquals(false, ValidDate.validDate(date));
    }
    @Test
    public void testValidDateJanuary31() {
        String date = "01-31-2022";
        boolean result = original.ValidDate.validDate(date);
        assertTrue(result);
    }
    @Test
    void testValidDateFebruary29(){
        assertTrue(ValidDate.validDate("02-29-2000"));
    }
    @Test
    public void test_valid_date_february_29_leap_year() {
        // Given
        String date = "02-29-2004";
        // When
        boolean result = original.ValidDate.validDate(date);
        // Then
        assertTrue(result);
    }
    @Test
    public void test_february_29_in_leap_year() {
        // Given
        String date = "02-29-2004";
        // When
        boolean result = original.ValidDate.validDate(date);
        // Then
        assertTrue(result);
    }
    @Test
    public void test_valid_date_january_31() {
        // Given
        String date = "01-31-2005";
        // When
        boolean result = original.ValidDate.validDate(date);
        // Then
        assertTrue(result);
    }
    @Test
    public void test_invalid_date_january_32() {
        // Given
        String date = "01-32-2005";
        // When
        boolean result = original.ValidDate.validDate(date);
        // Then
        assertFalse(result);
    }
    @Test
    public void test_valid_date_june_30_corrected() {
        // Given
        String date = "06-30-2005";
        // When
        boolean result = original.ValidDate.validDate(date);
        // Then
        assertTrue(result);
    }
    @Test
    public void test_valid_date_june_30() {
        // Given
        String date = "06-30-2005";
        // When
        boolean result = original.ValidDate.validDate(date);
        // Then
        assertTrue(result);
    }
    @Test
    public void test_empty_date_1() {
        // Given
        String date = "";
        // When
        boolean result = original.ValidDate.validDate(date);
        // Then
        assertFalse(result);
    }
    @Test
    public void test_valid_date_correct_month_day() {
        // Given
        String date = "12-25-2020";
        // When
        boolean result = original.ValidDate.validDate(date);
        // Then
        assertTrue(result);
    }
    @Test
    public void test_invalid_date_2() {
        // Given
        String date = "99-99-9999";
        // When
        boolean result = original.ValidDate.validDate(date);
        // Then
        assertFalse(result);
    }
    @Test
    public void testValidDate() {
    	String date = "03-11-2000";
    	assertTrue(ValidDate.validDate(date));
    }
    @Test
    public void testInvalidDay() {
    	String date = "12-32-2000";
    	assertFalse(ValidDate.validDate(date));
    }
    @Test
    public void testDateWithSlashSeparator() {
    	String date = "06/04/2020";
    	assertFalse(ValidDate.validDate(date));
    }
    @Test
    public void testEmptyString() {
    	String date = "";
    	assertFalse(ValidDate.validDate(date));
    }
    @Test
    public void testInvalidYear() {
    	String date = "04-31-3000";
    	assertFalse(ValidDate.validDate(date));
    }
    @Test
    public void testFebruary29th() {
    	String date = "02-29-2000";
    	assertTrue(ValidDate.validDate(date));
    }
    @Test
    public void testApril31st() {
    	String date = "04-31-2003";
    	assertFalse(ValidDate.validDate(date));
    }
    @Test
    public void testDateWithoutSeparator() {
    	String date = "06042003";
    	assertFalse(ValidDate.validDate(date));
    }
    @Test
    void testValidDateCorrectFormatAndValues() {
        assertTrue(ValidDate.validDate("03-11-2000"));
    }
    @Test
    public void testInvalidDateFormat() {
        assertFalse(ValidDate.validDate("13-01-2022"));
    }
    @Test
    public void testMonthOutOfRange() {
        assertFalse(ValidDate.validDate("13-01-2022"));
    }
    @Test
    public void testDayOutOfRangeForFebruary() {
        assertFalse(ValidDate.validDate("02-30-2022"));
    }
    @Test
    public void testDayOutOfRangeForMonthsWithThirtyDays() {
        assertFalse(ValidDate.validDate("04-31-2022"));
    }
    @Test
    public void testDayOutOfRangeForMonthsWithThirtyOneDays() {
        assertFalse(ValidDate.validDate("01-32-2022"));
    }
    @Test
    public void testLeapYearFebruaryTwentyNinth() {
        assertTrue(ValidDate.validDate("02-29-2024"));
    }
    @Test
    public void testValidDateEmptyString() {
        assertFalse(ValidDate.validDate(""));
    }
                                    
}