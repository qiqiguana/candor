/* $Id: ModuleInfo.java,v 1.4 2004/04/27 19:26:21 bja Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.4 $
 *
 */

package module;

import java.lang.annotation.*;
import java.util.regex.*;
import java.lang.reflect.*;
import java.util.*;

/**
 * Contains module specific information. The information contained
 * within the ModuleInfo is mainly used for debugging. 
 */
public class ModuleInfo {

    String file;    
    String revision;
    String date;
    String author;
    String tag;
    String name;
    String desc; 
    String cmds; 
    String topics;

    /** Hashtable containing all annotations. */
    private Hashtable<String,String> hash = new Hashtable<String,String>();

    /**
     * Regular expression that splits annotations into key and value, 
     * It cannot handle commas and right parens inside values, see BUGS!
     */
    private static Pattern p = 
	Pattern.compile("(\\p{Alpha}+)=(?:[$]\\p{Alpha}+[:]\\s*)?" +
			"(([\\p{Graph}&&[^=,$)]]|\\p{Blank})*)[$]?");

    /** 
     * Creates a new instance of the ModuleInfo class. 
     */
    public ModuleInfo() { }

    /** 
     * Creates a new instance of the ModuleInfo class. 
     * @param mod The module which to build info for.
     */
    public ModuleInfo(Module mod) {
	this(mod.getClass().getAnnotations());
    }

    /** 
     * Creates a new instance of the ModuleInfo class using the specified
     * annotation array.
     *
     * @param av Array of Annotation objects to build info from.
     */
    public ModuleInfo(Annotation[] av) {

	/* Get annotations and put them into hashtable. */

	for (Annotation a : av) {
	    Matcher m = p.matcher(a.toString());
	    while (m.find()) {
		hash.put(m.group(1), m.group(2));
	    }
	}

	/* Fill in instance variables with values contained in hash. */

	for (Field f : getClass().getDeclaredFields()) {
	    try {
		String val = get(f.getName());
		if (val != null) {
		    f.set(this, val);
		} 
	    } catch (Exception e) {
		e.printStackTrace(System.err);
	    }
	}
    }

    /**
     * Returns the value of the specified annotation field.
     *
     * @param key the annotation field to get.
     * @return the value of the specified annotation field.
     */
    public String get(String key) {
	return hash.get(key);
    }

    /**
     * Sets the value of the specified attribute to the given value.
     *
     * @param key the annotation field to get.
     * @param val the value to set.
     */
    public void set(String key, String val) {
	try {
	    hash.put(key,val);
	    Field f = getClass().getDeclaredField(key);
	    f.set(this, val);
	} catch (NoSuchFieldException e) {	// not a problem really.
	    //e.printStackTrace(System.err);
	} catch (IllegalArgumentException e) {	// neither is this.
	    //e.printStackTrace(System.err);
	} catch (IllegalAccessException e) {	// do not use security manager.
	    //e.printStackTrace(System.err);
	}
    }

    public void update() {
	hash.put("name",name);
    }

    public String toString() {
	return hash.toString();
    }
}
