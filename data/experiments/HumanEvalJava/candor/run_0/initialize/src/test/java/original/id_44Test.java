package original;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of ChangeBase.
*/
class ChangeBaseTest {

@Test
void testChangeBase_NormalNumbers_Base2() {
    String result = ChangeBase.changeBase(8, 2);
    assertEquals("1000", result);
}

@Test
    public void testNothing(){
        ChangeBase s = new ChangeBase();
        }
                                
}