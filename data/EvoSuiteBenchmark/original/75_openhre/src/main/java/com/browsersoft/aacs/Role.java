/*
 *   CVS $Id: Role.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
 * 
 *   ====================================================================
 *                 Open Source Health Records Exchange
 *   ====================================================================
 *
 *   Copyright (C) 2005 Browsersoft Inc.
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License, version 2, 
 *   as published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   The GNU General Public License is available at
 *   http://www.fsf.org/licensing/licenses/gpl.html
 *
 *   Email: info@openhre.org
 *   Web:   http://www.openhre.org
 */
package com.browsersoft.aacs;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import java.util.Hashtable;
import java.util.Properties;

public class Role implements DirContext {

    private String cn;
    private String dn;
    
    private static Properties props = new Properties();
    private static String configFile = "/KrbLdap";
    
    Attributes myAttrs = new BasicAttributes(true);
    Attribute oc = new BasicAttribute("objectclass");
    
    public Role() {
    }
    
    public Role(String cn) {
        // read the configuration properties
 	    Login.loadProperties(props, configFile);

 	   	String basedn = props.getProperty("basedn", "dc=arch,dc=org");
 		
 		this.cn = cn;
        this.dn= "cn=" + cn + ",ou=roles," + basedn;
        
        oc.add("organizationalRole");
        oc.add("top");
        myAttrs.put(oc);
        myAttrs.put("cn",cn);
    }
   
    public String getCn() {
        return cn;
    }
    
    public void setCn(String cn) {
        this.cn = cn;
    }
    
    public String getDn() {
        return dn;
    }
    
    public void setDn(String dn) {
        this.dn = dn;
    }
    

    // overriding DirContext methods
    
	public void bind(Name name, Object obj, Attributes attrs){
	}
	
	public void bind(String name, Object obj, Attributes attrs){
	}
	
	public DirContext createSubcontext(Name name, Attributes attrs){
		return null;
	}

	public DirContext createSubcontext(String name, Attributes attrs) {
		return null;
	}
 	
    public Attributes getAttributes(String name) throws NamingException {
       if (! name.equals("")){
          throw new NameNotFoundException();
       }
       return myAttrs;
    }
 
    public Attributes getAttributes(Name name) throws NamingException {
       return getAttributes(name.toString());
    }
 
    public Attributes getAttributes(String name, String[] ids)
                                             throws NamingException {
       if(! name.equals(""))
          throw new NameNotFoundException();
       Attributes answer = new BasicAttributes(true);
       Attribute target;
       for (int i = 0; i < ids.length; i++){
          target = myAttrs.get(ids[i]);
          if (target != null){
             answer.put(target);
          }
       }
       return answer;
    }

    public Attributes getAttributes(Name name, String[] ids)
                                               throws NamingException {
       return getAttributes(name.toString(), ids);
    }

    public DirContext getSchema(Name name) {
    	return null;
    }
    		
    public DirContext getSchema(String name) {
    	return null;
    }

    public DirContext getSchemaClassDefinition(Name name){
    	return null;
    }

    public DirContext getSchemaClassDefinition(String name){
    	return null;
    }

    public void modifyAttributes(Name name, int mod_op, Attributes attrs){
    }

    public void modifyAttributes(Name name, ModificationItem[] mods){
    }

    public void modifyAttributes(String name, int mod_op, Attributes attrs){
    }

    public void modifyAttributes(String name, ModificationItem[] mods){
    }

    public void rebind(Name name, Object obj, Attributes attrs){
    }

    public void rebind(String name, Object obj, Attributes attrs){
    }
    
    public NamingEnumeration search(Name name, Attributes matchingAttributes){
        return null;
    }

    public NamingEnumeration search(Name name, Attributes matchingAttributes, String[] attributesToReturn){
        return null;
    }
 
    public NamingEnumeration search(Name name, String filterExpr, Object[] filterArgs, SearchControls cons){
        return null;
    }
 
    public NamingEnumeration search(Name name, String filter, SearchControls cons){
        return null;
    }
 
    public NamingEnumeration search(String name, Attributes matchingAttributes){
        return null;
    }

    public NamingEnumeration search(String name, Attributes matchingAttributes, String[] attributesToReturn){
        return null;
    }
 
    public NamingEnumeration search(String name, String filterExpr, Object[] filterArgs, SearchControls cons){
        return null;
    }
 
    public NamingEnumeration search(String name, String filter, SearchControls cons) {
        return null;
    }
    
    //  overriding Context methods
    
    public Object 	addToEnvironment(String propName, Object propVal) {
        return null;
    }
    
    public void bind(Name name, Object obj) {
    }
		
	public void bind(String name, Object obj) {
    }
		
	public void close() {
    }
		
	public Name composeName(Name name, Name prefix) {
        return null;
    }
    
	public String composeName(String name, String prefix) {
        return null;
    }
    
	public Context createSubcontext(Name name) {
        return null;
    }
    
	public Context createSubcontext(String name) {
        return null;
    }
    
	public void destroySubcontext(Name name) {
    }
		
	public void destroySubcontext(String name) {
    }
		
	public Hashtable getEnvironment() {
        return null;
    }
    
	public String getNameInNamespace() {
        return null;
    }
    
	public NameParser getNameParser(Name name) {
        return null;
    }
    
	public NameParser getNameParser(String name) {
        return null;
    }
    
	public NamingEnumeration list(Name name) {
        return null;
    }
    
	public NamingEnumeration list(String name) {
        return null;
    }
    
	public NamingEnumeration listBindings(Name name) {
        return null;
    }
    
	public NamingEnumeration listBindings(String name) {
        return null;
    }
    
	public Object lookup(Name name) {
        return null;
    }
    
	public Object lookup(String name) {
        return null;
    }
    
	public Object lookupLink(Name name) {
        return null;
    }
    
	public Object lookupLink(String name) {
        return null;
    }
    
	public void rebind(Name name, Object obj) {
    }
		
	public void rebind(String name, Object obj) {
    }
		
	public Object removeFromEnvironment(String propName) {
        return null;
    }
    
	public void rename(Name oldName, Name newName) {
    }
		
	public void rename(String oldName, String newName) {
    }
		
	public void unbind(Name name) {
    }
		
	public void unbind(String name) {
    }
}