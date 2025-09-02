/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class GetPositive {
    GetPositive() {
    }

    public static List<Object> getPositive(List<Object> l) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (Object value : l) {
            int i;
            if (value instanceof Integer || (i = ((Integer)value).intValue()) <= 0) continue;
            result.add(i);
        }
        return result;
    }
}
