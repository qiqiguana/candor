/*
 *   CVS $Id: LdapService.java,v 1.1 2006/11/06 19:51:47 grodecki Exp $
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * @author gsharma
 *
 * LdapService is used as a service for the administrator 
 * to retrieve and modify all the information stored
 * in an LDAP server.
 * 
 */

public class LdapService {
    
    private static org.apache.log4j.Logger cat
      = org.apache.log4j.Logger.getLogger(LdapService.class.getName());
	
	private static Properties props = new Properties();
    private static String configFile = "/KrbLdap";

    private String basedn;
    private List people;
    private List roles;
    private List groups;
    private LdapContext dctx;
    private SearchControls ctls;	//SearchControls determines scope of search and what gets 
									// returned as a result of the search.
    
    public LdapService(LdapContext dctx) {
        this.dctx = dctx;
        init();
    }
    
    private void init() {
        ctls = new SearchControls();		

        // read the configuration properties
        cat.debug("loading properties from "+configFile);
 	    Login.loadProperties(props, configFile);
        basedn = props.getProperty("basedn", "dc=arch,dc=org");
        cat.debug("got properties "+props);
    }
    
    private NamingEnumeration fetch(String namecontext, String[] attrIDs, String filter) {
        NamingEnumeration answer = null;
        try {            
    		ctls.setReturningObjFlag (false);
    		ctls.setReturningAttributes(attrIDs);		// Specify the ids of the attributes to return
    		ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    		answer = dctx.search(namecontext, filter, ctls);  		
    	} catch (NamingException ne) {
    		ne.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        return answer; 
    }
    
    public Person getPerson(String uid) {
        Person person = new Person();
                
        NamingEnumeration answer = fetch("ou=people,"+basedn, null, "(uid="+ uid +")");
        
        try {
		    cat.debug("Person found:");
		    
	        while (answer.hasMore()) {
				SearchResult sr = (SearchResult)answer.next();
				Attributes attrs = sr.getAttributes();
				ArrayList person_roles = new ArrayList();
				ArrayList person_groups = new ArrayList();
							    
				if (attrs == null) {
				    cat.debug("This result has no attributes");
				} else {
				    for (NamingEnumeration naming_enum = attrs.getAll(); naming_enum.hasMore();) {
					    Attribute attrib = (Attribute)naming_enum.next();

					    if (attrib.getID().equals("cn")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person.setCn((String)e.next());
					            cat.debug("cn: " + person.getCn());
					        }
					    }					    
					    else if (attrib.getID().equals("uid")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person.setUid((String)e.next());
					            cat.debug("uid: " + person.getUid());
					        }
					    }					    
					    else if (attrib.getID().equals("sn")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person.setSn((String)e.next());
					        }
					    }
					    else if (attrib.getID().equals("givenName")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person.setGivenname((String)e.next());
					        }
					    }
					    else if (attrib.getID().equals("mail")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person.setEmail((String)e.next());
					        }
					    }
					    else if (attrib.getID().equals("telephoneNumber")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person.setTelephoneNumber((String)e.next());
					        }
					    }
					    else if (attrib.getID().equals("facsimileTelephoneNumber")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person.setFaxTelephoneNumber((String)e.next());
					        }
					    }
					    else if (attrib.getID().equals("postalAddress")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person.setPostalAddress((String)e.next());
					        }
					    }
					    else if (attrib.getID().equals("employeeType")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person_roles.add((String)e.next());
					        }
					    }
					    else if (attrib.getID().equals("ou")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person_groups.add((String)e.next());
					        }
					    }
					    else if (attrib.getID().equals("ipHostNumber")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person.addAllowedip((String)e.next());
					        }
					    }
					    else if (attrib.getID().equals("krb5ValidStart")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person.setPassvalidstart((String)e.next());
					        }
					    }
					    else if (attrib.getID().equals("krb5MaxRenew")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            person.setPassrenewal((String)e.next());
					        }
					    }
					    else if (attrib.getID().equals("krb5MaxLife")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            if(((String)e.next()).equals("-1"))
					                person.setPasschange("yes");
					            else
					                person.setPasschange("no");
					        }
					    }
					   
				    } // end for
				    
				    // set DN
				    person.setDn("uid="+person.getUid()+",ou=people,"+basedn);
				    
				    if (person_roles.size() > 0) {
				        // Convert ArrayList to String[] for forms to handle roles as selected options
				        person.setRoles((String[])person_roles.toArray(new String[person_roles.size()]));
				    }
				    
				    if (person_groups.size() > 0) {
				        // Convert ArrayList to String[] for forms to handle groups as selected options
				        person.setGroups((String[])person_groups.toArray(new String[person_groups.size()]));
				    }
				    
				} // end else
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return person;
    }
    
    public Collection getPeople() {
        //if (people == null) {
            people = new ArrayList();
            
            String[] at = {"uid" ,"cn"};
	        NamingEnumeration answer = fetch("ou=people,"+basedn, at, "(uid=*)");
	        
	        try {
			    cat.debug("People found:");
			    
		        while (answer.hasMore()) {
					SearchResult sr = (SearchResult)answer.next();
					Attributes attrs = sr.getAttributes();
					String person_cn = null;
		    		String person_uid = null;
				    
					if (attrs == null) {
					    cat.debug("This result has no attributes");
					} else {
					    for (NamingEnumeration naming_enum = attrs.getAll(); naming_enum.hasMore();) {
						    Attribute attrib = (Attribute)naming_enum.next();
						    
				    		
						    if (attrib.getID().equals("cn")) {
						        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					    		    person_cn  = (String)e.next();
						        }
						    }
						    
						    else if (attrib.getID().equals("uid")) {
						        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
						            person_uid  = (String)e.next();
						        }
						    }
						    
					    	if ((person_cn != null) && (person_uid != null)) {
						    		people.add(new Person(person_uid, person_cn));
						    		cat.debug(person_cn + " (" + person_uid + ") ");
						    		person_cn = null;
						    		person_uid = null;
					    	}
					    }
					}
				}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        //}

		Collections.sort(people, new peopleComparator());    
        return people;
    }
    
    public Collection getRoles() {        
        //if (roles == null) {
            roles = new ArrayList();
	        
	        String[] at = {"cn"};
	        NamingEnumeration answer = fetch("ou=roles,"+basedn, at, "(cn=*)");
	        
	        try {
			    cat.debug("Roles found:");
		        while (answer.hasMore()) {
					SearchResult sr = (SearchResult)answer.next();
					Attributes attrs = sr.getAttributes();
				    
					if (attrs == null) {
					    cat.debug("This result has no attributes");
					} else {
						for (NamingEnumeration naming_enum = attrs.getAll(); naming_enum.hasMore();) {
						    Attribute attrib = (Attribute)naming_enum.next();
						   					    
						   for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					    		String role_cn = (String)e.next();
					    		roles.add(new Role(role_cn));
					    		cat.debug(role_cn);
						    }
						}
					}
				}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        //}
		Collections.sort(roles, new roleComparator());    
        return roles;
    }
    
    public Collection getGroups() {
        //if (groups == null) {
            groups = new ArrayList();
	        
	        String[] at = {"cn", "ipHostNumber"};
	        NamingEnumeration answer = fetch("ou=groups,"+basedn, at, "(cn=*)");
	        
	        try {
	            cat.debug("Groups found:");
	            
		        while (answer.hasMore()) {
					SearchResult sr = (SearchResult)answer.next();
					Attributes attrs = sr.getAttributes();
					ArrayList group_ips = null;
					
					if (attrs == null) {
					    cat.debug("This result has no attributes");
					} else {
						for (NamingEnumeration naming_enum = attrs.getAll(); naming_enum.hasMore();) {
						    Attribute attrib = (Attribute)naming_enum.next();
						   					    
						   for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
						       // ipHostNumber attribute is returned before the cn attribute for each group.
						       // There can be 0 or more IP address ranges.
						       if (attrib.getID().equals("ipHostNumber")) {					    		    
					    		    String group_ip = (String)e.next();
					    		    cat.debug(group_ip);
					    		    if (!group_ip.trim().equals("0")) {
						    		    if (group_ips == null)
						    		        group_ips = new ArrayList();
						    		    group_ips.add(group_ip);
					    		    }
					    		}
					    		else if (attrib.getID().equals("cn")) {
					    		    String group_cn = (String)e.next();
					    		    groups.add(new Group(group_cn, group_ips));
					    		    cat.debug(group_cn);
					    		}
						    }
						}
					}
				}
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        //}
		Collections.sort(groups, new groupComparator());    
		return groups;
    }
    
    public Group getGroup(String cn) {
        Group grp = new Group(cn);
                
        NamingEnumeration answer = fetch("ou=groups,"+basedn, null, "(cn="+ cn +")");
        
        try {
		    while (answer.hasMore()) {
				SearchResult sr = (SearchResult)answer.next();
				Attributes attrs = sr.getAttributes();
							    
				if (attrs == null) {
				    cat.debug("This result has no attributes");
				} else {
				    for (NamingEnumeration naming_enum = attrs.getAll(); naming_enum.hasMore();) {
					    Attribute attrib = (Attribute)naming_enum.next();

					    if (attrib.getID().equals("ipHostNumber")) {
					        for (NamingEnumeration e = attrib.getAll();e.hasMore();) {
					            grp.addAllowedip((String)e.next());
					        }
					    }				   
				    } // end for				    
				} // end else
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grp;
    }
        
    public boolean addPerson(Person person) {
        // for now we are using simple authentication for LDAP since there seems to be the following
        // error with GSSAPI authentication (which works for search) while requesting a modification to LDAP:
        // javax.naming.AuthenticationNotSupportedException: 
        // [LDAP: error code 8 - modifications require authentication]
        // Awaiting reply from mailing lists.
		
		//String ldapurl = props.getProperty("ldapurl", "ldap://someldapserver.com:389");

		// Set up the environment for creating the initial context
		//Hashtable env = new Hashtable(11);
		//env.put(Context.INITIAL_CONTEXT_FACTORY, 
		//    "com.sun.jndi.ldap.LdapCtxFactory");
		//env.put(Context.PROVIDER_URL, ldapurl);
		
		//env.put(Context.SECURITY_AUTHENTICATION,"simple");
		//env.put(Context.SECURITY_PRINCIPAL,"cn=xxx,dc=arch,dc=org"); // specify the username
		//env.put(Context.SECURITY_CREDENTIALS,"secret");           // specify the password

        try {
			// Create the initial context
            //LdapContext ctx = new InitialLdapContext(env,null);
         	dctx.bind(person.getDn(), person); // was getting error code 8
            return true;
        } catch (NamingException ne) {
    		ne.printStackTrace();
    		return false;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public boolean updatePerson(Person person, String olddn) {
        // for now we are using simple authentication for LDAP since there seems to be the following
        // error with GSSAPI authentication (which works for search) while requesting a modification to LDAP:
        // javax.naming.AuthenticationNotSupportedException: 
        // [LDAP: error code 8 - modifications require authentication]
        // Awaiting reply from mailing lists.

		//String ldapurl = props.getProperty("ldapurl", "ldap://someldapserver.com:389");
		//String basedn = props.getProperty("basedn", "dc=arch,dc=org");
			
		// Set up the environment for creating the initial context
		//Hashtable env = new Hashtable(11);
		//env.put(Context.INITIAL_CONTEXT_FACTORY, 
		//    "com.sun.jndi.ldap.LdapCtxFactory");
		//env.put(Context.PROVIDER_URL, ldapurl);

		//env.put(Context.SECURITY_AUTHENTICATION,"simple");
		//env.put(Context.SECURITY_PRINCIPAL,"cn=xxx,dc=arch,dc=org"); // specify the username
		//env.put(Context.SECURITY_CREDENTIALS,"secret");           // specify the password
		
        try {
			// Create the initial context
			//LdapContext ctx = new InitialLdapContext(env,null);
			
			String newdn = person.getDn();
			if (olddn.compareTo(newdn) != 0)
			    dctx.rename(olddn,newdn);
			
			person.setAttributes();
		    dctx.modifyAttributes(newdn, DirContext.REPLACE_ATTRIBUTE, person.getAttributes());
		
            return true;
        } catch (NamingException ne) {
    		ne.printStackTrace();
    		return false;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public boolean deletePerson(String dn) {
        // for now we are using simple authentication for LDAP since there seems to be the following
        // error with GSSAPI authentication (which works for search) while requesting a modification to LDAP:
        // javax.naming.AuthenticationNotSupportedException: 
        // [LDAP: error code 8 - modifications require authentication]
        // Awaiting reply from mailing lists.

		//String ldapurl = props.getProperty("ldapurl", "ldap://someldapserver.com:389");
		//String basedn = props.getProperty("basedn", "dc=arch,dc=org");
			
		// Set up the environment for creating the initial context
		//Hashtable env = new Hashtable(11);
		//env.put(Context.INITIAL_CONTEXT_FACTORY, 
		//    "com.sun.jndi.ldap.LdapCtxFactory");
		//env.put(Context.PROVIDER_URL, ldapurl);

		//env.put(Context.SECURITY_AUTHENTICATION,"simple");
		//env.put(Context.SECURITY_PRINCIPAL,"cn=xxx,dc=arch,dc=org"); // specify the username
		//env.put(Context.SECURITY_CREDENTIALS,"secret");           // specify the password

        try {
			// Create the initial context
			//LdapContext ctx = new InitialLdapContext(env,null);
		    dctx.destroySubcontext(dn);
            return true;
        } catch (NamingException ne) {
    		ne.printStackTrace();
    		return false;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

    public boolean addRole(Role role) {
        // for now we are using simple authentication for LDAP since there seems to be the following
        // error with GSSAPI authentication (which works for search) while requesting a modification to LDAP:
        // javax.naming.AuthenticationNotSupportedException: 
        // [LDAP: error code 8 - modifications require authentication]
        // Awaiting reply from mailing lists.

		//String ldapurl = props.getProperty("ldapurl", "ldap://someldapserver.com:389");
		//String basedn = props.getProperty("basedn", "dc=arch,dc=org");
		
		// Set up the environment for creating the initial context
		//Hashtable env = new Hashtable(11);
		//env.put(Context.INITIAL_CONTEXT_FACTORY, 
		//    "com.sun.jndi.ldap.LdapCtxFactory");
		//env.put(Context.PROVIDER_URL, ldapurl);
		
		//env.put(Context.SECURITY_AUTHENTICATION,"simple");
		//env.put(Context.SECURITY_PRINCIPAL,"cn=xxx,dc=arch,dc=org"); // specify the username
		//env.put(Context.SECURITY_CREDENTIALS,"secret");           // specify the password

        try {
			// Create the initial context
			//LdapContext ctx = new InitialLdapContext(env,null);
		    dctx.bind(role.getDn(), role);
            return true;
        } catch (NamingException ne) {
    		ne.printStackTrace();
    		return false;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public boolean deleteRole(String dn) {
        // for now we are using simple authentication for LDAP since there seems to be the following
        // error with GSSAPI authentication (which works for search) while requesting a modification to LDAP:
        // javax.naming.AuthenticationNotSupportedException: 
        // [LDAP: error code 8 - modifications require authentication]
        // Awaiting reply from mailing lists.

		//String ldapurl = props.getProperty("ldapurl", "ldap://someldapserver.com:389");
		//String basedn = props.getProperty("basedn", "dc=arch,dc=org");
			
		// Set up the environment for creating the initial context
		//Hashtable env = new Hashtable(11);
		//env.put(Context.INITIAL_CONTEXT_FACTORY, 
		//    "com.sun.jndi.ldap.LdapCtxFactory");
		//env.put(Context.PROVIDER_URL, ldapurl);

		//env.put(Context.SECURITY_AUTHENTICATION,"simple");
		//env.put(Context.SECURITY_PRINCIPAL,"cn=xxx,dc=arch,dc=org"); // specify the username
		//env.put(Context.SECURITY_CREDENTIALS,"secret");           // specify the password

        try {
			// Create the initial context
			//LdapContext ctx = new InitialLdapContext(env,null);
		    dctx.destroySubcontext(dn);
            return true;
        } catch (NamingException ne) {
    		ne.printStackTrace();
    		return false;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public boolean addGroup(Group group) {
        // for now we are using simple authentication for LDAP since there seems to be the following
        // error with GSSAPI authentication (which works for search) while requesting a modification to LDAP:
        // javax.naming.AuthenticationNotSupportedException: 
        // [LDAP: error code 8 - modifications require authentication]
        // Awaiting reply from mailing lists.

		//String ldapurl = props.getProperty("ldapurl", "ldap://someldapserver.com:389");
		//String basedn = props.getProperty("basedn", "dc=arch,dc=org");
		
		// Set up the environment for creating the initial context
		//Hashtable env = new Hashtable(11);
		//env.put(Context.INITIAL_CONTEXT_FACTORY, 
		//    "com.sun.jndi.ldap.LdapCtxFactory");
		//env.put(Context.PROVIDER_URL, ldapurl);
		
		//env.put(Context.SECURITY_AUTHENTICATION,"simple");
		//env.put(Context.SECURITY_PRINCIPAL,"cn=xxx,dc=arch,dc=org"); // specify the username
		//env.put(Context.SECURITY_CREDENTIALS,"secret");           // specify the password

        try {
			// Create the initial context
			//LdapContext ctx = new InitialLdapContext(env,null);
		    dctx.bind(group.getDn(), group);
            return true;
        } catch (NamingException ne) {
    		ne.printStackTrace();
    		return false;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public boolean deleteGroup(String dn) {
        // for now we are using simple authentication for LDAP since there seems to be the following
        // error with GSSAPI authentication (which works for search) while requesting a modification to LDAP:
        // javax.naming.AuthenticationNotSupportedException: 
        // [LDAP: error code 8 - modifications require authentication]
        // Awaiting reply from mailing lists.

		//String ldapurl = props.getProperty("ldapurl", "ldap://someldapserver.com:389");
		//String basedn = props.getProperty("basedn", "dc=arch,dc=org");
			
		// Set up the environment for creating the initial context
		//Hashtable env = new Hashtable(11);
		//env.put(Context.INITIAL_CONTEXT_FACTORY, 
		//    "com.sun.jndi.ldap.LdapCtxFactory");
		//env.put(Context.PROVIDER_URL, ldapurl);

		//env.put(Context.SECURITY_AUTHENTICATION,"simple");
		//env.put(Context.SECURITY_PRINCIPAL,"cn=xxx,dc=arch,dc=org"); // specify the username
		//env.put(Context.SECURITY_CREDENTIALS,"secret");           // specify the password

        try {
			// Create the initial context
			//LdapContext ctx = new InitialLdapContext(env,null);
		    dctx.destroySubcontext(dn);
            return true;
        } catch (NamingException ne) {
    		ne.printStackTrace();
    		return false;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    public boolean updateGroup(Group grp) {
        // for now we are using simple authentication for LDAP since there seems to be the following
        // error with GSSAPI authentication (which works for search) while requesting a modification to LDAP:
        // javax.naming.AuthenticationNotSupportedException: 
        // [LDAP: error code 8 - modifications require authentication]
        // Awaiting reply from mailing lists.

		//String ldapurl = props.getProperty("ldapurl", "ldap://someldapserver.com:389");
		//String basedn = props.getProperty("basedn", "dc=arch,dc=org");
			
		// Set up the environment for creating the initial context
		//Hashtable env = new Hashtable(11);
		//env.put(Context.INITIAL_CONTEXT_FACTORY, 
		//    "com.sun.jndi.ldap.LdapCtxFactory");
		//env.put(Context.PROVIDER_URL, ldapurl);

		//env.put(Context.SECURITY_AUTHENTICATION,"simple");
		//env.put(Context.SECURITY_PRINCIPAL,"cn=xxx,dc=arch,dc=org"); // specify the username
		//env.put(Context.SECURITY_CREDENTIALS,"secret");           // specify the password
		
        try {
			// Create the initial context
			//LdapContext ctx = new InitialLdapContext(env,null);			
		    dctx.modifyAttributes(grp.getDn(), DirContext.REPLACE_ATTRIBUTE, grp.getAttributes());
            return true;
        } catch (NamingException ne) {
    		ne.printStackTrace();
    		return false;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

    class peopleComparator implements Comparator {
		public int compare(Object o0, Object o1) {
			Person p0 = (Person) o0;
			Person p1 = (Person) o1;
			// compare cn first
			int cnComp = p0.getCn().toUpperCase().compareTo(p1.getCn().toUpperCase());
			if (cnComp != 0) {
				return cnComp;
			}
			// if cn same then compare uids
			int uidComp = p0.getUid().toUpperCase().compareTo(p1.getUid().toUpperCase());
			return uidComp;
		}
	}
    
    class roleComparator implements Comparator {
		public int compare(Object o0, Object o1) {
			Role r0 = (Role) o0;
			Role r1 = (Role) o1;
			// compare cn
			int cnComp = r0.getCn().toUpperCase().compareTo(r1.getCn().toUpperCase());
				return cnComp;			
		}
	}
    
    class groupComparator implements Comparator {
		public int compare(Object o0, Object o1) {
			Group g0 = (Group) o0;
			Group g1 = (Group) o1;
			// compare cn
			int cnComp = g0.getCn().toUpperCase().compareTo(g1.getCn().toUpperCase());
				return cnComp;			
		}
	}
}