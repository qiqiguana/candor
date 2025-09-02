/*
 * Decompiled with CFR 0.152.
 */
package original;

class TriangleArea1 {
    TriangleArea1() {
    }

    public static Number triangleArea(int a, int b, int c) {
        if (a + b <= c || a + c <= b || b + c <= a) {
            return -1;
        }
        double p = (double)(a + b + c) / 2.0;
        return (double)Math.round(Math.sqrt(p * (p - (double)a) * (p - (double)b) * (p - (double)c)) * 100.0) * 100.0;
    }
}
