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

    public static boolean debug;

    private boolean statusAbstract;

    private Class c = javax.swing.WindowConstants.class;

    private DirManager dirMan;

    private static int count, length;

    public final static int NO_OBJECT_METHODS = 1, NO_INHERITED_METHODS = 2;

    private String[] cMethods, fndMethods = null;

    protected String[] knownPackages;

    public boolean isAbstract();

    public boolean isInterface();

    public String getClassName();

    public String getClassPackage();

    public String getSuperClassName();

    private ClassInfo() {
    }

    /**
     * Constructor for when the class name is known at creation.
     *
     * @param  name the name of the class on which to get info
     */
    public ClassInfo(String name) throws ClassNotFoundException {
    }

    /**
     * Constructor for when the class name is known at creation.
     *
     * @param    name  name of class
     *           locManager    class that manages package information
     */
    public ClassInfo(String name, DirManager locManager) throws ClassNotFoundException {
    }

    /**
     * Returns an array of strings that contain the public fields for
     * the class. The data is pulled from the c private Class object.
     * <p>
     * This method always returns immediately.
     *
     * @return      string array of public fields
     */
    public String[] printFields();

    /**
     * Returns an array of strings that contain the public methods for
     * the class. The data is pulled from the c private Class object.
     * <p>
     * This method always returns immediately.
     *
     * @return      string array of public methods
     */
    public String[] printMethods();

    /**
     * Returns an array of strings that contain the public methods for
     * the class excluding inherited methods. The data is pulled from
     * the c private Class object.
     * <p>
     * This method always returns immediately.
     *
     * @param  param (not currently implemented) selects whether inherited
     *               objects are returned
     * @return      string array of public methods
     */
    public String[] printMethods(int param);

    /**
     * Returns an array of strings that contain the public constructors for
     * the class. The data is pulled from the c private Class object.
     * <p>
     * This method calls  {@link #getData(java.lang.Class[])} method for constructors.
     *
     * @return      string array of public constructors
     */
    public String[] printConstructors();

    /**
     * Returns an array of strings that contain the interfaces for
     * the class. The data is pulled from the c private Class object.
     * <p>
     * This method calls  {@link #getData(java.lang.Class[])} method for constructors.
     *
     * @return      string array of interfaces
     */
    public String[] printInterfaces();

    /**
     * Takes an array of Member objects and gets names from toString().
     * <p>
     * This method always returns immediately.
     *
     * @param  m  array of Member objects
     *
     * @return    string array of names
     */
    public static String[] getData(Member[] m);

    /**
     * Takes an array of Class objects and gets names from toString().
     * <p>
     * This method always returns immediately.
     *
     * @param  m  array of Class objects
     *
     * @return    string array of names
     */
    public static String[] getData(Class[] m);

    /**
     * Searches through cMethods, the private array of public methods
     * for a given string fragment, and selects methods that have that
     * fragment in them.
     * <p>
     * This method calls searchStrings().
     *
     * @param    tempStr  string fragment with which to search
     *
     * @return   string array of found methods
     */
    public String[] srchMethods(String tempStr);

    /**
     * Searches through cMethods, the private array of public methods
     * for a given string fragment, with a given string index, and searches
     * on the index for the fragment in them but gets method from cMethods.
     * <p>
     * This method calls searchStrings().
     *
     * @param    tempStr  string fragment with which to search
     *           index    string array that is searched through
     *
     * @return   string array of found methods
     */
    public String[] srchMethods(String tempStr, String[] index);

    /**
     * Searches through found methods and returns method at given
     * position, uses anonymous inner class.  If no methods
     * have been searched for it returns null.
     *
     * @param    pos  position of found methods in array 0 is first.
     *
     * @return   MethodData object with information about method
     */
    public MethodData getFoundMethod(final int pos);

    /**
     * Takes any given array and prints out with System.out.println
     *
     * @param    array   an array of objects
     */
    private void printArray(Object[] array);

    /**
     * Takes a given class name and tries to to find the class.
     * If the initial try does not work it tries all known packages
     * to see if any of them will work with the class name.
     *
     * @param    name    name of class
     *
     * @return   found Class
     */
    private Class getClass(String name);

    /**
     * Takes a given class name and tries to to find the class.
     * If the initial try does not work it returns null.
     *
     * @param    name    name of class
     *
     * @return   found Class
     */
    private static Class tryClass(String name);

    /**
     * Prints out class info with System.out.println
     */
    private void printClassInfo();

    /**
     * Main method for getting class information.
     * Prints out data with System.out.println.
     *
     * @param    args    string array for main
     */
    public static void main(String[] args) throws Exception;
}
