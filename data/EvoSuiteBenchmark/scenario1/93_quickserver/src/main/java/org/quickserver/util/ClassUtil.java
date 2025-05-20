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

    public static ClassLoader getClassLoaderFromJars(String jarDir) throws Exception {
        logger.fine("Getting ClassLoader for jars in " + jarDir);
        File file = new File(jarDir);
        ArrayList list = new ArrayList();
        File[] jars = file.listFiles(new JarFileList());
        for (int j = 0; j < jars.length; j++) {
            list.add(jars[j].toURL());
        }
        Object[] array = list.toArray();
        URL[] jarurl = new URL[array.length];
        for (int i = 0; i < array.length; i++) {
            jarurl[i] = (URL) array[i];
        }
        URLClassLoader classLoader = URLClassLoader.newInstance(jarurl);
        return classLoader;
    }
}
