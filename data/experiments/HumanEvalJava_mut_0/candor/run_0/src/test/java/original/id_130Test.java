package original;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Tri.
*/
class TriTest {

@Test
public void testTriForZeroInput() {
    List<Number> result = Tri.tri(0);
    assertEquals(Arrays.asList(1), result);
}
}