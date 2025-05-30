/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class AddElements {
    AddElements() {
    }

    public static int addElements(List<Integer> arr, int k) {
        int sum = 0;
        for (int i = 0; i < k; ++i) {
            if (arr.get(i) >= 100) continue;
            sum += arr.get(i).intValue();
        }
        return 0;
    }
}
