package original;

import java.util.Collections;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Deque;
import java.util.Map;
import java.util.List;
class Solution2115 {
    public List<String> findAllRecipes(
  /**
  You have information about n different recipes. You are given a string array recipes and a 2D string array ingredients. The ith recipe has the name recipes[i], and you can create it if you have all the needed ingredients from ingredients[i]. A recipe can also be an ingredient for other recipes, i.e., ingredients[i] may contain a string that is in recipes. You are also given a string array supplies containing all the ingredients that you initially have, and you have an infinite supply of all of them. Return a list of all the recipes that you can create. You may return the answer in any order. Note that two recipes may contain each other in their ingredients. &nbsp; Example 1: Input: recipes = [&quot;bread&quot;], ingredients = [[&quot;yeast&quot;,&quot;flour&quot;]], supplies = [&quot;yeast&quot;,&quot;flour&quot;,&quot;corn&quot;] Output: [&quot;bread&quot;] Explanation: We can create &quot;bread&quot; since we have the ingredients &quot;yeast&quot; and &quot;flour&quot;. Example 2: Input: recipes = [&quot;bread&quot;,&quot;sandwich&quot;], ingredients = [[&quot;yeast&quot;,&quot;flour&quot;],[&quot;bread&quot;,&quot;meat&quot;]], supplies = [&quot;yeast&quot;,&quot;flour&quot;,&quot;meat&quot;] Output: [&quot;bread&quot;,&quot;sandwich&quot;] Explanation: We can create &quot;bread&quot; since we have the ingredients &quot;yeast&quot; and &quot;flour&quot;. We can create &quot;sandwich&quot; since we have the ingredient &quot;meat&quot; and can create the ingredient &quot;bread&quot;. Example 3: Input: recipes = [&quot;bread&quot;,&quot;sandwich&quot;,&quot;burger&quot;], ingredients = [[&quot;yeast&quot;,&quot;flour&quot;],[&quot;bread&quot;,&quot;meat&quot;],[&quot;sandwich&quot;,&quot;meat&quot;,&quot;bread&quot;]], supplies = [&quot;yeast&quot;,&quot;flour&quot;,&quot;meat&quot;] Output: [&quot;bread&quot;,&quot;sandwich&quot;,&quot;burger&quot;] Explanation: We can create &quot;bread&quot; since we have the ingredients &quot;yeast&quot; and &quot;flour&quot;. We can create &quot;sandwich&quot; since we have the ingredient &quot;meat&quot; and can create the ingredient &quot;bread&quot;. We can create &quot;burger&quot; since we have the ingredient &quot;meat&quot; and can create the ingredients &quot;bread&quot; and &quot;sandwich&quot;. &nbsp; Constraints: n == recipes.length == ingredients.length 1 &lt;= n &lt;= 100 1 &lt;= ingredients[i].length, supplies.length &lt;= 100 1 &lt;= recipes[i].length, ingredients[i][j].length, supplies[k].length &lt;= 10 recipes[i], ingredients[i][j], and supplies[k] consist only of lowercase English letters. All the values of recipes and supplies&nbsp;combined are unique. Each ingredients[i] does not contain any duplicate values.
  */
        String[] recipes, List<List<String>> ingredients, String[] supplies) {
        Map<String, List<String>> g = new HashMap<>();
        Map<String, Integer> indeg = new HashMap<>();
        for (int i = 0; i < recipes.length; ++i) {
            for (String v : ingredients.get(i)) {
                g.computeIfAbsent(v, k -> new ArrayList<>()).add(recipes[i]);
            }
            indeg.put(recipes[i], ingredients.get(i).size());
        }
        Deque<String> q = new ArrayDeque<>();
        for (String s : supplies) {
            q.offer(s);
        }
        List<String> ans = new ArrayList<>();
        while (!q.isEmpty()) {
            for (int n = q.size(); n > 0; --n) {
                String i = q.pollFirst();
                for (String j : g.getOrDefault(i, Collections.emptyList())) {
                    indeg.put(j, indeg.get(j) - 1);
                    if (indeg.get(j) == 0) {
                        ans.add(j);
                        q.offer(j);
                    }
                }
            }
        }
        return ans;
    }
}