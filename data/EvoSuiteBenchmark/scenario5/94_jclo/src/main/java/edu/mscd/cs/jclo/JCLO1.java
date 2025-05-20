package edu.mscd.cs.jclo;

import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;

public class JCLO {

    /**
     * Create and usage message for the acceptable command line variables.
     *
     * @return a String that specifies acceptable options
     */
    public String usage() {
        boolean additional = false;
        String[] a = new String[fields.length];
        int count = 0;
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String key = field.getName();
            Class type = field.getType();
            if (key.equals("additional"))
                continue;
            if (prefix != null) {
                if (!key.startsWith(prefix))
                    continue;
                else
                    key = key.replaceFirst("^" + prefix, "");
            }
            if (Modifier.isFinal(field.getModifiers()))
                continue;
            key = key.replaceFirst("^_([0-9])", "$1");
            key = key.replaceAll("__", "-");
            key = key.replaceAll("_\\$", ".");
            a[count++] = (doubleDashes ? "--" : "-") + key + getUsageType(type) + "\n";
        }
        String r = "";
        java.util.Arrays.sort(a, 0, count);
        for (int i = 0; i < a.length; i++) if (a[i] != null)
            r += a[i];
        return (r);
    }

    private String getUsageType(Class type);
}
