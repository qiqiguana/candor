/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2000-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 */
package org.exolab.jms.messagemgr;

import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

import org.exolab.jms.client.JmsDestination;


/**
 * This class provides helper methods to register destinations in JNDI
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:43 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
class ContextHelper {

    /**
     * Binds a name to an object, overwriting any existing binding.
     * All intermediate contexts and the target context
     * (that named by all but the terminal atomic component of the name)
     * are created if they don't already exist.
     *
     * @param       context             the context to rebind to
     * @param       name                a destination name,
     *                                  possibly containing one or more '.'
     * @param       destination         the destination to bind
     * @throws      NamingException
     */
    public static void rebind(Context context, String name,
                              JmsDestination destination)
        throws NamingException {
        CompositeName composite = getCompositeName(name);
        Context subcontext = context;
        String component = null;

        for (int i = 0; i < composite.size() - 1; i++) {
            component = composite.get(i);
            if (component.length() == 0) {
                throw new InvalidNameException("'" + name
                                               + "' is not a valid name");
            }
            Object object = null;
            try {
                object = subcontext.lookup(component);
                if (!(object instanceof Context)) {
                    String subname = "";
                    for (int j = 0; j <= i; j++) {
                        if (j > 0) {
                            subname += ".";
                        }
                        subname += composite.get(j);
                    }
                    throw new NameAlreadyBoundException(
                        "'" + subname + "' is already bound");
                } else {
                    subcontext = (Context) object;
                }
            } catch (NameNotFoundException exception) {
                subcontext = subcontext.createSubcontext(component);
            }
        }

        component = composite.get(composite.size() - 1);
        if (component.length() == 0) {
            throw new InvalidNameException("'" + name
                                           + "' is not a valid name");
        }

        try {
            Object object = subcontext.lookup(component);
            if (object instanceof Context) {
                throw new NameAlreadyBoundException("'" + name +
                                                    "' is already bound");
            }
        } catch (NameNotFoundException ignore) {
        } catch (NamingException exception) {
        }

        subcontext.rebind(component, destination);
    }

    /**
     * Unbinds the named object. Removes the terminal atomic name in name
     * from the target context - that named by all but the terminal atomic
     * part of name.
     *
     * @param       context             the context to unbind from
     * @param       name                a destination name, possibly
     *                                  containing one or more '.'
     * @throws      NamingException
     */
    public static void unbind(Context context, String name)
        throws NamingException {
        try {
            Object object = context.lookup(name);
            if (!(object instanceof JmsDestination)) {
                throw new NamingException(
                    "Cannot unbind name='" + name
                    + "': it does not refer to a Destination");
            }
            context.unbind(name);
        } catch (NameNotFoundException ignore) {
        }
    }

    private static CompositeName getCompositeName(String name)
        throws NamingException {
        // Need to replace the dots in destination names with a '/' in
        // order for it to be parsed by CompositeName
        CompositeName composite = new CompositeName(name.replace('.', '/'));
        if (composite.size() == 0) {
            throw new InvalidNameException("'" + name
                                           + "' is not a valid name");
        }
        return composite;
    }
}
