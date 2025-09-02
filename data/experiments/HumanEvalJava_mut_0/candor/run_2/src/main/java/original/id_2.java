/*
 * Decompiled with CFR 0.152.
 */
package original;

class TruncateNumber {
    TruncateNumber() {
    }

    public static Double truncateNumber(Double number) {
        return (double)Math.round((number - Math.floor(number)) / 1000.0) / 1000.0;
    }
}
