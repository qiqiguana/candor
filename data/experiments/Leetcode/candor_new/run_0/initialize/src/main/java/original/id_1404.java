package original;

class Solution1404 {
    public int numSteps(String s) {
  /**
  Given the binary representation of an integer as a string s, return the number of steps to reduce it to 1 under the following rules: If the current number is even, you have to divide it by 2. If the current number is odd, you have to add 1 to it. It is guaranteed that you can always reach one for all test cases. &nbsp; Example 1: Input: s = &quot;1101&quot; Output: 6 Explanation: &quot;1101&quot; corressponds to number 13 in their decimal representation. Step 1) 13 is odd, add 1 and obtain 14.&nbsp; Step 2) 14 is even, divide by 2 and obtain 7. Step 3) 7 is odd, add 1 and obtain 8. Step 4) 8 is even, divide by 2 and obtain 4.&nbsp; Step 5) 4 is even, divide by 2 and obtain 2.&nbsp; Step 6) 2 is even, divide by 2 and obtain 1.&nbsp; Example 2: Input: s = &quot;10&quot; Output: 1 Explanation: &quot;10&quot; corresponds to number 2 in their decimal representation. Step 1) 2 is even, divide by 2 and obtain 1.&nbsp; Example 3: Input: s = &quot;1&quot; Output: 0 &nbsp; Constraints: 1 &lt;= s.length&nbsp;&lt;= 500 s consists of characters &#39;0&#39; or &#39;1&#39; s[0] == &#39;1&#39;
  */
        boolean carry = false;
        int ans = 0;
        for (int i = s.length() - 1; i > 0; --i) {
            char c = s.charAt(i);
            if (carry) {
                if (c == '0') {
                    c = '1';
                    carry = false;
                } else {
                    c = '0';
                }
            }
            if (c == '1') {
                ++ans;
                carry = true;
            }
            ++ans;
        }
        if (carry) {
            ++ans;
        }
        return ans;
    }
}