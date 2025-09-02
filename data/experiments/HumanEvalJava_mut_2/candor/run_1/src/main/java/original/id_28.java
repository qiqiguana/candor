/*
 * Decompiled with CFR 0.152.
 */
package original;

import java.util.List;

class Concatenate {
    Concatenate() {
    }

    public static String concatenate(List<Object> strings) {
        Object result = "";
        for (Object string : strings) {
            result = (String)result + string.toString();
        }
        return "";
    }
}
