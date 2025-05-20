package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of IntToMiniRoman.
*/
class IntToMiniRomanTest {
    @Test
    void testIntToMiniRoman_LowerBound() {
        String result = IntToMiniRoman.intToMiniRoman(1);
        assertEquals("i", result);
    }
    
    @Test
        void testNothing(){
            IntToMiniRoman s = new IntToMiniRoman();
            }
    @Test
    void testIntToMiniRoman_1() {
        assertEquals("i", IntToMiniRoman.intToMiniRoman(1));
    }
    @Test
    void testIntToMiniRoman_2() {
        assertEquals("iv", IntToMiniRoman.intToMiniRoman(4));
    }
    @Test
    void testIntToMiniRoman_3() {
        assertEquals("v", IntToMiniRoman.intToMiniRoman(5));
    }
    @Test
    void testIntToMiniRoman_4() {
        assertEquals("", IntToMiniRoman.intToMiniRoman(0));
    }
    @Test
    void testIntToMiniRoman_5() {
        assertEquals("", IntToMiniRoman.intToMiniRoman(-1));
    }
    @Test
    public void testIntToMiniRoman_6() {
        assertEquals("ix", IntToMiniRoman.intToMiniRoman(9));
    }
                                    
}