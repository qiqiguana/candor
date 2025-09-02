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
        Solution2115 solution = new Solution2115();
        String[] recipes = {"bread","sandwich"};
        List<List<String>> ingredients = List.of(List.of("yeast","flour"),List.of("bread","meat"));
        String[] supplies = {"yeast","flour","meat"};
        List<String> expected = List.of("bread", "sandwich");
        assertEquals(expected, solution.findAllRecipes(recipes, ingredients, supplies));
    }

}