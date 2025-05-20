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
     * the class excluding inherited methods. The data is pulled from
     * the c private Class object.
     * <p>
     * This method always returns immediately.
     *
     * @param param (not currently implemented) selects whether inherited
     *               objects are returned
     * @return string array of public methods
     */
    public String[] printMethods(int param);
}
