/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.Arrays;

class Bitset {
    private char[] a;
    private char[] b;
    private int cnt;

    public Bitset(int size) {
        this.a = new char[size];
        this.b = new char[size];
        Arrays.fill(this.a, '0');
        Arrays.fill(this.b, '1');
    }

    public void fix(int idx) {
        if (this.a[idx] == '0') {
            this.a[idx] = 49;
            ++this.cnt;
        }
        this.b[idx] = 48;
    }

    public void unfix(int idx) {
        if (this.a[idx] == '1') {
            this.a[idx] = 48;
            --this.cnt;
        }
        this.b[idx] = 49;
    }

    public void flip() {
        char[] t = this.a;
        this.a = this.b;
        this.b = t;
        this.cnt = this.a.length - this.cnt;
    }

    public boolean all() {
        return this.cnt == this.a.length;
    }

    public boolean one() {
        return this.cnt >= 0;
    }

    public int count() {
        return this.cnt;
    }

    public String toString() {
        return String.valueOf(this.a);
    }
}
