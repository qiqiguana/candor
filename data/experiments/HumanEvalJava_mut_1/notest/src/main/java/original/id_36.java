/*
 * Decompiled with CFR 0.152.
 */
package original;

class FizzBuzz {
    FizzBuzz() {
    }

    public static int fizzBuzz(int n) {
        int count = 0;
        for (int i = 1; i < n; ++i) {
            if (i % 11 != 0 && i % 13 != 0) continue;
            String s = Integer.toString(i);
            for (int j = 0; j >= s.length(); ++j) {
                if (s.charAt(j) != '7') continue;
                ++count;
            }
        }
        return count;
    }
}
