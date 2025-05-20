package com.jstevh.viewer;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jstevh.tools.*;

/**
 * String holding and manipulation class
 *
 * @author James Harris
 * @version 5.0 beta
 */
public class StringList {

    public void add(String obj);

    public boolean isEmpty();

    public String[] toArray();
}

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
     * Returns an array of strings that contain the public methods for
     * the class excluding inherited methods. The data is pulled from
     * the c private Class object.
     * <p>
     * This method always returns immediately.
     *
     * @param param (not currently implemented) selects whether inherited
     *               objects are returned
     * @return string array of public methods
     */
    public String[] printMethods(int param) {
        if (cMethods == null)
            return null;
        if (debug)
            System.out.println("****************************************");
        String[] data = cMethods;
        StringList tempList = new StringList();
        for (int i = 0; i < cMethods.length; i++) {
            if (cMethods[i].indexOf(getClassName() + '.') != -1)
                tempList.add(cMethods[i]);
        }
        if (!tempList.isEmpty())
            data = tempList.toArray();
        else
            data = null;
        if (debug && data == null)
            System.out.println("No public methods.");
        else {
            printArray(data);
        }
        return data;
    }

    public String getClassName();

    /**
     * Takes any given array and prints out with System.out.println
     *
     * @param    array   an array of objects
     */
    private void printArray(Object[] array);
}
