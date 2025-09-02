package original;

import java.util.Arrays;
class Solution2268 {
    public int minimumKeypresses(String s) {
  /**
  You have a keypad with 9 buttons, numbered from 1 to 9, each mapped to lowercase English letters. You can choose which characters each button is matched to as long as: All 26 lowercase English letters are mapped to. Each character is mapped to by exactly 1 button. Each button maps to at most 3 characters. To type the first character matched to a button, you press the button once. To type the second character, you press the button twice, and so on. Given a string s, return the minimum number of keypresses needed to type s using your keypad. Note that the characters mapped to by each button, and the order they are mapped in cannot be changed. &nbsp; Example 1: Input: s = &quot;apple&quot; Output: 5 Explanation: One optimal way to setup your keypad is shown above. Type &#39;a&#39; by pressing button 1 once. Type &#39;p&#39; by pressing button 6 once. Type &#39;p&#39; by pressing button 6 once. Type &#39;l&#39; by pressing button 5 once. Type &#39;e&#39; by pressing button 3 once. A total of 5 button presses are needed, so return 5. Example 2: Input: s = &quot;abcdefghijkl&quot; Output: 15 Explanation: One optimal way to setup your keypad is shown above. The letters &#39;a&#39; to &#39;i&#39; can each be typed by pressing a button once. Type &#39;j&#39; by pressing button 1 twice. Type &#39;k&#39; by pressing button 2 twice. Type &#39;l&#39; by pressing button 3 twice. A total of 15 button presses are needed, so return 15. &nbsp; Constraints: 1 &lt;= s.length &lt;= 105 s consists of lowercase English letters.
  */
        int[] cnt = new int[26];
        for (int i = 0; i < s.length(); ++i) {
            ++cnt[s.charAt(i) - 'a'];
        }
        Arrays.sort(cnt);
        int ans = 0, k = 1;
        for (int i = 1; i <= 26; ++i) {
            ans += k * cnt[26 - i];
            if (i % 9 == 0) {
                ++k;
            }
        }
        return ans;
    }
}