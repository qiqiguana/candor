package original;

import java.util.Arrays;
import java.util.ArrayList;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of EvenOddPalindrome.
*/
class EvenOddPalindromeTest {
    @Test
    void testEvenOddPalindrome() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(3);
        assertEquals(result.get(0).intValue(), 1);
    }
    
    @Test
        public void testNothing(){
            EvenOddPalindrome s = new EvenOddPalindrome();
            }
    @Test
    public void test_even_odd_palindrome_with_input_1() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(1);
        assertEquals(List.of(0, 1), result);
    }
    @Test
    public void test_even_odd_palindrome_with_input_3() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(3);
        assertEquals(java.util.Arrays.asList(1, 2), result);
    }
    @Test
    public void test_even_odd_palindrome_with_input_12() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(12);
        assertEquals(java.util.Arrays.asList(4, 6), result);
    }
    @Test
    public void test_even_odd_palindrome_with_input_123_fixed() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(123);
        assertEquals(Arrays.asList(8, 13), result);
    }
    @Test
    public void test_even_odd_palindrome_with_input_12_unique() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(12);
        assertEquals(Integer.valueOf(4), result.get(0));
    }
    @Test
    public void test_even_odd_palindrome_with_input_1_new() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(1);
        assertEquals(0, (int)result.get(0));
        assertEquals(1, (int)result.get(1));
    }
                                    
}