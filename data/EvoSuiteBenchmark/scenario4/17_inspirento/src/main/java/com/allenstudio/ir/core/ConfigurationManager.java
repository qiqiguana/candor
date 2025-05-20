package com.allenstudio.ir.core;

import java.util.*;
import java.io.*;
import com.allenstudio.ir.util.*;

/**
 * Manages the configuration for Inspirento.<br>
 * This manager uses XML format to store information.
 * The configuration file is, by default, saved in the
 * "config" directory and named "config.xml". Clearly,
 * this class should be a singleton, so we use
 * {@link #getInstance()} to get an instance and call
 * other instance methods to get the settings needed
 * by Inspirento, such as "window.size", "window.location",
 * and etc.<br>
 * The program first tries to get the configuration from
 * this <code>ConfigurationManager</code>. If it fails to
 * get any key, it uses the default settings presetted in
 * the protected <code>default</code> field.
 *
 * @author Allen Chue
 */
public class ConfigurationManager extends Properties {

    public static final String CONFIG_DIRECTORY = "config";

    public static final String CONFIG_FILE = "config.xml";

    public static final String COMMON_PREFIX = "Inspirento.";

    private static ConfigurationManager instance = null;

    private XmlIO xmlIO;

    /**
     * Private constructor for singleton use.
     */
    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance();

    public void readIn();

    public void writeBack();

    /**
     * Uses XML parser to get the specified property.
     * If there is no such a key, the method returns
     * <code>null</code>.
     * @param key the key of the property
     * @return the property value
     */
    @Override
    public synchronized String getProperty(String key);

    @Override
    public synchronized Object setProperty(String key, String value);

    /**
     * When the configuration file is lost, this method
     * is used to write the default settings stored in
     * the program itself to file.
     */
    private void writeDefaultsToFile();

    /**
     * Returns an string array of length 2.
     * The parameter <code>pathText</code> is supposed to
     * be a string separated with dots. For example,
     * "Inspirento.window.location" is a valid parameter.
     * This method puts the token after the last dot in
     * the second position of the result array, and the
     * remaining string(excluding the last dot) in the first
     * position of the result array. It is a rivate helping method.
     * <br>
     * Example: getPath("Inspirento.window.location") returns
     * the array {"Inspirento.window", "location"}.<br>
     * <em>No format checking is done in this method! <code>
     * ArrayOutOfBoundsException</code> will be thrown
     * when no dots are found in the string.</em>
     * @param pathText the path text to be processed
     * @return an array containing the result
     */
    private static String[] getPath(String pathText);

    private void initDefaultSettings();
}
