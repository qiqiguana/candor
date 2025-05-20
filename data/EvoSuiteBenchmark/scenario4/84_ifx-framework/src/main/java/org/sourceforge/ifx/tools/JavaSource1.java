package org.sourceforge.ifx.tools;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class provides an abstraction to collect information about a
 * generated Java class file. Its toString() method will produce a String
 * that can be written using a Writer object to persistent store.
 *
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
    public String getPackageName();

    /**
     * Allows setting the package name for the class.
     * @param packageName the package name to set.
     */
    public void setPackageName(String packageName);

    /**
     * Returns the class javadocs for this class.
     * @return the class javadocs for this class.
     */
    public String getClassJavadocs();

    /**
     * Allows setting the class javadocs for this class.
     * @param classJavadocs the class level javadocs to set.
     */
    public void setClassJavadocs(String classJavadocs);

    /**
     * Returns the class name.
     * @return the class name.
     */
    public String getClassName();

    /**
     * Sets the class name.
     * @param className the class name.
     */
    public void setClassName(String className);

    /**
     * Gets the superclass name for this class.
     * @return the superclass for this class.
     */
    public String getSuperClass();

    /**
     * Sets the superclass name if applicable.
     * @param superClass the super class name to set.
     */
    public void setSuperClass(String superClass);

    /**
     * Gets the fully qualified interface for this class if specified,
     * @return the interface name for this class.
     */
    public String getInterface();

    /**
     * Sets the fully qualified interface name for this class.
     * @param interfaceName the fully qualified interface name for this class.
     */
    public void setInterface(String interfaceName);

    /**
     * Returns a list of member variable objects in this class.
     * @return a list of member variable objects in this class.
     */
    public List getMemberVariables();

    /**
     * Returns the number of member variables for this class.
     * @return the number of member variables.
     */
    public int getNumberOfMemberVariables();

    /**
     * Adds a new member variable object for the class. Deduces a variable
     * name from the class name.
     * @param mVarClass the class name for the member variable.
     * @param isArray if the member variable type is an array.
     */
    public void addMemberVariable(String mVarClass, boolean isArray);

    /**
     * Adds a new member variable for the class.
     * @param mVarName the name of the member variable.
     * @param mVarClass the class name for the member variable.
     * @param isArray if the member variable type is an array.
     */
    public void addMemberVariable(String mVarName, String mVarClass, boolean isArray);

    /**
     * Convenience methods since velocity does not understand the conditional
     * "== null". Returns true if this class implements an interface.
     * @return true if this class implements an interface else false.
     */
    public boolean hasInterface();

    /**
     * Convenience method since velocity does not understand the conditional
     * "== null". Returns true if this class inherits from a superclass.
     * @return true if this class has a superclass, else false.
     */
    public boolean hasSuperClass();
}
