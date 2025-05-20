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
