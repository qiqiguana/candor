/*
 * $Id: IFXEncoder.java,v 1.2 2004/03/05 02:17:24 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/utils/IFXEncoder.java,v $
 * IFX-Framework - IFX XML to JavaBean application framework.
 * Copyright (C) 2003  The IFX-Framework Team
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
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
        try {
            bundle = ResourceBundle.getBundle(
                "org.sourceforge.ifx.framework.beanmap");
        } catch (MissingResourceException e) { 
            e.printStackTrace();
            // :NOTE: should never happen
        }
    }

    /**
     * Creates an IFXEncoder in the specified namespace.
     * @param namespacePrefix the namespace prefix to use. If set to null,
     * then the element will be considered to be in the default namespace.
     * @param namespaceURI the URI of the namespace.
     */
    public IFXEncoder(String namespacePrefix, String namespaceURI) {
        this();
        this.setNamespace(namespacePrefix, namespaceURI);
    }

    /**
     * Builds a JDOM Element for the IFXObject supplied. No validation is
     * performed, regardless of the setting. To validate, use the encode()
     * methods instead.
     * @param obj an IFXObject.
     * @return a JDOM Element.
     * @exception IFXException if an exception was encountered in encoding.
     */
    public Element encode(IFXObject obj) throws IFXException {
        try {
            Element element = new Element(getElementName(obj), namespace);
            List accessorList = getAccessors(obj);
            Iterator accessorIter = accessorList.iterator();
            while (accessorIter.hasNext()) {
                Method accessorMethod = (Method) accessorIter.next();
                if (accessorMethod.getName().equals("getClass")) { continue; }
                Object retValObj = accessorMethod.invoke(obj, null);
                if (retValObj == null) { continue; }
                IFXObject[] retVals = new IFXObject[1];
                if (retValObj.getClass().isArray()) {
                    retVals = (IFXObject[]) retValObj;
                } else {
                    retVals[0] = (IFXObject) retValObj;
                }
                for (int i = 0; i < retVals.length; i++) {
                    IFXObject retVal = retVals[i];
                    if (retVal instanceof IBaseType) {
                        String value = ((IBaseType) retVal).getString();
                        if (accessorMethod.getName().equals("getId")) {
                            element.setAttribute("Id", value);
                        } else {
                            Element baseElement = 
                                new Element(getElementName(retVal), namespace);
                            baseElement.setText(value);
                            element.addContent(baseElement);
                        }
                    } else {
                        element.addContent(encode(retVal));
                    }
                }
            }
            return element;
        } catch (Exception e) {
            throw new IFXException(
                "Error encoding " + obj.getClass().getName(), e);
        }
    }

    /**
     * Sets the name space for this Document.
     * @param prefix the namespace prefix to use. If null, this namespace
     * will be set to the default namespace.
     * @param nameSpaceURI the String URI of this namespace.
     */
    private void setNamespace(String prefix, String nameSpaceURI) {
        if (prefix == null) {
            this.namespace = Namespace.getNamespace(nameSpaceURI);
        } else {
            this.namespace = Namespace.getNamespace(prefix, nameSpaceURI);
        }
    }

    /**
     * Return a List of accessor methods for a given IFXObject. The
     * accessors will be ordered in the same order expected by the IFX
     * schema. This ordering is dictated by the ELEMENTS String[] variable.
     * @param obj an IFXObject.
     * @return a List of getXXX Method objects.
     * @exception IFXException wraps the original exception thrown.
     */
    private List getAccessors(IFXObject obj) throws IFXException {
        try {
            Class cl = obj.getClass();
            Method[] allMethods = cl.getMethods();
            Map getMethodsMap = new HashMap();
            for (int i = 0; i < allMethods.length; i++) {
                if (allMethods[i].getName().startsWith("get")) {
                    getMethodsMap.put(allMethods[i].getName(), allMethods[i]);
                }
            }
            Field elementsField = cl.getField("ELEMENTS");
            String[] elements = (String[]) elementsField.get(obj);
            List getMethods = new ArrayList();
            for (int i = 0; i < elements.length; i++) {
                String elementName = elements[i];
                Method getMethod = 
                    (Method) getMethodsMap.get("get" + elementName);
                getMethods.add(getMethod);
            }
            return getMethods;
        } catch (Exception e) {
            throw new IFXException("Exception getting Accessor", e);
        }
    }

    /**
     * Gets the element name from the object class name. It retrieves the
     * class name portion from the fully qualified class name for the bean,
     * then applies transformations to convert back to dotted form for
     * certain elements.
     * @param obj an IFXObject.
     * @return the name of the element that is represented by this object.
     * @exception IFXException if element name was not found.
     */
    private String getElementName(IFXObject obj) throws IFXException {
        String elementName = bundle.getString(obj.getClass().getName());
        if (elementName == null) {
            throw new IFXException("No element found for " + 
                obj.getClass().getName());
        }
        return elementName;
    }
}
