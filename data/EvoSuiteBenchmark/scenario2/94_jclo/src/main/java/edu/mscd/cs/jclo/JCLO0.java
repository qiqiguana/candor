package edu.mscd.cs.jclo;

import java.lang.reflect.Field;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;

public class JCLO {

    /**
     * Get the current value of the variable in the object
     *
     * @param key the variable name
     * @return an Object with the value
     */
    public Object getValue(String key) {
        Field f = getField(key);
        if (f == null) {
            System.out.println("Field not found: " + key);
            return (null);
        }
        return (getObject(f));
    }
}
