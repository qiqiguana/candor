package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of CountUpTo.
*/
class CountUpToTest {

@Test
void countUpTo_WhenInputIs5_ReturnsPrimeNumbers() {
        List<Object> expected = new ArrayList<>();
        expected.add(2);
        expected.add(3);
        assertEquals(expected, CountUpTo.countUpTo(5));
}

@Test
    public void testNothing(){
        CountUpTo s = new CountUpTo();
        }
@Test
public void testCountUpTo_n_5() {
    int n = 5;
    List<Object> expected = new ArrayList<>();
    expected.add(2);
    expected.add(3);
    assertEquals(expected, CountUpTo.countUpTo(n));
}
@Test
public void testCountUpTo_n_0() {
    int n = 0;
    List<Object> expected = new ArrayList<>();
    assertEquals(expected, CountUpTo.countUpTo(n));
}
@Test
public void testCountUpTo_n_47() {
    int n = 47;
    List<Object> expected = new ArrayList<>();
    expected.add(2);
    expected.add(3);
    expected.add(5);
    expected.add(7);
    expected.add(11);
    expected.add(13);
    expected.add(17);
    expected.add(19);
    expected.add(23);
    expected.add(29);
    expected.add(31);
    expected.add(37);
    expected.add(41);
    expected.add(43);
    assertEquals(expected, CountUpTo.countUpTo(n));
}
@Test
public void testCountUpTo_n_101() {
    int n = 101;
    List<Object> expected = new ArrayList<>();
    expected.add(2);
    expected.add(3);
    expected.add(5);
    expected.add(7);
    expected.add(11);
    expected.add(13);
    expected.add(17);
    expected.add(19);
    expected.add(23);
    expected.add(29);
    expected.add(31);
    expected.add(37);
    expected.add(41);
    expected.add(43);
    expected.add(47);
    expected.add(53);
    expected.add(59);
    expected.add(61);
    expected.add(67);
    expected.add(71);
    expected.add(73);
    expected.add(79);
    expected.add(83);
    expected.add(89);
    expected.add(97);
    assertEquals(expected, CountUpTo.countUpTo(n));
}
@Test
public void testCountUpTo_n_47_1() {
    int n = 47;
    assertNotNull(CountUpTo.countUpTo(n));
}
@Test
public void testCountUpTo_n_101_2() {
    int n = 101;
    assertTrue(CountUpTo.countUpTo(n).size() > 0);
}
                                
}