package com.allenstudio.ir.util;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Vector;

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
     * Adds a sub element to this one. The path
     * is separated with dots(".").
     *
     * @return the <code>XmlElement</code> added
     * @param path The subpath of the sub element to add
     */
    public XmlElement addSubElement(String path) {
        XmlElement parent = this;
        XmlElement child;
        String name;
        while (path.indexOf('.') != -1) {
            name = path.substring(0, path.indexOf('.'));
            path = path.substring(path.indexOf('.') + 1);
            // if path startsWith "/" -> skip
            if (name.length() == 0)
                continue;
            if (parent.getElement(name) != null) {
                parent = parent.getElement(name);
            } else {
                child = new XmlElement(name);
                parent.addElement(child);
                parent = child;
            }
        }
        child = new XmlElement(path);
        parent.addElement(child);
        return child;
    }

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

    /**
     * **
     *
     * @return boolean
     * @param XmlElement
     *            E
     */
    public boolean addElement(XmlElement e);
}
