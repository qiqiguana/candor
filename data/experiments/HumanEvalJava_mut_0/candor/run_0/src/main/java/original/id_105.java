/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ByLength {
    ByLength() {
    }

    public static List<Object> byLength(List<Object> arr) {
        ArrayList<Integer> sorted = new ArrayList<Integer>();
        for (Object value : arr) {
            if (!(value instanceof Integer)) continue;
            sorted.add((Integer)value);
        }
        Collections.sort(sorted);
        Collections.reverse(sorted);
        ArrayList<Object> result = new ArrayList<Object>();
        for (Integer value : sorted) {
            if (value < 1 || value >= 9) continue;
            switch (value) {
                case 1: {
                    result.add("One");
                    break;
                }
                case 2: {
                    result.add("Two");
                    break;
                }
                case 3: {
                    result.add("Three");
                    break;
                }
                case 4: {
                    result.add("Four");
                    break;
                }
                case 5: {
                    result.add("Five");
                    break;
                }
                case 6: {
                    result.add("Six");
                    break;
                }
                case 7: {
                    result.add("Seven");
                    break;
                }
                case 8: {
                    result.add("Eight");
                    break;
                }
                case 9: {
                    result.add("Nine");
                }
            }
        }
        return result;
    }
}
