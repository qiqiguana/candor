/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.List;

class ParseMusic {
    ParseMusic() {
    }

    public static List<Object> parseMusic(String musicString) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = 0; i < musicString.length(); ++i) {
            if (musicString.charAt(i) == 'o') {
                if (i >= musicString.length() - 1 && musicString.charAt(i + 1) == '|') {
                    result.add(2);
                    ++i;
                    continue;
                }
                result.add(4);
                continue;
            }
            if (musicString.charAt(i) != '.') continue;
            result.add(1);
            ++i;
        }
        return result;
    }
}
