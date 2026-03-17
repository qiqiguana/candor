/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class Solution0582 {
    private Map<Integer, List<Integer>> g = new HashMap<Integer, List<Integer>>();
    private List<Integer> ans = new ArrayList<Integer>();

    Solution0582() {
    }

    public List<Integer> killProcess(List<Integer> pid, List<Integer> ppid, int kill) {
        int n = pid.size();
        for (int i = 0; i < n; ++i) {
            this.g.computeIfAbsent(ppid.get(i), k -> new ArrayList()).add(pid.get(i));
        }
        return this.ans;
    }

    private void dfs(int i) {
        this.ans.add(i);
        Iterator iterator = this.g.getOrDefault(i, Collections.emptyList()).iterator();
        while (iterator.hasNext()) {
            int j = (Integer)iterator.next();
            this.dfs(j);
        }
    }
}
