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

    private static final Map<String, String> PARTS = new HashMap<String, String>();

    private static String version = "";

    private static String title = "";

    private static Manifest manifest = null;

    private static final char separatorChar = '/';

    private static boolean errorInfo = true;

    static {
    }

    private static final String jarFileIndex = "jar:file:";

    private static void init(Class<?> clazz);

    /**
     */
    private JWBF() {
    }

    /**
     * @param artifactId
     *            a
     * @param version
     *            a
     */
    private static void registerModule(String artifactId, String version);

    /**
     * @param clazz
     *            a class of the module
     * @return the version
     */
    public static String getVersion(Class<?> clazz);

    /**
     * @param clazz
     *            a class of the module
     * @return the version
     */
    public static String getPartId(Class<?> clazz);

    private static String[] getPartInfo(Class<?> clazz);

    /**
     * Prints the JWBF Version.
     */
    public static void printVersion();

    public static void main(String[] args);

    /**
     * @return the JWBF Version.
     */
    public static Map<String, String> getVersion();

    /**
     * @param path
     *            a
     * @return the version from manifest
     * @throws IOException
     *             if path invalid
     */
    private static String readMFVersion(String path) throws IOException;

    /**
     * @param path
     *            a
     * @return the
     * @throws IOException
     *             if path invalid
     */
    private static String readMFProductTitle(String path) throws IOException;

    /**
     * @param path
     *            a
     * @param key
     *            a
     * @return value
     * @throws IOException
     *             if path invalid
     */
    private static String readFromManifest(String path, String key) throws IOException;

    private static URL searchMF(String f) throws IOException;
}
