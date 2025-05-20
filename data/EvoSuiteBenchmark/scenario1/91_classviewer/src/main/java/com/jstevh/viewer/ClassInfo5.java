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

    public static String[] getData(Member[] m) {
        length = m.length;
        if (length == 0)
            return null;
        String[] StringData = new String[length];
        String tempStr;
        for (count = 0; count < m.length; count++) {
            tempStr = m[count].toString();
            StringData[count] = tempStr;
        }
        return StringData;
    }
}
