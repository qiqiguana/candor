package original;

import java.util.ArrayList;

import java.util.HashSet;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Unique.
*/
class UniqueTest {

    @Test
    void testUniqueRemovesDuplicates() {
        List<Integer> inputList = new ArrayList<>();
        inputList.add(1);
        inputList.add(2);
        inputList.add(2);
        inputList.add(3);
        inputList.add(4);
        inputList.add(4);

        List<Integer> result = Unique.unique(inputList);
        assertEquals(4, result.size());
    }
}