package original;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of EvenOddPalindrome.
*/
class EvenOddPalindromeTest {

    @Test
    void testEvenOddPalindrome_SmallInput() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(3);
        assertEquals(2, result.size());
        assertEquals(1, (int)result.get(0)); // number of even palindromes
        assertEquals(2, (int)result.get(1)); // number of odd palindromes
    }
    
    @Test
        void testNothing(){
            EvenOddPalindrome s = new EvenOddPalindrome();
            }
    @Test
    public void TestEvenOddPalindrome_HappyPath() {
        int n = 123;
        List<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(8);
        expectedResult.add(13);
        assertEquals(expectedResult, EvenOddPalindrome.evenOddPalindrome(n));
    }
    @Test
    public void TestEvenOddPalindrome_SmallInput() {
        int n = 1;
        List<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(0);
        expectedResult.add(1);
        assertEquals(expectedResult, EvenOddPalindrome.evenOddPalindrome(n));
    }
    @Test
    public void EvenOddPalindromeTest_Positive_EvenNumber() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(12);
        assertEquals(List.of(4, 6), result);
    }
    @Test
    public void EvenOddPalindromeTest_Positive_OddNumber() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(123);
        assertEquals(List.of(8, 13), result);
    }
    @Test
    public void EvenOddPalindromeTest_Negative_ZeroInput() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(0);
        assertEquals(List.of(0, 0), result);
    }
    @Test
    public void testEvenOddPalindromePositive1() {
        int n = 3;
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(n);
        assertEquals(2, result.size());
        assertEquals(1, (int) result.get(0)); // even
        assertEquals(2, (int) result.get(1)); // odd
    }
    @Test
    public void testEvenOddPalindromePositive2() {
        int n = 12;
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(n);
        assertEquals(2, result.size());
        assertEquals(4, (int) result.get(0)); // even
        assertEquals(6, (int) result.get(1)); // odd
    }
    @Test
    public void testEvenOddPalindromeEdgeCase1() {
        int n = 1;
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(n);
        assertEquals(2, result.size());
        assertEquals(0, (int) result.get(0)); // even
        assertEquals(1, (int) result.get(1)); // odd
    }
    @Test
    void test_evenOddPalindrome_1() {
        List<Integer> result = EvenOddPalindrome.evenOddPalindrome(1);
        assertEquals(result, Arrays.asList(0, 1));
    }
                                    
}