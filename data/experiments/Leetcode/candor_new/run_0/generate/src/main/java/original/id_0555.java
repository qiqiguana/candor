package original;

class Solution0555 {
    public String splitLoopedString(String[] strs) {
  /**
  You are given an array of strings strs. You could concatenate these strings together into a loop, where for each string, you could choose to reverse it or not. Among all the possible loops Return the lexicographically largest string after cutting the loop, which will make the looped string into a regular one. Specifically, to find the lexicographically largest string, you need to experience two phases: Concatenate all the strings into a loop, where you can reverse some strings or not and connect them in the same order as given. Cut and make one breakpoint in any place of the loop, which will make the looped string into a regular one starting from the character at the cutpoint. And your job is to find the lexicographically largest one among all the possible regular strings. &nbsp; Example 1: Input: strs = [&quot;abc&quot;,&quot;xyz&quot;] Output: &quot;zyxcba&quot; Explanation: You can get the looped string &quot;-abcxyz-&quot;, &quot;-abczyx-&quot;, &quot;-cbaxyz-&quot;, &quot;-cbazyx-&quot;, where &#39;-&#39; represents the looped status. The answer string came from the fourth looped one, where you could cut from the middle character &#39;a&#39; and get &quot;zyxcba&quot;. Example 2: Input: strs = [&quot;abc&quot;] Output: &quot;cba&quot; &nbsp; Constraints: 1 &lt;= strs.length &lt;= 1000 1 &lt;= strs[i].length &lt;= 1000 1 &lt;= sum(strs[i].length) &lt;= 1000 strs[i] consists of lowercase English letters.
  */
        int n = strs.length;
        for (int i = 0; i < n; ++i) {
            String s = strs[i];
            String t = new StringBuilder(s).reverse().toString();
            if (s.compareTo(t) < 0) {
                strs[i] = t;
            }
        }
        String ans = "";
        for (int i = 0; i < n; ++i) {
            String s = strs[i];
            StringBuilder sb = new StringBuilder();
            for (int j = i + 1; j < n; ++j) {
                sb.append(strs[j]);
            }
            for (int j = 0; j < i; ++j) {
                sb.append(strs[j]);
            }
            String t = sb.toString();
            for (int j = 0; j < s.length(); ++j) {
                String a = s.substring(j);
                String b = s.substring(0, j);
                String cur = a + t + b;
                if (ans.compareTo(cur) < 0) {
                    ans = cur;
                }
                cur = new StringBuilder(b)
                          .reverse()
                          .append(t)
                          .append(new StringBuilder(a).reverse().toString())
                          .toString();
                if (ans.compareTo(cur) < 0) {
                    ans = cur;
                }
            }
        }
        return ans;
    }
}