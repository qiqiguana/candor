package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution1154.
*/
class Solution1154Test {
    @Test
    void testDayOfYear() {
        Solution1154 solution = new Solution1154();
        assertEquals(60, solution.dayOfYear("2003-03-01"));
    }
    
    @Test
        public void testNothing(){
            Solution1154 s = new Solution1154();
            }
    @Test
    public void test_dayOfYear_LeapYear() {
        Solution1154 solution = new Solution1154();
        String date = "2020-02-29";
        int expected_result = 60;
        assertEquals(expected_result, solution.dayOfYear(date));
    }
    @Test
    public void test_dayOfYear_NonLeapYear() {
        Solution1154 solution = new Solution1154();
        String date = "2019-03-01";
        int expected_result = 60;
        assertEquals(expected_result, solution.dayOfYear(date));
    }
    @Test
    public void test_dayOfYear_February28th() {
        Solution1154 solution = new Solution1154();
        String date = "2020-02-28";
        int expected_result = 59;
        assertEquals(expected_result, solution.dayOfYear(date));
    }
    @Test
    public void test_dayOfYear_February29th() {
        Solution1154 solution = new Solution1154();
        String date = "2020-02-29";
        int expected_result = 60;
        assertEquals(expected_result, solution.dayOfYear(date));
    }
    @Test
    public void test_dayOfYear_leap_year() {
        Solution1154 solution = new Solution1154();
        String date = "2020-02-29";
        int expected_result = 60;
        assertEquals(expected_result, solution.dayOfYear(date));
    }
    @Test
    public void test_dayOfYear_non_leap_year() {
        Solution1154 solution = new Solution1154();
        String date = "2019-03-01";
        int expected_result = 60;
        assertEquals(expected_result, solution.dayOfYear(date));
    }
    @Test
    public void test_dayOfYear_LeapYear_Fixed() {
        Solution1154 solution = new Solution1154();
        String date = "2020-02-29";
        int expected = 60;
        int actual = solution.dayOfYear(date);
        assertEquals(expected, actual);
    }
    @Test
    public void test_dayOfYear_NonLeapYear_February() {
        Solution1154 solution = new Solution1154();
        String date = "2019-02-01";
        int expected = 32;
        int actual = solution.dayOfYear(date);
        assertEquals(expected, actual);
    }
    @Test
    public void LeapYearTest() {
        Solution1154 solution = new Solution1154();
        String date = "2020-02-29";
        int expected_result = 60;
        int actual_result = solution.dayOfYear(date);
        assertEquals(expected_result, actual_result);
    }
    @Test
    public void NonLeapYearTest() {
        Solution1154 solution = new Solution1154();
        String date = "2019-02-28";
        int expected_result = 59;
        int actual_result = solution.dayOfYear(date);
        assertEquals(expected_result, actual_result);
    }
    @Test
    public void EdgeCaseFebruary29th() {
        Solution1154 solution = new Solution1154();
        String date = "2020-02-29";
        int expected_result = 60;
        int actual_result = solution.dayOfYear(date);
        assertEquals(expected_result, actual_result);
    }
    @Test
    public void EdgeCaseDecember31st() {
        Solution1154 solution = new Solution1154();
        String date = "2020-12-31";
        int expected_result = 366;
        int actual_result = solution.dayOfYear(date);
        assertEquals(expected_result, actual_result);
    }
    @Test
    public void testDayOfYear_LeapYear() {
        Solution1154 solution = new Solution1154();
        String date = "2020-02-29";
        int expected_result = 60;
        assertEquals(expected_result, solution.dayOfYear(date));
    }
    @Test
    public void testDayOfYear_NonLeapYear() {
        Solution1154 solution = new Solution1154();
        String date = "2019-02-28";
        int expected_result = 59;
        assertEquals(expected_result, solution.dayOfYear(date));
    }
    @Test
    public void test_dayOfYear_LeepYear() {
        Solution1154 solution = new Solution1154();
        String date = "2020-02-29";
        int expected_result = 60;
        int result = solution.dayOfYear(date);
        assertEquals(expected_result, result);
    }
    @Test
    public void test_dayOfYear_NonLeepYear() {
        Solution1154 solution = new Solution1154();
        String date = "2019-02-28";
        int expected_result = 59;
        int result = solution.dayOfYear(date);
        assertEquals(expected_result, result);
    }
                                    
}