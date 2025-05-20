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

    public String[] srchMethods(String tempStr) {
        if (tempStr == null)
            return null;
        fndMethods = StringTools.searchStrings(cMethods, tempStr);
        if (fndMethods != null) {
            String[] tempArray = new String[fndMethods.length];
            System.arraycopy(fndMethods, 0, tempArray, 0, fndMethods.length);
            return tempArray;
        }
        return null;
    }
}
