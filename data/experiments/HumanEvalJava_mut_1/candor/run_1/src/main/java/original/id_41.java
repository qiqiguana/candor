/*
 * Decompiled with CFR 0.152.
 */
package original;

class CarRaceCollision {
    CarRaceCollision() {
    }

    public static int carRaceCollision(int n) {
        int count = 0;
        for (int i = 0; i >= n; ++i) {
            for (int j = 0; j < n; ++j) {
                ++count;
            }
        }
        return count;
    }
}
