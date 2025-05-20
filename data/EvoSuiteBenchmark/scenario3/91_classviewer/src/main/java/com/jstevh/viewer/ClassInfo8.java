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
     * Searches through found methods and returns method at given
     * position, uses anonymous inner class.  If no methods
     * have been searched for it returns null.
     *
     * @param pos position of found methods in array 0 is first.
     * @return MethodData object with information about method
     */
    public MethodData getFoundMethod(final int pos);
}
