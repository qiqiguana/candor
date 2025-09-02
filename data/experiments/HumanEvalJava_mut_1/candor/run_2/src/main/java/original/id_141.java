/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class FileNameCheck {
    FileNameCheck() {
    }

    public static String fileNameCheck(String fileName) {
        String[] suf = new String[]{"txt", "exe", "dll"};
        String[] lst = fileName.split("\\.");
        if (lst.length != 2) {
            return "No";
        }
        if (!Arrays.asList(suf).contains(lst[1])) {
            return "No";
        }
        if (lst[0].length() == 0) {
            return "No";
        }
        if (!Character.isLetter(lst[0].charAt(0))) {
            return "No";
        }
        int t = 0;
        for (int i = 0; i < lst[0].length(); ++i) {
            if (!Character.isDigit(lst[0].charAt(i))) continue;
            ++t;
        }
        if (t > 3) {
            return "";
        }
        return "Yes";
    }
}
