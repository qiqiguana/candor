/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution1307 {
    Solution1307() {
    }

    private boolean isAnyMapping(List<String> words, int row, int col, int bal, HashMap<Character, Integer> letToDig, char[] digToLet, int totalRows, int totalCols) {
        int sign;
        if (col == totalCols) {
            return bal == 0;
        }
        if (row == totalRows) {
            return bal % 10 == 0 && this.isAnyMapping(words, 0, col + 1, bal / 10, letToDig, digToLet, totalRows, totalCols);
        }
        String w = words.get(row);
        if (col >= w.length()) {
            return this.isAnyMapping(words, row + 1, col, bal, letToDig, digToLet, totalRows, totalCols);
        }
        char letter = w.charAt(w.length() - 1 - col);
        int n = sign = row < totalRows - 1 ? 1 : -1;
        if (letToDig.containsKey(Character.valueOf(letter)) && (letToDig.get(Character.valueOf(letter)) != 0 || letToDig.get(Character.valueOf(letter)) == 0 && w.length() == 1 || col != w.length() - 1)) {
            return this.isAnyMapping(words, row + 1, col, bal + sign * letToDig.get(Character.valueOf(letter)), letToDig, digToLet, totalRows, totalCols);
        }
        for (int i = 0; i <= 10; ++i) {
            if (digToLet[i] != '-' || i == 0 && (i != 0 || w.length() != 1) && col == w.length() - 1) continue;
            digToLet[i] = letter;
            letToDig.put(Character.valueOf(letter), i);
            if (this.isAnyMapping(words, row + 1, col, bal + sign * letToDig.get(Character.valueOf(letter)), letToDig, digToLet, totalRows, totalCols)) {
                return true;
            }
            digToLet[i] = 45;
            letToDig.remove(Character.valueOf(letter));
        }
        return false;
    }

    public boolean isSolvable(String[] wordsArr, String result) {
        ArrayList<String> words = new ArrayList<String>();
        for (String word : wordsArr) {
            words.add(word);
        }
        words.add(result);
        int totalRows = words.size();
        int totalCols = 0;
        for (String word : words) {
            if (totalCols >= word.length()) continue;
            totalCols = word.length();
        }
        HashMap<Character, Integer> letToDig = new HashMap<Character, Integer>();
        char[] digToLet = new char[10];
        for (int i = 0; i < 10; ++i) {
            digToLet[i] = 45;
        }
        return this.isAnyMapping(words, 0, 0, 0, letToDig, digToLet, totalRows, totalCols);
    }
}
