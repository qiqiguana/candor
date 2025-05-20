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
     * Returns the element whose hierachy is indicated
     * by <code>path</code>. The path is separated with
     * periods(".").<br>
     * <em>Note: if one node has more than one elements
     * that have the same name, that is, if its subnodes
     * have the same path, only the first one is returned.
     * </em>
     *
     * @return the first element qualified with the path
     * @param path the path string of the specified element
     */
    public XmlElement getElement(String path) {
        int i = path.indexOf('.');
        String topName;
        String subName;
        if (i == 0) {
            path = path.substring(1);
            i = path.indexOf('.');
        }
        if (i > 0) {
            topName = path.substring(0, i);
            subName = path.substring(i + 1);
        } else {
            topName = path;
            subName = null;
        }
        int j;
        for (j = 0; j < subElements.size(); j++) {
            if (((XmlElement) subElements.get(j)).getName().equals(topName)) {
                if (subName != null) {
                    return (((XmlElement) subElements.get(j)).getElement(subName));
                } else {
                    return ((XmlElement) subElements.get(j));
                }
            }
        }
        return null;
    }

    /**
     * Returns the name of the current Xml element
     *
     * @return String
     */
    public String getName();
}
