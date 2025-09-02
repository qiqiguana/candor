package original;

import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
* Test class of GetRow.
*/
class GetRowTest {

@Test
void testGetRowWithEmptyList() {
    List<Object> lst = new ArrayList<>();
    int x = 1;
    assertEquals(0, GetRow.getRow(lst, x).size());
}
}