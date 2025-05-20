package org.sourceforge.ifx.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import org.sourceforge.ifx.basetypes.IFXObject;
import org.sourceforge.ifx.basetypes.IFXString;
import org.sourceforge.ifx.basetypes.IBaseType;
import org.jdom.Element;

/**
 * Decodes an IFX XML String to its equivalent Framework bean.
 *
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.3 $
 */
public class IFXDecoder {

    private ResourceBundle bundle;

    /**
     * Creates an IFXDecoder object.
     */
    public IFXDecoder() {
    }

    /**
     * Decodes the element into its equivalent IFX XML representation.
     * @param element the Element to decode.
     * @return an IFXObject.
     * @exception IFXException wrapper for underlying exception.
     */
    public IFXObject decode(Element element) throws IFXException;

    /**
     * Returns a single setXXX Method object corresponding to the IFXObject
     * and childElement.
     * @param obj the IFXObject.
     * @param childElement the element corresponding to the mutator method.
     * @return a setXXX method object.
     * @exception IFXException if there was a problem getting the mutators.
     */
    private Method getMutator(IFXObject obj, Element childElement) throws IFXException;

    /**
     * Returns a single setXXX Method object corresponding to the IFXObject
     * and childElement name.
     * @param obj the IFXObject.
     * @param childElementName the element name for the mutator method.
     * @return a setXXX method object.
     * @exception IFXException if there was a problem getting the mutators.
     */
    private Method getMutator(IFXObject obj, String elementName) throws IFXException;

    /**
     * Returns the accessor method associated with the given element name.
     * @param obj an IFXObject.
     * @param element the element object.
     * @return a getXXX Method object associated with the element.
     * @exception IFXException wraps the original exception thrown.
     */
    private Method getAccessor(IFXObject obj, Element element) throws IFXException;

    /**
     * Returns the element name from a bean name.
     * @param element the Element representing the bean.
     * @return the element name.
     * @exception IFXException if bean class name could not be found.
     */
    private String getBeanClassName(Element element) throws IFXException;
}
