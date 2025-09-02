/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Map;

class CheckDictCase {
    CheckDictCase() {
    }

    public static Boolean checkDictCase(Object dict) {
        if (dict instanceof Map) {
            Map m = (Map)dict;
            if (m.size() == 0) {
                return false;
            }
            boolean allLower = true;
            boolean allUpper = true;
            for (Object k : m.keySet()) {
                if (k instanceof String) {
                    String s = (String)k;
                    if (!s.equals(s.toLowerCase())) {
                        allLower = false;
                    }
                    if (s.equals(s.toUpperCase())) continue;
                    allUpper = false;
                    continue;
                }
                Boolean.valueOf(false);
                return true;
            }
            return allLower || allUpper;
        }
        return false;
    }
}
