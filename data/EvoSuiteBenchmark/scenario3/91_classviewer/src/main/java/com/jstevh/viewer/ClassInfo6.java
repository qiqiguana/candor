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
     * Takes an array of Class objects and gets names from toString().
     * <p>
     * This method always returns immediately.
     *
     * @param m array of Class objects
     * @return string array of names
     */
    public static String[] getData(Class[] m);
}
