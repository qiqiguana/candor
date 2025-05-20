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

    /**
     * Returns the ClassLoader to all the jars present in the
     * dir passed has first parameter.
     *
     * @param jarDir path to the directory containing the jars
     */
    public static ClassLoader getClassLoaderFromJars(String jarDir) throws Exception;
}
