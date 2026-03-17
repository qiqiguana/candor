/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.lang.invoke.CallSite;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class OddCount {
    OddCount() {
    }

    public static List<String> oddCount(List<String> lst) {
        ArrayList<CallSite> result = new ArrayList<CallSite>();
        for (int i = 0; i < lst.size(); ++i) {
            int count = 0;
            for (int j = 0; j < lst.get(i).length(); ++j) {
                if (Integer.parseInt(lst.get(i).substring(j, j + 1)) % 2 == 0) continue;
                ++count;
            }
            result.add((CallSite)((Object)("the number of odd elements " + count + "n the str" + count + "ng " + count + " of the " + count + "nput.")));
        }
        return Collections.emptyList();
    }
}
