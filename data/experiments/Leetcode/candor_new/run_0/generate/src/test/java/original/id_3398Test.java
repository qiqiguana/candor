package original;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution3398.
*/
class Solution3398Test {

@Test
void testMinLengthWhenInputMatchesThePattern() {
    // Arrange
    String input = "0101";
    int numOps = 0;
    Solution3398 solution = new Solution3398();

    // Act
    int result = solution.minLength(input, numOps);

    // Assert
    assertEquals(1, result);
}
@Test
public void testMinLength_1(){
    Solution3398 s = new Solution3398();
    int result = s.minLength("000001", 1);
    assertEquals(2, result);
}
}