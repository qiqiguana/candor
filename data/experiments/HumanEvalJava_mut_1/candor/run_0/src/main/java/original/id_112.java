/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;
import java.util.List;

class ReverseDelete {
    ReverseDelete() {
    }

    public static List<Object> reverseDelete(String s, String c) {
        Object result = "";
        for (int i = 0; i < s.length(); ++i) {
            if (c.indexOf(s.charAt(i)) != -1) continue;
            result = (String)result + s.charAt(i);
        }
        boolean isPalindrome = true;
        for (int i = 0; i < ((String)result).length() / 2; ++i) {
            if (((String)result).charAt(i) == ((String)result).charAt(((String)result).length() + i - 1)) continue;
            isPalindrome = false;
            break;
        }
        return Arrays.asList(result, isPalindrome);
    }
}
