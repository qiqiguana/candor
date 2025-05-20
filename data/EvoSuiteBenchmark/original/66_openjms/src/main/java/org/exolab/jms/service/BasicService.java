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
 * Copyright 1999-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: BasicService.java,v 1.2 2005/08/30 04:56:14 tanderson Exp $
 */

package org.exolab.jms.service;




/**
 * <code>BasicService</code> is a service implementation that will run the
 * service in a separate thread. Derived class must define an implementation for
 * the 'run' method.
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.2 $ $Date: 2005/08/30 04:56:14 $
 * @see Service
 */
public abstract class BasicService extends Service implements Runnable {

    /**
     * The service thread.
     */
    private Thread _thread = null;


    /**
     * Construct a new <code>BasicService</code> with no name.
     */
    public BasicService() {
    }

    /**
     * Construct a new <code>BasicService</code>, specifying its name.
     *
     * @param name the name of the service
     */
    public BasicService(String name) {
        super(name);
    }

    /**
     * Return the state of the object as a string.
     *
     * @return a string form of the object state
     */
    public String toString() {
        StringBuffer buf = new StringBuffer("BasicService:[");
        buf.append("name=");
        buf.append(getName());
        buf.append("thread=");
        buf.append(_thread);
        buf.append("started=");
        buf.append(isStarted());
        buf.append("]");
        return buf.toString();
    }

    /**
     * Start the service.
     */
    protected void doStart() throws ServiceException {
        _thread = new Thread(this, getName());
        _thread.start();
    }

    /**
     * Stop the service.
     */
    protected void doStop() throws ServiceException {
        _thread.interrupt();
        try {
            _thread.join();
        } catch (InterruptedException ignore) {
        } finally {
            _thread = null;
        }
    }

}
