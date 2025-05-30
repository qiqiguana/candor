package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringSequence.
*/
class StringSequenceTest {
    @Test
    void testStringSequence_ReturnsEmptyString_WhenInputIsNegative() {
        // Arrange and Act
        String result = StringSequence.stringSequence(-1);

        // Assert
        assertEquals("", result);
    }
    
    @Test
        void testNothing(){
            StringSequence s = new StringSequence();
            }
    @Test
    public void test_stringSequence_with_n_equal_to_0() {
        String result = StringSequence.stringSequence(0);
        assertEquals("0", result);
    }
    @Test
    public void test_stringSequence_with_n_greater_than_0() {
        String result = StringSequence.stringSequence(5);
        assertEquals("0 1 2 3 4 5", result);
    }
    @Test
    public void test_stringSequence_with_negative_n() {
        String result = StringSequence.stringSequence(-1);
        assertEquals("", result);
    }
    @Test
    public void test_stringSequence_with_large_n() {
        String result = StringSequence.stringSequence(100);
        assertEquals("0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100", result);
    }
    @Test
    public void test_stringSequence_large_input() {
        String result = StringSequence.stringSequence(100000);
        StringBuilder expected = new StringBuilder();
        for (int i = 0; i <= 100000; i++) {
            expected.append(i).append(" ");
        }
        assertEquals(expected.toString().trim(), result);
    }
                                    
}