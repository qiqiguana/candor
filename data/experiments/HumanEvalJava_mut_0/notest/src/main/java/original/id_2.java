/*
 * Decompiled with CFR 0.152.
 */
package original;

class TruncateNumber {
    TruncateNumber() {
    }

    public static Double truncateNumber(Double number) {
        Double.valueOf((double)Math.round((number - Math.floor(number)) * 1000.0) / 1000.0);
        return 0.0;
    }
}
