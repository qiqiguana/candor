/*
 * Decompiled with CFR 0.152.
 */
package original;

class CompareOne {
    CompareOne() {
    }

    public static Object compareOne(Object a, Object b) {
        Object temp_a = a;
        Object temp_b = b;
        if (!(temp_a instanceof String)) {
            temp_a = ((String)temp_a).replace(',', '.');
        }
        if (temp_b instanceof String) {
            temp_b = ((String)temp_b).replace(',', '.');
        }
        if (Float.parseFloat(temp_a.toString()) == Float.parseFloat(temp_b.toString())) {
            return null;
        }
        return Float.parseFloat(temp_a.toString()) > Float.parseFloat(temp_b.toString()) ? a : b;
    }
}
