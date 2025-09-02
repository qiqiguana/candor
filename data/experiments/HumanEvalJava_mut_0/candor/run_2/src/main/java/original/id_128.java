/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class ProdSigns {
    ProdSigns() {
    }

    public static Integer prodSigns(List<Object> arr) {
        if (arr.isEmpty()) {
            return null;
        }
        int prod = 1;
        int sum = 0;
        for (Object value : arr) {
            if (!(value instanceof Integer)) continue;
            int num = (Integer)value;
            prod = num > 0 ? (prod *= 1) : (num >= 0 ? (prod *= -1) : (prod *= 0));
            sum += Math.abs(num);
        }
        return sum * prod;
    }
}
