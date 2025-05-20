/*
 * $Id: IFXDecoder.java,v 1.3 2004/03/05 02:17:24 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/utils/IFXDecoder.java,v $
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
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.3 $
 */
public class IFXDecoder {
    
    private ResourceBundle bundle;

    /**
     * Creates an IFXDecoder object.
     */
    public IFXDecoder() {
        try {
            bundle = ResourceBundle.getBundle(
                "org.sourceforge.ifx.framework.elementmap");
        } catch (MissingResourceException e) {
            e.printStackTrace();
            // :NOTE: should never happen
        }
    }

    /**
     * Decodes the element into its equivalent IFX XML representation.
     * @param element the Element to decode.
     * @return an IFXObject.
     * @exception IFXException wrapper for underlying exception.
     */
    public IFXObject decode(Element element) throws IFXException {
        try {
            String id = element.getAttributeValue("Id");
            String beanClassName = getBeanClassName(element);
            IFXObject obj = 
                (IFXObject) Class.forName(beanClassName).newInstance();
            List children = element.getChildren();
            if (children.size() == 0) {
                String text = element.getText();
                if (text != null && text.trim().length() > 0) {
                    if (!(obj instanceof IBaseType)) {
                        throw new IFXException(
                            "Leaf node not an instance of IBaseType: " + 
                            obj.getClass().getName());
                    } else {
                        ((IBaseType) obj).setString(text);
                        return obj;
                    }
                } else { 
                    // return the empty object
                    return obj;
                }
            } else {
                Iterator childIter = children.iterator();
                while (childIter.hasNext()) {
                    Element childElement = (Element) childIter.next();
                    IFXObject childObj = decode(childElement);
                    // run corresponding accessor method to see if the
                    // result is an array
                    Method accessorMethod = getAccessor(obj, childElement);
                    Object retValObj = accessorMethod.invoke(obj, null);
                    boolean isArray = accessorMethod.getReturnType().isArray();
                    // then get the mutator method and invoke it
                    Method mutatorMethod = getMutator(obj, childElement);
                    if (isArray) {
                        // if result is an array, build a new array with 
                        // size = returned array size + 1, and stuff this
                        // object at the end of the array
                        IFXObject[] childObjs = null;
                        if (retValObj != null) {
                            int oldLen = Array.getLength(retValObj);
                            childObjs = (IFXObject[]) Array.newInstance(
                                childObj.getClass(), new int[] {oldLen + 1});
                            IFXObject[] retValObjs = (IFXObject[]) retValObj;
                            System.arraycopy(retValObj,0,childObjs,0,oldLen);
                            childObjs[oldLen] = childObj;
                        } else {
                            childObjs = (IFXObject[]) Array.newInstance(
                                childObj.getClass(), new int[] {1});
                            childObjs[0] = childObj;
                        }
                        mutatorMethod.invoke(obj, new Object[] {childObjs});
                    } else {
                        mutatorMethod.invoke(obj, new Object[] {childObj});
                    }
                }
                // if Id attribute is specified, populate it
                if (id != null) {
                    Method mutatorMethod = getMutator(obj, "Id");
                    IFXString idObj = new IFXString();
                    idObj.setString(id);
                    mutatorMethod.invoke(obj, new Object[] {idObj});
                }
                return obj;
            }
        } catch (Exception e) {
            throw new IFXException("Error decoding " + element.getName(), e);
        }
    }

    /**
     * Returns a single setXXX Method object corresponding to the IFXObject
     * and childElement.
     * @param obj the IFXObject.
     * @param childElement the element corresponding to the mutator method.
     * @return a setXXX method object.
     * @exception IFXException if there was a problem getting the mutators.
     */
    private Method getMutator(IFXObject obj, Element childElement) 
            throws IFXException {
        String childElementName = childElement.getName();
        return getMutator(obj, childElementName);
    }

    /**
     * Returns a single setXXX Method object corresponding to the IFXObject
     * and childElement name.
     * @param obj the IFXObject.
     * @param childElementName the element name for the mutator method.
     * @return a setXXX method object.
     * @exception IFXException if there was a problem getting the mutators.
     */
    private Method getMutator(IFXObject obj, String elementName) 
            throws IFXException {
        try {
            // handle element names like USA.MilitaryRank or EU.Cur
            if (elementName.indexOf('.') > -1) {
                elementName = elementName.replace('.', '_');
            }
            String setterName = "set" + elementName;
            Class cl = obj.getClass();
            Method[] methods = cl.getMethods();
            boolean foundMethod = false;
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().equals(setterName)) {
                    foundMethod = true;
                    return methods[i];
                } else { continue; }
            }
            // if we are here, then we did not get the method
            if (!foundMethod) {
                throw new IFXException("Could not find method: " + 
                    obj.getClass().getName() + "#set" + elementName);
            }
        } catch (Exception e) {
            throw new IFXException("Exception getting Mutator Method: " + 
                obj.getClass().getName() + "#set" + elementName, e);
        }
        return null;
    }

    /**
     * Returns the accessor method associated with the given element name.
     * @param obj an IFXObject.
     * @param element the element object.
     * @return a getXXX Method object associated with the element.
     * @exception IFXException wraps the original exception thrown.
     */
    private Method getAccessor(IFXObject obj, Element element) 
            throws IFXException {
        try {
            String elementName = element.getName();
            // handle element names like USA.MilitaryRank or EU.Cur
            if (elementName.indexOf('.') > -1) {
                elementName = elementName.replace('.', '_');
            }
            String getterName = "get" + elementName;
            Class cl = obj.getClass();
            Method[] allMethods = cl.getMethods();
            Method accessorMethod = null;
            for (int i = 0; i < allMethods.length; i++) {
                if (allMethods[i].getName().equals(getterName)) {
                    accessorMethod = allMethods[i];
                    break;
                }
            }
            return accessorMethod;
        } catch (Exception e) {
            throw new IFXException("Exception getting Accessor", e);
        }
    }

    /**
     * Returns the element name from a bean name.
     * @param element the Element representing the bean.
     * @return the element name.
     * @exception IFXException if bean class name could not be found.
     */
    private String getBeanClassName(Element element) throws IFXException {
        String beanClassName = bundle.getString(element.getName());
        if (beanClassName == null) {
            throw new IFXException("No bean class found for: " + 
                element.getName());
        }
        return beanClassName;
    }
}
