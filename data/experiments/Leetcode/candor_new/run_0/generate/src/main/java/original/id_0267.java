package original;

import java.util.ArrayList;
import java.util.List;
class Solution0267 {
    private List<String> ans = new ArrayList<>();
    private int[] cnt = new int[26];
    private int n;

    public List<String> generatePalindromes(String s) {
  /**
  Given a string s, return all the palindromic permutations (without duplicates) of it. You may return the answer in any order. If s has no palindromic permutation, return an empty list. &nbsp; Example 1: Input: s = "aabb" Output: ["abba","baab"] Example 2: Input: s = "abc" Output: [] &nbsp; Constraints: 1 &lt;= s.length &lt;= 16 s consists of only lowercase English letters.
  */
        n = s.length();
        for (char c : s.toCharArray()) {
            ++cnt[c - 'a'];
        }
        String mid = "";
        for (int i = 0; i < 26; ++i) {
            if (cnt[i] % 2 == 1) {
                if (!"".equals(mid)) {
                    return ans;
                }
                mid = String.valueOf((char) (i + 'a'));
            }
        }
        dfs(mid);
        return ans;
    }

    private void dfs(String t) {
        if (t.length() == n) {
            ans.add(t);
            return;
        }
        for (int i = 0; i < 26; ++i) {
            if (cnt[i] > 1) {
                String c = String.valueOf((char) (i + 'a'));
                cnt[i] -= 2;
                dfs(c + t + c);
                cnt[i] += 2;
            }
        }
    }
}