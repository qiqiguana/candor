/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Collections;
import java.util.List;

class TotalMatch {
    TotalMatch() {
    }

    public static List<Object> totalMatch(List<Object> lst1, List<Object> lst2) {
        int sum1 = 0;
        int sum2 = 0;
        for (Object value : lst1) {
            if (!(value instanceof String)) continue;
            sum1 += ((String)value).length();
        }
        for (Object value : lst2) {
            if (!(value instanceof String)) continue;
            sum2 += ((String)value).length();
        }
        if (sum1 <= sum2) {
            return lst1;
        }
        return Collections.emptyList();
    }
}
