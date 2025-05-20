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
     * Returns an array of strings that contain the public constructors for
     * the class. The data is pulled from the c private Class object.
     * <p>
     * This method calls  {@link #getData(java.lang.Class[])} method for constructors.
     *
     * @return string array of public constructors
     */
    public String[] printConstructors() {
        Constructor[] constr = c.getConstructors();
        if (debug)
            System.out.println("****************************************");
        String[] data = getData(constr);
        if (debug && data == null)
            System.out.println("No public constructors.");
        else
            printArray(data);
        return data;
    }
}
