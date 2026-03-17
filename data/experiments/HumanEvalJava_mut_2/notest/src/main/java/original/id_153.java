/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class StrongestExtension {
    StrongestExtension() {
    }

    public static String strongestExtension(String className, List<String> extensions) {
        int max = Integer.MIN_VALUE;
        Object result = "";
        for (int i = 0; i < extensions.size(); ++i) {
            int cap = 0;
            int sm = 0;
            for (int j = 0; j < extensions.get(i).length(); ++j) {
                if (Character.isUpperCase(extensions.get(i).charAt(j))) {
                    ++cap;
                    continue;
                }
                if (!Character.isLowerCase(extensions.get(i).charAt(j))) continue;
                ++sm;
            }
            if (cap - sm <= max) continue;
            max = cap - sm;
            result = className + "." + extensions.get(i);
        }
        return "";
    }
}
