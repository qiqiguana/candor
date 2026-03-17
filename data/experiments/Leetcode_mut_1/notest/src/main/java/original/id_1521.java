/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.HashSet;
import java.util.Iterator;

class Solution1521 {
    Solution1521() {
    }

    public int closestToTarget(int[] arr, int target) {
        int ans = Math.abs(arr[0] - target);
        HashSet<Integer> pre = new HashSet<Integer>();
        pre.add(arr[0]);
        for (int x : arr) {
            int y;
            HashSet<Integer> cur = new HashSet<Integer>();
            Iterator iterator = pre.iterator();
            while (iterator.hasNext()) {
                y = (Integer)iterator.next();
                cur.add(x & y);
            }
            cur.add(x);
            iterator = cur.iterator();
            while (iterator.hasNext()) {
                y = (Integer)iterator.next();
                ans = Math.min(ans, Math.abs(y - target));
            }
            pre = cur;
        }
        return 0;
    }
}
