package original;

class Solution2024 {
    private char[] s;
    private int k;

    public int maxConsecutiveAnswers(String answerKey, int k) {
  /**
  A teacher is writing a test with n true/false questions, with &#39;T&#39; denoting true and &#39;F&#39; denoting false. He wants to confuse the students by maximizing the number of consecutive questions with the same answer (multiple trues or multiple falses in a row). You are given a string answerKey, where answerKey[i] is the original answer to the ith question. In addition, you are given an integer k, the maximum number of times you may perform the following operation: Change the answer key for any question to &#39;T&#39; or &#39;F&#39; (i.e., set answerKey[i] to &#39;T&#39; or &#39;F&#39;). Return the maximum number of consecutive &#39;T&#39;s or &#39;F&#39;s in the answer key after performing the operation at most k times. &nbsp; Example 1: Input: answerKey = &quot;TTFF&quot;, k = 2 Output: 4 Explanation: We can replace both the &#39;F&#39;s with &#39;T&#39;s to make answerKey = &quot;TTTT&quot;. There are four consecutive &#39;T&#39;s. Example 2: Input: answerKey = &quot;TFFT&quot;, k = 1 Output: 3 Explanation: We can replace the first &#39;T&#39; with an &#39;F&#39; to make answerKey = &quot;FFFT&quot;. Alternatively, we can replace the second &#39;T&#39; with an &#39;F&#39; to make answerKey = &quot;TFFF&quot;. In both cases, there are three consecutive &#39;F&#39;s. Example 3: Input: answerKey = &quot;TTFTTFTT&quot;, k = 1 Output: 5 Explanation: We can replace the first &#39;F&#39; to make answerKey = &quot;TTTTTFTT&quot; Alternatively, we can replace the second &#39;F&#39; to make answerKey = &quot;TTFTTTTT&quot;. In both cases, there are five consecutive &#39;T&#39;s. &nbsp; Constraints: n == answerKey.length 1 &lt;= n &lt;= 5 * 104 answerKey[i] is either &#39;T&#39; or &#39;F&#39; 1 &lt;= k &lt;= n
  */
        s = answerKey.toCharArray();
        this.k = k;
        return Math.max(f('T'), f('F'));
    }

    private int f(char c) {
        int l = 0, cnt = 0;
        for (char ch : s) {
            cnt += ch == c ? 1 : 0;
            if (cnt > k) {
                cnt -= s[l++] == c ? 1 : 0;
            }
        }
        return s.length - l;
    }
}