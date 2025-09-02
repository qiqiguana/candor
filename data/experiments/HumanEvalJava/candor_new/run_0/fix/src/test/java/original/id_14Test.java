package original;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of AllPrefixes.
*/
class AllPrefixesTest {
    @Test
    void testAllPrefixes_EmptyString_ReturnsEmptyList() {
        // Arrange
        String input = "";

        // Act
        List<Object> result = AllPrefixes.allPrefixes(input);

        // Assert
        assertTrue(result.isEmpty());
    }
    
    @Test
        public void testNothing(){
            AllPrefixes s = new AllPrefixes();
            }
    @Test
    public void testAllPrefixesSingleSpaceString() {
        List<Object> result = AllPrefixes.allPrefixes(" ");
        if (result.size() == 2 && result.get(0).equals("") && result.get(1).equals(" ")) {
            assertTrue(true);
        } else {
           fail();
        }
    }
                                    
}