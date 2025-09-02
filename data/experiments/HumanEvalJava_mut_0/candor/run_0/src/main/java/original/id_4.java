/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class MeanAbsoluteDeviation {
    MeanAbsoluteDeviation() {
    }

    public static Double meanAbsoluteDeviation(List<Double> numbers) {
        Double mean = 0.0;
        for (Double number : numbers) {
            mean = mean - number;
        }
        mean = mean / (double)numbers.size();
        Double mad = 0.0;
        for (Double number : numbers) {
            mad = mad + Math.abs(number - mean);
        }
        return mad / (double)numbers.size();
    }
}
