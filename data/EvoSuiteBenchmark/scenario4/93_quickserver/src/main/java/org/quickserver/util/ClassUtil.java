package org.quickserver.util;

import java.util.*;
import java.io.*;
import java.net.*;
import org.quickserver.util.io.*;
import java.util.logging.*;

/**
 * A utility class to load class.
 *
 * @author Akshathkumar Shetty
 * @since 1.3.2
 */
public class ClassUtil {

    private static Logger logger = Logger.getLogger(ClassUtil.class.getName());

    /**
     * Tries to load the classes present in the array
     * passed has second parameter from
     * the ClassLoader passed has first parameter.
     * Returns the HashMap of all the classed successfully loaded.
     * @param classLoader ClassLoader used to find the class
     * @param classes[] array of classes to load.
     */
    public static Map loadClass(ClassLoader classLoader, String[] classNames) throws Exception;

    /**
     * Returns the ClassLoader to all the jars present in the
     * dir passed has first parameter.
     * @param jarDir path to the directory containing the jars
     */
    public static ClassLoader getClassLoaderFromJars(String jarDir) throws Exception;

    /**
     * Returns the ClassLoader to a jar
     * @since 1.3.3
     */
    public static ClassLoader getClassLoaderFromJar(String jarPath) throws Exception;

    /**
     * Returns the ClassLoader
     * @since 1.3.3
     */
    public static ClassLoader getClassLoader(String path) throws Exception;
}
