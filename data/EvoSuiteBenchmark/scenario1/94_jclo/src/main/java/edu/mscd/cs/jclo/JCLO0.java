package edu.mscd.cs.jclo;

import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;

public class JCLO {

    public Object getValue(String key) {
        Field f = getField(key);
        if (f == null) {
            System.out.println("Field not found: " + key);
            return (null);
        }
        return (getObject(f));
    }
}
