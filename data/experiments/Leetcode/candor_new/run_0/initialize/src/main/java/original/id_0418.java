package original;

class Solution0418 {
    public int wordsTyping(String[] sentence, int rows, int cols) {
  /**
  Given a&nbsp;rows x cols screen and a sentence represented as a list of strings, return the number of&nbsp;times the given sentence can be fitted on the screen. The order of words in the sentence must remain unchanged, and a word cannot be split into two lines. A single space must separate two consecutive words in a line. &nbsp; Example 1: Input: sentence = [&quot;hello&quot;,&quot;world&quot;], rows = 2, cols = 8 Output: 1 Explanation: hello--- world--- The character &#39;-&#39; signifies an empty space on the screen. Example 2: Input: sentence = [&quot;a&quot;, &quot;bcd&quot;, &quot;e&quot;], rows = 3, cols = 6 Output: 2 Explanation: a-bcd- e-a--- bcd-e- The character &#39;-&#39; signifies an empty space on the screen. Example 3: Input: sentence = [&quot;i&quot;,&quot;had&quot;,&quot;apple&quot;,&quot;pie&quot;], rows = 4, cols = 5 Output: 1 Explanation: i-had apple pie-i had-- The character &#39;-&#39; signifies an empty space on the screen. &nbsp; Constraints: 1 &lt;= sentence.length &lt;= 100 1 &lt;= sentence[i].length &lt;= 10 sentence[i] consists of lowercase English letters. 1 &lt;= rows, cols &lt;= 2 * 104
  */
        String s = String.join(" ", sentence) + " ";
        int m = s.length();
        int cur = 0;
        while (rows-- > 0) {
            cur += cols;
            if (s.charAt(cur % m) == ' ') {
                ++cur;
            } else {
                while (cur > 0 && s.charAt((cur - 1) % m) != ' ') {
                    --cur;
                }
            }
        }
        return cur / m;
    }
}