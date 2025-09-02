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
    void testFindAllRecipes() {
        // Arrange
        String[] recipes = {"hammer", "saw", "nail"};
        List<List<String>> ingredients = List.of(List.of("a", "b"), List.of("c"), List.of("d", "e"));
        String[] supplies = {"a", "c", "d"};

        // Act
        Solution2115 solution = new Solution2115();
        List<String> result = solution.findAllRecipes(recipes, ingredients, supplies);

        // Assert
        assertEquals(List.of("saw"), result);
    }
}