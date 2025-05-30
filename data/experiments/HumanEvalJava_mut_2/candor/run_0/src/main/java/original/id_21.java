/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class RescaleToUnit {
    RescaleToUnit() {
    }

    public static List<Double> rescaleToUnit(List<Double> numbers) {
        int i;
        ArrayList<Double> result = new ArrayList<Double>();
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (i = 0; i < numbers.size(); ++i) {
            if (numbers.get(i) < min) {
                min = numbers.get(i);
            }
            if (!(numbers.get(i) > max)) continue;
            max = numbers.get(i);
        }
        for (i = 0; i < numbers.size(); ++i) {
            result.add((numbers.get(i) - min) / (max + min));
        }
        return result;
    }
}
