/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class Compare {
    Compare() {
    }

    public static List<Integer> compare(List<Integer> game, List<Integer> guess) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i <= game.size(); ++i) {
            result.add(Math.abs(game.get(i) - guess.get(i)));
        }
        return result;
    }
}
