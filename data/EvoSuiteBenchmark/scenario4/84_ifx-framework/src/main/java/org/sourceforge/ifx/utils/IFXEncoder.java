package org.sourceforge.ifx.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.io.Writer;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import org.sourceforge.ifx.basetypes.IFXObject;
import org.sourceforge.ifx.basetypes.IBaseType;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import org.jdom.input.SAXBuilder;

/**
 * Encodes an IFXObject to its equivalent IFX XML Element.
 *
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class IFXEncoder {

    private ResourceBundle bundle;

    private int xmlIndentSize = 2;

    private String lineSeparator = System.getProperty("line.separator");

    private Namespace namespace = Namespace.NO_NAMESPACE;

    /**
     * Creates an IFXEncoder object in the default namespace.
     */
    public IFXEncoder() {
    }

    /**
     * Creates an IFXEncoder in the specified namespace.
     * @param namespacePrefix the namespace prefix to use. If set to null,
     * then the element will be considered to be in the default namespace.
     * @param namespaceURI the URI of the namespace.
     */
    public IFXEncoder(String namespacePrefix, String namespaceURI) {
    }

    /**
     * Builds a JDOM Element for the IFXObject supplied. No validation is
     * performed, regardless of the setting. To validate, use the encode()
     * methods instead.
     * @param obj an IFXObject.
     * @return a JDOM Element.
     * @exception IFXException if an exception was encountered in encoding.
     */
    public Element encode(IFXObject obj) throws IFXException;

    /**
     * Sets the name space for this Document.
     * @param prefix the namespace prefix to use. If null, this namespace
     * will be set to the default namespace.
     * @param nameSpaceURI the String URI of this namespace.
     */
    private void setNamespace(String prefix, String nameSpaceURI);

    /**
     * Return a List of accessor methods for a given IFXObject. The
     * accessors will be ordered in the same order expected by the IFX
     * schema. This ordering is dictated by the ELEMENTS String[] variable.
     * @param obj an IFXObject.
     * @return a List of getXXX Method objects.
     * @exception IFXException wraps the original exception thrown.
     */
    private List getAccessors(IFXObject obj) throws IFXException;

    /**
     * Gets the element name from the object class name. It retrieves the
     * class name portion from the fully qualified class name for the bean,
     * then applies transformations to convert back to dotted form for
     * certain elements.
     * @param obj an IFXObject.
     * @return the name of the element that is represented by this object.
     * @exception IFXException if element name was not found.
     */
    private String getElementName(IFXObject obj) throws IFXException;
}
