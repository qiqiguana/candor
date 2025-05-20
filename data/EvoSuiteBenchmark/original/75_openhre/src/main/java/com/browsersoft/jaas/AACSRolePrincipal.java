/*
 *   CVS $Id: AACSRolePrincipal.java,v 1.1 2006/11/06 19:52:19 grodecki Exp $
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

/**
 * <p> This class implements the <code>Principal</code> interface
 * and represents an AACS role.
 *
 * <p> Principals such as this <code>AACSRolePrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon 
 * the Principals associated with a <code>Subject</code>.
 * 
 * @version $Id: AACSRolePrincipal.java,v 1.1 2006/11/06 19:52:19 grodecki Exp $
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
public class AACSRolePrincipal extends AACSPrincipal {

    /**
     * Create a AACRolePrincipal with a name.
     *
     * <p>
     *
     * @param name
     *
     * @exception NullPointerException if the <code>name</code>
     *			is <code>null</code>.
     */
    public AACSRolePrincipal(String name) {
		super(name);
    }
}
