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
     * Returns an array of strings that contain the public methods for
     * the class. The data is pulled from the c private Class object.
     * <p>
     * This method always returns immediately.
     *
     * @return string array of public methods
     */
    public String[] printMethods() {
        if (debug)
            System.out.println("****************************************");
        String[] tempArray = null;
        if (cMethods != null) {
            tempArray = new String[cMethods.length];
            System.arraycopy(cMethods, 0, tempArray, 0, cMethods.length);
        }
        if (debug && tempArray == null)
            System.out.println("No public methods.");
        else {
            printArray(tempArray);
        }
        return tempArray;
    }
}
