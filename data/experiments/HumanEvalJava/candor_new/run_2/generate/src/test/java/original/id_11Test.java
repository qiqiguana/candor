package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of StringXor.
*/
class StringXorTest {
    @Test
    void testStringXor_DifferentLength_ThrowsIndexOutOfBoundsException() {
        assertThrows(StringIndexOutOfBoundsException.class, () -> StringXor.stringXor("1010", "1"));
    }
    
    @Test
        public void testNothing(){
            StringXor s = new StringXor();
            }
    @Test
    public void testStringXorWithDifferentLengths_PadShorterString() {
        String a = "1010";
        String b = "11";
        int maxLength = Math.max(a.length(),b.length());
        String expected = "1001";
        a = String.format("%" + maxLength + "s",a).replace(' ','0');
        b = String.format("%" + maxLength + "s",b).replace(' ','0');
        assertEquals(expected, StringXor.stringXor(a, b));
    }
                                    
}