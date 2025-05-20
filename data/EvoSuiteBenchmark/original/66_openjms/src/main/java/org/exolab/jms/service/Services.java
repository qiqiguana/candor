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
 * $Id: Services.java,v 1.1 2005/08/30 04:56:14 tanderson Exp $
 */

package org.exolab.jms.service;

/**
 * A <code>Serviceable</code> which manages a collection of services.
 * <p/>
 * A service may be any object. If it implements the {@link Serviceable}
 * interface, then its lifecycle will be managed by this.
 * <p/>
 * Services may be registered using their class types, or instances.
 * Only a single instance of a particular service may exist at any time.
 * <p/>
 * Services that are registered using their class types will be created
 * when first accessed via {@link #getService}. This will recursively resolve
 * any other services that the service is dependent on.
 *
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/08/30 04:56:14 $
 */
public interface Services extends Serviceable {

    /**
     * Add a service of the specified type.
     * <p/>
     * The service will be constructed when it is first accessed via
     * {@link #getService}.
     *
     * @param type the type of the service
     * @throws ServiceAlreadyExistsException if the service already exists
     * @throws ServiceException for any service error
     */
    void addService(Class type) throws ServiceException;

    /**
     * Add a service instance.
     *
     * @param service the service instance
     * @throws ServiceAlreadyExistsException if the service already exists
     * @throws ServiceException for any service error
     */
    void addService(Object service) throws ServiceException;

    /**
     * Returns a service given its type.
     * <p/>
     * If the service has been registered but not constructed, it will be
     * created and any setters populated.
     *
     * @param type the type of the service
     * @return an instance of <code>type</code>
     * @throws ServiceDoesNotExistException if the service doesn't exist, or
     * is dependent on a service which doesn't exist
     * @throws ServiceException for any service error
     */
    Object getService(Class type) throws ServiceException;

}
