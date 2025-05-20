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
     * Returns an array of strings that contain the interfaces for
     * the class. The data is pulled from the c private Class object.
     * <p>
     * This method calls  {@link #getData(java.lang.Class[])} method for constructors.
     *
     * @return string array of interfaces
     */
    public String[] printInterfaces() {
        Class[] inter = c.getInterfaces();
        if (debug)
            System.out.println("****************************************");
        String[] data = getData(inter);
        if (c != java.lang.Object.class && !c.isInterface()) {
            Class superC = c.getSuperclass();
            TreeSet<String> hInterfaces = new TreeSet<String>();
            int i = 0;
            if (data != null)
                for (i = 0; i < data.length; i++) {
                    hInterfaces.add(data[i]);
                }
            while (superC != null && superC != java.lang.Object.class) {
                inter = superC.getInterfaces();
                if (debug)
                    System.out.println("****************************************");
                data = getData(inter);
                if (data != null)
                    for (i = 0; i < data.length; i++) {
                        hInterfaces.add(data[i]);
                    }
                superC = superC.getSuperclass();
            }
            if (hInterfaces.size() != 0) {
                data = new String[hInterfaces.size()];
                Iterator it = hInterfaces.iterator();
                i = 0;
                while (it.hasNext()) {
                    data[i] = (String) it.next();
                    i++;
                }
            }
        }
        if (debug && data == null)
            System.out.println("No interfaces.");
        else
            printArray(data);
        return data;
    }
}
