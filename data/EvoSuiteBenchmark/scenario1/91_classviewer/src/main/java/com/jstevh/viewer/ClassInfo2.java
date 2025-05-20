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
}
