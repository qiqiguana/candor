package original;

class Solution2156 {
    public String subStrHash(String s, int power, int modulo, int k, int hashValue) {
  /**
  The hash of a 0-indexed string s of length k, given integers p and m, is computed using the following function: hash(s, p, m) = (val(s[0]) * p0 + val(s[1]) * p1 + ... + val(s[k-1]) * pk-1) mod m. Where val(s[i]) represents the index of s[i] in the alphabet from val(&#39;a&#39;) = 1 to val(&#39;z&#39;) = 26. You are given a string s and the integers power, modulo, k, and hashValue. Return sub, the first substring of s of length k such that hash(sub, power, modulo) == hashValue. The test cases will be generated such that an answer always exists. A substring is a contiguous non-empty sequence of characters within a string. &nbsp; Example 1: Input: s = &quot;leetcode&quot;, power = 7, modulo = 20, k = 2, hashValue = 0 Output: &quot;ee&quot; Explanation: The hash of &quot;ee&quot; can be computed to be hash(&quot;ee&quot;, 7, 20) = (5 * 1 + 5 * 7) mod 20 = 40 mod 20 = 0. &quot;ee&quot; is the first substring of length 2 with hashValue 0. Hence, we return &quot;ee&quot;. Example 2: Input: s = &quot;fbxzaad&quot;, power = 31, modulo = 100, k = 3, hashValue = 32 Output: &quot;fbx&quot; Explanation: The hash of &quot;fbx&quot; can be computed to be hash(&quot;fbx&quot;, 31, 100) = (6 * 1 + 2 * 31 + 24 * 312) mod 100 = 23132 mod 100 = 32. The hash of &quot;bxz&quot; can be computed to be hash(&quot;bxz&quot;, 31, 100) = (2 * 1 + 24 * 31 + 26 * 312) mod 100 = 25732 mod 100 = 32. &quot;fbx&quot; is the first substring of length 3 with hashValue 32. Hence, we return &quot;fbx&quot;. Note that &quot;bxz&quot; also has a hash of 32 but it appears later than &quot;fbx&quot;. &nbsp; Constraints: 1 &lt;= k &lt;= s.length &lt;= 2 * 104 1 &lt;= power, modulo &lt;= 109 0 &lt;= hashValue &lt; modulo s consists of lowercase English letters only. The test cases are generated such that an answer always exists.
  */
        long h = 0, p = 1;
        int n = s.length();
        for (int i = n - 1; i >= n - k; --i) {
            int val = s.charAt(i) - 'a' + 1;
            h = ((h * power % modulo) + val) % modulo;
            if (i != n - k) {
                p = p * power % modulo;
            }
        }
        int j = n - k;
        for (int i = n - k - 1; i >= 0; --i) {
            int pre = s.charAt(i + k) - 'a' + 1;
            int cur = s.charAt(i) - 'a' + 1;
            h = ((h - pre * p % modulo + modulo) * power % modulo + cur) % modulo;
            if (h == hashValue) {
                j = i;
            }
        }
        return s.substring(j, j + k);
    }
}