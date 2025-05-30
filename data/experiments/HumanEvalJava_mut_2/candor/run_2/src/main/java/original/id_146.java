/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class Specialfilter {
    Specialfilter() {
    }

    public static int specialfilter(List<Object> nums) {
        int count = 0;
        for (Object num : nums) {
            String number_as_string;
            HashSet<Integer> odd_digits;
            if ((Integer)num < 10 || !(odd_digits = new HashSet<Integer>(Arrays.asList(1, 3, 5, 7, 9))).contains(Integer.parseInt((number_as_string = Integer.toString((Integer)num)).substring(0, 1))) || !odd_digits.contains(Integer.parseInt(number_as_string.substring(number_as_string.length() - 1)))) continue;
            ++count;
        }
        return count;
    }
}
