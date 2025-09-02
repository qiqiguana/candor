package original;

class Solution1641 {
    private Integer[][] f;
    private int n;

    public int countVowelStrings(int n) {
  /**
  Given an integer n, return the number of strings of length n that consist only of vowels (a, e, i, o, u) and are lexicographically sorted. A string s is lexicographically sorted if for all valid i, s[i] is the same as or comes before s[i+1] in the alphabet. &nbsp; Example 1: Input: n = 1 Output: 5 Explanation: The 5 sorted strings that consist of vowels only are [&quot;a&quot;,&quot;e&quot;,&quot;i&quot;,&quot;o&quot;,&quot;u&quot;]. Example 2: Input: n = 2 Output: 15 Explanation: The 15 sorted strings that consist of vowels only are [&quot;aa&quot;,&quot;ae&quot;,&quot;ai&quot;,&quot;ao&quot;,&quot;au&quot;,&quot;ee&quot;,&quot;ei&quot;,&quot;eo&quot;,&quot;eu&quot;,&quot;ii&quot;,&quot;io&quot;,&quot;iu&quot;,&quot;oo&quot;,&quot;ou&quot;,&quot;uu&quot;]. Note that &quot;ea&quot; is not a valid string since &#39;e&#39; comes after &#39;a&#39; in the alphabet. Example 3: Input: n = 33 Output: 66045 &nbsp; Constraints: 1 &lt;= n &lt;= 50&nbsp;
  */
        this.n = n;
        f = new Integer[n][5];
        return dfs(0, 0);
    }

    private int dfs(int i, int j) {
        if (i >= n) {
            return 1;
        }
        if (f[i][j] != null) {
            return f[i][j];
        }
        int ans = 0;
        for (int k = j; k < 5; ++k) {
            ans += dfs(i + 1, k);
        }
        return f[i][j] = ans;
    }
}