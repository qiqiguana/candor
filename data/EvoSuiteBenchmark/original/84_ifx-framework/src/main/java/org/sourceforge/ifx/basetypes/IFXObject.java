/*
 * $Id: IFXObject.java,v 1.4 2004/03/05 02:17:24 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/basetypes/IFXObject.java,v $
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
package org.sourceforge.ifx.basetypes;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * This is the superclass from which all IFX beans descend. This is needed
 * to make the XML Encoding and Decoding happen in a general way.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.4 $
 */
public class IFXObject {

    /** Tabstop specifying nesting depth for nested objects in toString() */
    private final static String TABSTOP = "  ";

    /**
     * Compares two IFXObjects for equality. This class uses reflection
     * to compare two objects. The first test is class equivalence. The
     * next test is to check if both are NULL, if both are NULL, then 
     * they are treated as equal. The next test will recursively go through
     * each getXXX() method and compare the results for equality. This 
     * is a hog and has application during testing, so thats not too much
     * of a concern.
     * @param obj the Object to compare against.
     * @return true if the objects are equal, else false.
     */ 
    public boolean equals(Object obj) {
        if (!(obj instanceof IFXObject)) return false;
        IFXObject that = (IFXObject) obj;
        return doCheckEquals(this, that);
    }

    /**
     * Returns a String representation of this object. The method uses
     * reflection to stringify the values of the member variables. This
     * method is useful for dumping the contents of a IFXObject to see
     * whats going wrong or for logging. Needs to be used sparingly since
     * it uses reflection.
     * @return a String representation of this object.
     */
    public String toString() {
        return stringify(this, 0);
    }

    /**
     * Does the real work of stringifying the IFXObject. Called from the
     * toString object.
     * @param obj the IFXObject to stringify.
     * @param level the depth in which the current object is to be nested.
     * @return a String representation of this object.
     */
    private String stringify(IFXObject obj, int level) {
        int depth = level;
        StringBuffer buf = new StringBuffer();
        try {
            Class objClass = obj.getClass();
            buf.append(objClass.getName()).append(" = {");
            depth++;
            Method[] methods = objClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                String methodName = method.getName();
                if ((!methodName.equals("getClass")) && 
                        (methodName.startsWith("get"))) {
                    String memberName = methodName.substring(3);
                    buf.append("\n").append(tab(depth)).
                        append(memberName).append(" = ");
                    Object retObj = method.invoke(obj, null);
                    if (retObj == null) {
                        buf.append("NULL");
                    } else if (retObj.getClass().isArray()) {
                        int numObjs = Array.getLength(retObj);
                        buf.append("[\n");
                        depth++;
                        for (int j = 0; j < numObjs; j++) {
                            if (j > 0) { buf.append(",\n"); }
                            buf.append(tab(depth)).append("[").
                                append(j).append("]:");
                            IFXObject arrayObj = 
                                (IFXObject) Array.get(retObj, j);
                            buf.append(stringify(arrayObj, depth));
                        }
                        depth--;
                        buf.append("\n").append(tab(depth)).append("]");
                    } else if (retObj instanceof IBaseType) {
                        buf.append(((IBaseType) retObj).getString());
                    } else {
                        buf.append(stringify(((IFXObject) retObj), depth));
                    }
                }
            }
        } catch (Exception e) {
            buf.append("Exception! " + e.toString());
        }
        depth--;
        buf.append("\n").append(tab(depth)).append("}");
        return buf.toString();
    }

    /**
     * Private workhorse method to compare two IFXObjects. Called from
     * IFXObject.equals().
     * @param thisObj the IFX object to compare against.
     * @param thatObj the IFX object to compare.
     * @return true if the two objects are equal, else false.
     */
    private boolean doCheckEquals(IFXObject thisObj, IFXObject thatObj) {
        // both null check
        if (thisObj == null && thatObj == null) return true; 
        // if one is null and the other is not then equals is false,
        // no point checking any further
        if (thisObj == null ^ thatObj == null) { return false; }
        // same class check
        if (!(thisObj.getClass().getName().equals(
            thatObj.getClass().getName()))) { return false; }
        // both not null and same class, now check for subelements
        Method[] thisMethods = thisObj.getClass().getMethods();
        Method[] thatMethods = thatObj.getClass().getMethods();
        boolean matched = true;
        for (int i = 0; i < thisMethods.length; i++) {
            if ((thisMethods[i].getName().startsWith("get")) &&
                (!(thisMethods[i].getName().equals("getClass")))) {
                // find the corresponding method in thatObj
                Method thatMethod = null;
                for (int j = 0; j < thatMethods.length; j++) {
                    if (thatMethods[j].getName().equals(
                        thisMethods[i].getName())) {
                        thatMethod = thatMethods[j];
                        break;
                    }
                }
                IFXObject thatRetVal = null;
                IFXObject thisRetVal = null;
                try {
                    if (thisMethods[i].getReturnType().isArray()) {
                        Object thisRetValObj = 
                            thisMethods[i].invoke(thisObj, null);
                        Object thatRetValObj = 
                            thatMethod.invoke(thatObj, null);
                        int thisNumElements = 0;
                        int thatNumElements = 0;
                        if (thisRetValObj != null) {
                            thisNumElements = Array.getLength(thisRetValObj);
                        }
                        if (thatRetValObj != null) {
                            thatNumElements = Array.getLength(thatRetValObj);
                        }
                        if (thisNumElements != thatNumElements) {
                            matched = false;
                        }
                        for (int j = 0; j < thisNumElements && matched; j++) {
                            thisRetVal = 
                                (IFXObject) Array.get(thisRetValObj, j);
                            thatRetVal = 
                                (IFXObject) Array.get(thatRetValObj, j);
                            matched = doCheckEquals(thisRetVal, thatRetVal);
                        }
                    } else {
                        thisRetVal =
                            (IFXObject) thisMethods[i].invoke(thisObj, null);
                        thatRetVal =
                            (IFXObject) thatMethod.invoke(thatObj, null);
                        if ((thisRetVal instanceof IBaseType) &&
                            (thatRetVal instanceof IBaseType)) {
                            String thisRetValAsString = 
                                (((IBaseType) thisRetVal).getString());
                            String thatRetValAsString = 
                                (((IBaseType) thatRetVal).getString());
                            if (thisRetValAsString == null && 
                                thatRetValAsString == null) {
                                matched = true;
                            } else if (thisRetValAsString == null ^ 
                                thatRetValAsString == null) {
                                matched = false;
                            } else {
                                matched = thisRetValAsString.equals(
                                    thatRetValAsString);
                            }
                        } else {
                            matched = doCheckEquals(thisRetVal, thatRetVal);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                    matched = false;
                }
                // equals are ANDed, so the first false will make it all false
                if (matched == false) break;
            }
        }
        return matched;
    }

    /**
     * Builds the appropriate tab depth for the given nesting depth.
     * @param level the nesting depth
     * @return the tab.
     */
    private String tab(int level) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < level; i++) {
            buf.append(TABSTOP);
        }
        return buf.toString();
    }
}
