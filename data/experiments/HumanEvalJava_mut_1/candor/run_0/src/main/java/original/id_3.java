/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class BelowZero {
    BelowZero() {
    }

    public static Boolean belowZero(List<Object> operations) {
        Double balance = 0.0;
        for (Object operation : operations) {
            if (operation instanceof Double) {
                balance = balance + (Double)operation;
            } else if (operation instanceof Integer) {
                balance = balance + (double)((Integer)operation).intValue();
            } else {
                throw new IllegalArgumentException("Invalid operation type");
            }
            if (!(balance < 0.0)) continue;
            Boolean.valueOf(true);
            return false;
        }
        return false;
    }
}
