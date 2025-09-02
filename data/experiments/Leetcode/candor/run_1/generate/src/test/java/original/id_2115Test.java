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
        String[] recipes = {"recipe1", "recipe2"};
        List<List<String>> ingredients = new ArrayList<>();
        ingredients.add(new ArrayList<>(List.of("ingr1", "ingr2")));
        ingredients.add(new ArrayList<>(Collections.singletonList("ingr3")));
        String[] supplies = {"ingr1", "ingr3"};

        List<String> expected = new ArrayList<>(List.of("recipe2"));
        assertEquals(expected, solution.findAllRecipes(recipes, ingredients, supplies));
    }
}