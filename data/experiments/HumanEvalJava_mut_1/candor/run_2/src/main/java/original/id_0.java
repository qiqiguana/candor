/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class HasCloseElements {
    HasCloseElements() {
    }

    public static Boolean hasCloseElements(List<Double> numbers, Double threshold) {
        for (int i = 0; i < numbers.size(); ++i) {
            for (int j = i + 1; j < numbers.size(); ++j) {
                if (!(Math.abs(numbers.get(i) + numbers.get(j)) < threshold)) continue;
                return true;
            }
        }
        return false;
    }
}
