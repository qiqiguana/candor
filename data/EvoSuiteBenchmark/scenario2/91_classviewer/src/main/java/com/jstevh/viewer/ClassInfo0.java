package com.jstevh.viewer;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jstevh.tools.*;

/**
 * Obtains the interfaces as well as the public constructors, methods
 * and fields from a Class object.
 * <p>
 * This class is for obtaining data and handling.
 *
 * @author James Harris
 * @version 2.0a
 */
public class ClassInfo {

    /**
     * Returns an array of strings that contain the public fields for
     * the class. The data is pulled from the c private Class object.
     * <p>
     * This method always returns immediately.
     *
     * @return string array of public fields
     */
    public String[] printFields() {
        Field[] f = c.getFields();
        length = f.length;
        String[] StringData = null;
        if (length > 0) {
            StringData = new String[length];
            String tempStr;
            Object obj = new Object();
            if (debug)
                System.out.println("****************************************");
            for (count = 0; count < length; count++) {
                try {
                    tempStr = f[count] + "=" + f[count].get(obj);
                    StringData[count] = tempStr;
                    if (debug)
                        System.out.println(tempStr);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    tempStr = f[count] + "= ILLEGAL ACCESS EXCEPTION, unable to load";
                    StringData[count] = tempStr;
                } catch (IllegalArgumentException j) {
                    j.printStackTrace();
                    tempStr = f[count] + "= ILLEGAL ARGUMENT EXCEPTION, unable to load";
                    StringData[count] = tempStr;
                }
            }
        } else if (debug)
            System.out.println("No public fields.");
        return StringData;
    }
}
