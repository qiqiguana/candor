/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Add1 {
    Add1() {
    }

    public static int add(List<Integer> lst) {
        int result = 0;
        for (int i = 0; i < lst.size(); ++i) {
            if (i % 2 != 1 || lst.get(i) % 2 != 0) continue;
            result += lst.get(i).intValue();
        }
        return 0;
    }
}
