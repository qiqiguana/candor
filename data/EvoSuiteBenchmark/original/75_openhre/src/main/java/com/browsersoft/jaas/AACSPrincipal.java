/*
 *   CVS $Id: AACSPrincipal.java,v 1.1 2006/11/06 19:52:20 grodecki Exp $
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
package com.browsersoft.jaas;

import java.security.Principal;

/**
 * <p> This class implements the <code>Principal</code> interface
 * and represents a AACS user or role.
 *
 * <p> Principals such as this <code>AACSPrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon 
 * the Principals associated with a <code>Subject</code>.
 * 
 * @version $Id: AACSPrincipal.java,v 1.1 2006/11/06 19:52:20 grodecki Exp $
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
public class AACSPrincipal implements Principal, java.io.Serializable {

    /**
     * @serial
     */
    private String name;

    /**
     * Create a AACSPrincipal with a name.
     *
     * <p>
     *
     * @param name
     *
     * @exception NullPointerException if the <code>name</code>
     *			is <code>null</code>.
     */
    public AACSPrincipal(String name) {
	if (name == null)
	    throw new NullPointerException("illegal null input");

	this.name = name;
    }

    /**
     * Return the name for this <code>AACSPrincipal</code>.
     *
     * <p>
     *
     * @return the name for this <code>AACSPrincipal</code>
     */
    public String getName() {
	return name;
    }

    /**
     * Return a string representation of this <code>AACSPrincipal</code>.
     *
     * <p>
     *
     * @return a string representation of this <code>AACSPrincipal</code>.
     */
    public String toString() {
	return("AACSPrincipal:  " + name);
    }

    /**
     * Compares the specified Object with this <code>AACSPrincipal</code>
     * for equality.  Returns true if the given object is also a
     * <code>AACSPrincipal</code> and the two AACSPrincipals
     * have the same name.
     *
     * <p>
     *
     * @param o Object to be compared for equality with this
     *		<code>AACSPrincipal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *		<code>AACSPrincipal</code>.
     */
    public boolean equals(Object o) {
	if (o == null)
	    return false;

        if (this == o)
            return true;
 
        if (!(o instanceof AACSPrincipal))
            return false;
        AACSPrincipal that = (AACSPrincipal)o;

	if (this.getName().equals(that.getName()))
	    return true;
	return false;
    }
 
    /**
     * Return a hash code for this <code>AACSPrincipal</code>.
     *
     * <p>
     *
     * @return a hash code for this <code>AACSPrincipal</code>.
     */
    public int hashCode() {
	return name.hashCode();
    }
}
