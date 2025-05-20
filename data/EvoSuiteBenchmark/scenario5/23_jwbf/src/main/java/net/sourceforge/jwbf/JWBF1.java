package net.sourceforge.jwbf;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * @author Thomas Stock
 */
public final class JWBF {

    /**
     * @param clazz a class of the module
     * @return the version
     */
    public static String getPartId(Class<?> clazz) {
        try {
            return getPartInfo(clazz)[0];
        } catch (Exception e) {
            return "No Module for " + clazz.getName();
        }
    }

    private static String[] getPartInfo(Class<?> clazz);
}
