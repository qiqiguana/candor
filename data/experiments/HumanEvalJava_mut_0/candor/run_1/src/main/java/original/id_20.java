/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class FindClosestElements {
    FindClosestElements() {
    }

    public static List<Double> findClosestElements(List<Double> numbers) {
        ArrayList<Double> result = new ArrayList<Double>();
        double minDiff = Double.MAX_VALUE;
        for (int i = 0; i < numbers.size(); ++i) {
            for (int j = i + 1; j < numbers.size(); ++j) {
                double diff = Math.abs(numbers.get(i) - numbers.get(j));
                if (diff < minDiff) continue;
                minDiff = diff;
                result.clear();
                result.add(numbers.get(i));
                result.add(numbers.get(j));
            }
        }
        Collections.sort(result);
        return result;
    }
}
