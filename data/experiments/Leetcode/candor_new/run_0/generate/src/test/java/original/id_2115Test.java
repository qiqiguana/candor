package original;

import java.util.Collections;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Deque;
import java.util.Map;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
* Test class of Solution2115.
*/
class Solution2115Test {

    @Test
    void testFindAllRecipes_ReturnsExpectedRecipes_WhenInputIsValid() {
        // Arrange
        String[] recipes = {"banana_bread", "ice_cream"};
        List<List<String>> ingredients = List.of(List.of("flour", "eggs", "bananas"), List.of("cream", "sugar"));
        String[] supplies = {"flour", "eggs", "bananas", "cream", "sugar"};
        Solution2115 solution = new Solution2115();

        // Act
        List<String> result = solution.findAllRecipes(recipes, ingredients, supplies);

        // Assert
        assertEquals(2, result.size());
    }
}