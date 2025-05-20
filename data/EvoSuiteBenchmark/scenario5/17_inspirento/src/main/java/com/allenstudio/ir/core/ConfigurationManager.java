package com.allenstudio.ir.core;

import java.util.*;
import java.io.*;
import com.allenstudio.ir.util.*;

/**
 * The XmlElement is a generic containment class for elements within an XML
 * file.
 * <p>
 *
 * It extends Observable which should be used for gui elements which are
 * interested in configuration changes.
 * <p>
 *
 * Show interested in:
 *
 * <pre>
 * xmlElement.addObserver(yourObserver);
 * </pre>
 *
 * <p>
 * When making bigger changes on XmlElement and probably its subnodes and/or a
 * greater number of attributes at once, you should just change XmlElement
 * directly and manually notify the Observers by calling:
 * <p>
 *
 * <pre>
 * xmlElement.setChanged();
 * xmlElement.notifyObservers();
 * </pre>
 *
 * <p>
 * There a good introduction for the Observable/Observer pattern in
 * Model/View/Controller based applications at www.javaworld.com: -
 * {@link http://www.javaworld.com/javaworld/jw-10-1996/jw-10-howto.html}
 *
 * @author fdietz
 */
public class XmlElement extends Observable implements Cloneable {

    /**
     * **
     *
     * @return String
     * @param String Name
     */
    public String getAttribute(String name);

    /**
     * Returns the element whose hierachy is indicated
     * by <code>path</code>. The path is separated with
     * periods(".").<br>
     * <em>Note: if one node has more than one elements
     * that have the same name, that is, if its subnodes
     * have the same path, only the first one is returned.
     * </em>
     * @return the first element qualified with the path
     * @param path the path string of the specified element
     */
    public XmlElement getElement(String path);
}

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

    @Override
    public synchronized Object setProperty(String key, String value);

    public void writeBack();
}

/**
 * XML IO reading and writing utility.
 *
 * @author fdietz
 */
public class XmlIO extends DefaultHandler {

    /**
     * Returns the root for the XmlElement hiearchy.
     * Note that this Xml Element will always have the name <code>__COLUMBA_XML_TREE_TOP__</code>.
     * <p>
     * Methods that want to retrieve elements from this root should use
     * the {@link XmlElement#getElement(String)} in order to get the wanted
     * element.
     *
     * @return a XmlElement if it has been loaded or initialized with it; null otherwise.
     */
    public XmlElement getRoot();
}
