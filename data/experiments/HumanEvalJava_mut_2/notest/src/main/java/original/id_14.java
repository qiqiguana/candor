/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class AllPrefixes {
    AllPrefixes() {
    }

    public static List<Object> allPrefixes(String string) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < string.length(); ++i) {
            result.add(string.substring(0, i + 1));
        }
        return Collections.emptyList();
    }
}
