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

    /**
     * Uses XML parser to get the specified property.
     * If there is no such a key, the method returns
     * <code>null</code>.
     *
     * @param key the key of the property
     * @return the property value
     */
    @Override
    public synchronized String getProperty(String key) {
        String value = xmlIO.getRoot().getElement(Constants.PROJECT_NAME + "." + getPath(key)[0]).getAttribute(getPath(key)[1]);
        if (value == null) {
            //Perhaps some element is lost in the file
            value = defaults.getProperty(key);
            //null value has no side effect
            setProperty(key, value);
            new Thread() {

                @Override
                public void run() {
                    writeBack();
                }
            }.start();
        }
        return value;
    }
}
