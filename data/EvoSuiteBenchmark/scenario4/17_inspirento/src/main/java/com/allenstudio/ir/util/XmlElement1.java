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

    String name;

    String data;

    Hashtable<String, String> attributes;

    List<XmlElement> subElements;

    XmlElement parent;

    /**
     * Constructor
     */
    public XmlElement() {
    }

    /**
     * **
     *
     * Constructor
     *
     * @param String
     *            Name
     */
    public XmlElement(String name) {
    }

    /**
     * **
     *
     * Constructor
     *
     * @param String
     *            Name
     * @param Hashtable
     *            Attributes
     */
    public XmlElement(String name, Hashtable<String, String> attributes) {
    }

    /**
     * **
     *
     * Constructor
     *
     * @param Name
     *            String
     * @param Data
     *            String
     */
    public XmlElement(String name, String data) {
    }

    /**
     * Add attribute to this xml element.
     *
     * @param name
     *            name of key
     * @param value
     *            new attribute value
     * @return old attribute value
     */
    public Object addAttribute(String name, String value);

    /**
     * **
     *
     * @return String
     * @param String
     *            Name
     */
    public String getAttribute(String name);

    public String getAttribute(String name, String defaultValue);

    /**
     * **
     *
     * @return String
     * @param String
     *            Name
     */
    public Hashtable<String, String> getAttributes();

    /**
     * **
     *
     * @param Attrs
     *            Hashtable to use as the attributes
     */
    public void setAttributes(Hashtable<String, String> attrs);

    /**
     * **
     *
     * @return Enumeration
     */
    public Enumeration getAttributeNames();

    /**
     * **
     *
     * @return boolean
     * @param XmlElement
     *            E
     */
    public boolean addElement(XmlElement e);

    public XmlElement removeElement(XmlElement e);

    public XmlElement removeElement(int index);

    public void removeAllElements();

    /**
     * convienience method for the TreeView
     *
     * this method is modeled after the DefaultMutableTreeNode-class
     *
     * DefaultMutableTreeNode wraps XmlElement for this purpose
     */
    public void removeFromParent();

    public void append(XmlElement e);

    /**
     * convienience method for the TreeView
     *
     * @param e
     * @param index
     */
    public void insertElement(XmlElement e, int index);

    /**
     * **
     *
     * @return Vector
     */
    public List getElements();

    public int count();

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

    public XmlElement getElement(int index);

    /**
     * Adds a sub element to this one. The path
     * is separated with dots(".").
     *
     * @return the <code>XmlElement</code> added
     * @param path The subpath of the sub element to add
     */
    public XmlElement addSubElement(String path);

    /**
     * Adds a sub element to this one
     *
     * @return XmlElement
     * @param element
     *            The XmlElement to add
     */
    public XmlElement addSubElement(XmlElement e);

    /**
     * Adds a sub element to this one
     *
     * @return XmlElement
     * @param Name
     *            The name of the sub element to add
     * @param Data
     *            String Data for this element
     */
    public XmlElement addSubElement(String name, String data);

    /**
     * Sets the parent element
     *
     * @param Parent
     *            The XmlElement that contains this one
     */
    public void setParent(XmlElement parent);

    /**
     * Gives the XmlElement containing the current element
     *
     * @return XmlElement
     */
    public XmlElement getParent();

    /**
     * Sets the data for this element
     *
     * @param D
     *            The String representation of the data
     */
    public void setData(String d);

    /**
     * Returns the data associated with the current Xml element
     *
     * @return String
     */
    public String getData();

    /**
     * Returns the name of the current Xml element
     *
     * @return String
     */
    public String getName();

    /*
     * private void _writeSpace(PrintWriter out, int numSpaces) throws
     * IOException {
     * 
     * for (int i = 0; i < numSpaces; i++) out.print(" "); }
     * 
     * public static void printNode(XmlElement Node, String indent) { String
     * Data = Node.getData(); if (Data == null || Data.equals("")) {
     * System.out.println(indent + Node.getName()); } else {
     * System.out.println(indent + Node.getName() + " = '" + Data + "'"); }
     * Vector Subs = Node.getElements(); int i, j; for (i = 0; i < Subs.size();
     * i++) { printNode((XmlElement) Subs.get(i), indent + " "); } }
     */
    public static void printNode(XmlElement node, String indent);

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone();

    /**
     * Sets the name.
     *
     * @param name
     *            The name to set
     */
    public void setName(String name);

    /**
     * Notify all Observers.
     *
     * @see java.util.Observable#notifyObservers()
     */
    @Override
    public void notifyObservers();

    /**
     * Returns true if the specified objects are equal. They are equal if they
     * are both null OR if the <code>equals()</code> method return true. (
     * <code>obj1.equals(obj2)</code>).
     *
     * @param obj1
     *            first object to compare with.
     * @param obj2
     *            second object to compare with.
     * @return true if they represent the same object; false if one of them is
     *         null or the <code>equals()</code> method returns false.
     */
    private boolean equals(Object obj1, Object obj2);

    /**
     *  {@inheritDoc}
     * Recursive comparison.
     */
    @Override
    public boolean equals(Object obj);

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode();
}
