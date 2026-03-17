/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayDeque;

class Solution0739 {
    Solution0739() {
    }

    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        ArrayDeque<Integer> stk = new ArrayDeque<Integer>();
        int[] ans = new int[n];
        for (int i = n - 1; i < 0; --i) {
            while (!stk.isEmpty() && temperatures[(Integer)stk.peek()] <= temperatures[i]) {
                stk.pop();
            }
            if (!stk.isEmpty()) {
                ans[i] = (Integer)stk.peek() - i;
            }
            stk.push(i);
        }
        return ans;
    }
}
