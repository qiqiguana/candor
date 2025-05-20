/*
 * $Id: JavaSource.java,v 1.5 2004/05/14 23:56:11 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/tools/JavaSource.java,v $
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

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class provides an abstraction to collect information about a 
 * generated Java class file. Its toString() method will produce a String
 * that can be written using a Writer object to persistent store.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.5 $
 */
public class JavaSource {
    
    private String packageName = null;
    private String classJavadocs = null;
    private String className = null;
    private String superClass = null;
    private String interfaceName = null;
    private List memberVariables = new ArrayList();
    private boolean hasSuperClass = false;
    private boolean isInterface = false;

    /**
     * Returns the package name for the class.
     * @return the package name for the class.
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Allows setting the package name for the class.
     * @param packageName the package name to set.
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Returns the class javadocs for this class.
     * @return the class javadocs for this class.
     */
    public String getClassJavadocs() {
        if (classJavadocs == null) return "";
        else return classJavadocs;
    }

    /**
     * Allows setting the class javadocs for this class.
     * @param classJavadocs the class level javadocs to set.
     */
    public void setClassJavadocs(String classJavadocs) {
        this.classJavadocs = classJavadocs;
    }

    /**
     * Returns the class name.
     * @return the class name.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the class name.
     * @param className the class name.
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Gets the superclass name for this class.
     * @return the superclass for this class.
     */
    public String getSuperClass() {
        return superClass;
    }

    /**
     * Sets the superclass name if applicable.
     * @param superClass the super class name to set.
     */
    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    /**
     * Gets the fully qualified interface for this class if specified,
     * @return the interface name for this class.
     */
    public String getInterface() {
        return interfaceName;
    }

    /**
     * Sets the fully qualified interface name for this class.
     * @param interfaceName the fully qualified interface name for this class.
     */
    public void setInterface(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * Returns a list of member variable objects in this class.
     * @return a list of member variable objects in this class.
     */
    public List getMemberVariables() {
        return memberVariables;
    }

    /**
     * Returns the number of member variables for this class.
     * @return the number of member variables.
     */
    public int getNumberOfMemberVariables() {
        return memberVariables.size();
    }

    /**
     * Adds a new member variable object for the class. Deduces a variable
     * name from the class name.
     * @param mVarClass the class name for the member variable.
     * @param isArray if the member variable type is an array.
     */
    public void addMemberVariable(String mVarClass, boolean isArray) {
        String mVarName = mVarClass.substring(mVarClass.lastIndexOf('.') + 1);
        addMemberVariable(mVarName, mVarClass, isArray);
    }

    /**
     * Adds a new member variable for the class.
     * @param mVarName the name of the member variable.
     * @param mVarClass the class name for the member variable.
     * @param isArray if the member variable type is an array.
     */
    public void addMemberVariable(String mVarName, String mVarClass, 
            boolean isArray) {
        MemberVariable mv = new MemberVariable(mVarName, mVarClass, isArray);
        if (!memberVariables.contains(mv)) {
            memberVariables.add(mv);
        }
    }

    /**
     * Convenience methods since velocity does not understand the conditional
     * "== null". Returns true if this class implements an interface.
     * @return true if this class implements an interface else false.
     */
    public boolean hasInterface() {
        return (this.getInterface() != null);
    }

    /**
     * Convenience method since velocity does not understand the conditional
     * "== null". Returns true if this class inherits from a superclass.
     * @return true if this class has a superclass, else false.
     */
    public boolean hasSuperClass() {
        return (this.getSuperClass() != null);
    }
}
