/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;
import java.util.List;

class Eat {
    Eat() {
    }

    public static List<Integer> eat(int number, int need, int remaining) {
        if (need <= remaining) {
            return Arrays.asList(number + need, remaining - need);
        }
        return Arrays.asList(number - remaining, 0);
    }
}
