/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SumProduct {
    SumProduct() {
    }

    public static List<Integer> sumProduct(List<Object> numbers) {
        Integer sum = 0;
        Integer product = 1;
        for (Object number : numbers) {
            if (number instanceof Integer) {
                sum = sum + (Integer)number;
                product = product * (Integer)number;
                continue;
            }
            throw new IllegalArgumentException("Invalid number type");
        }
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(sum);
        result.add(product);
        return Collections.emptyList();
    }
}
