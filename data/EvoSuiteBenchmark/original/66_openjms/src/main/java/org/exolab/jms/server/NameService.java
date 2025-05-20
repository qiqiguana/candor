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
 * Copyright 2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: NameService.java,v 1.1 2005/08/30 05:41:44 tanderson Exp $
 */
package org.exolab.jms.server;

import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;

import org.codehaus.spice.jndikit.NamingProvider;

import org.exolab.jms.config.Configuration;
import org.exolab.jms.config.Property;


/**
 * Enter description here.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/08/30 05:41:44 $
 */
public class NameService {

    /**
     * The configuration.
     */
    private Configuration _config;

    /**
     * The embedded name service. Null if an external name service is
     * configured.
     */
    private EmbeddedNameService _embedded;

    /**
     * The environment for connecting to the external name service.
     * Null if the embedded name service is configured.
     */
    private Hashtable _environment;


    /**
     * Construct a new <code>NameService</code>.
     *
     * @param config the configuration to use
     */
    public NameService(Configuration config) {
        if (config == null) {
            throw new IllegalArgumentException("Argument 'config' is null");
        }
        _config = config;
        // initialise the embedded JNDI server if required
        if (_config.getServerConfiguration().getEmbeddedJNDI()) {
            _embedded = new EmbeddedNameService();
        } else {
            _environment = new Hashtable();
            Property[] properties =
                config.getJndiConfiguration().getProperty();
            for (int index = 0; index < properties.length; ++index) {
                _environment.put(properties[index].getName(),
                    properties[index].getValue());
            }
        }
    }

    /**
     * Return the initial context. <br/>
     * If an embedded JNDI service is configured, then return its initial
     * context, else return the initial context of the external JNDI provider.
     *
     * @return the initial context
     * @throws NamingException if a naming error occurs
     */
    public Context getInitialContext() throws NamingException {
        Context initial = null;

        if (_embedded != null) {
            initial = _embedded.getInitialContext();
        } else {
            initial = new InitialContext(_environment);
        }
        return initial;
    }

    /**
     * Returns the embedded naming provider.
     *
     * @return the embedded naming provider, or <code>null</code> if an
     * external provider is configureed
     */
    public NamingProvider getNamingProvider() {
        return (_embedded != null) ? _embedded.getNamingProvider() : null; 
    }

}
