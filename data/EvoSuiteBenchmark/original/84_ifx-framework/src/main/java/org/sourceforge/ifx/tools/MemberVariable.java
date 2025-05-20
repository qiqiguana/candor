/*
 * $Id: MemberVariable.java,v 1.1 2004/05/03 21:41:31 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/tools/MemberVariable.java,v $
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
package org.sourceforge.ifx.tools;

/**
 * Models a bean property for a IFX bean. The class defines several convenience
 * methods that operate on the member variable name to produce appropriate names
 * for getter and setter functions for the bean member variable.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1 $
 */
public class MemberVariable {

    private String className;
    private boolean isArray = false;
    private String name;

    /**
     * Constructor to build a MemberVariable object.
     * @param name the name of the member variable. IFX beans will prefix
     * @param className the fully qualified class name for the variable.
     * an underscore character for private variables automatically.
     */
    public MemberVariable(String name, String className, boolean isArray) {
        this.className = className;
        // ensure that the name always begins with lowercase alphabet
        char fchar = name.charAt(0);
        if (Character.isUpperCase(fchar)) {
            this.name = Character.toLowerCase(fchar) + name.substring(1);
        } else {
            this.name = name;
        }
        this.isArray = isArray;
    }

    /**
     * Returns the fully qualified class name of this member variable.
     * @return the fully qualified class name of this member variable.
     */
    public String getClassName() {
        return className + (isArray ? "[]" : "");
    }

    /**
     * Returns the name of this member variable.
     * @return the name of the member variable.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the name of the getXXX() method to retrieve this variable
     * from the bean.
     * @return the getter method name.
     */
    public String getGetterName() {
        return "get" + getElementName();
    }

    /**
     * Returns the name of the setXXX() method to set this variable within
     * the bean.
     * @return the setter method name.
     */
    public String getSetterName() {
        return "set" + getElementName();
    }

    /**
     * Returns the name of the IFX XML element. This involves uppercasing
     * the first character of the name.
     * @return the element name.
     */
    public String getElementName() {
        char fchar = name.charAt(0);
        return Character.toUpperCase(fchar) + name.substring(1);
    }

    /**
     * This is used to throttle duplicate member variables. There are 
     * cases in the schema where a member variable can appear in one of
     * two optional sequences, so there should be one member variable
     * regardless of which sequence is populated.
     * @return a hashcode for the variable name.
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * The equality check would be used in the call to List.contains()
     * from the addMemberVariable() method in JavaSource.
     * @param obj the Object to compare against.
     * @return true if the names of the two member variable objects are equal.
     */ 
    public boolean equals(Object obj) {
        if (!(obj instanceof MemberVariable)) { return false; }
        MemberVariable that = (MemberVariable) obj;
        return (this.getName().equals(that.getName()));
    }
}
