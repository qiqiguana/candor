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
 * Copyright 2000-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: JmsConnectionMetaData.java,v 1.2 2005/03/18 03:36:37 tanderson Exp $
 */
package org.exolab.jms.client;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;
import javax.jms.ConnectionMetaData;

import org.exolab.jms.util.Version;


/**
 * This class maintains static connection meta data information
 *
 * @author <a href="mailto:jima@comware.com.au">Jim Alateras</a>
 * @version $Revision: 1.2 $ $Date: 2005/03/18 03:36:37 $
 */
public class JmsConnectionMetaData implements ConnectionMetaData {

    /**
     * The property names supported by OpenJMS
     */
    private static final Vector PROPERTY_NAMES = new Vector(Arrays.asList(
            new String[]{"JMSXGroupID", "JMSXGroupSeq", "JMSXRcvTimestamp"}));

    /**
     * Returns the JMS API version.
     *
     * @return the JMS API version
     */
    public String getJMSVersion() {
        StringBuffer result = new StringBuffer(getJMSMajorVersion());
        result.append(".");
        result.append(getJMSMinorVersion());
        return result.toString();
    }

    /**
     * Returns the JMS major version number.
     *
     * @return the JMS API major version number
     */
    public int getJMSMajorVersion() {
        return 1;
    }

    /**
     * Returns the JMS minor version number.
     *
     * @return the JMS API minor version number
     */
    public int getJMSMinorVersion() {
        return 1;
    }

    /**
     * Returns the JMS provider name.
     *
     * @return the JMS provider name
     */
    public String getJMSProviderName() {
        return Version.TITLE;
    }

    /**
     * Returns the JMS provider version.
     *
     * @return the JMS provider version
     */
    public String getProviderVersion() {
        return Version.VERSION;
    }

    /**
     * Gets the JMS provider major version number.
     *
     * @return the JMS provider major version number
     */
    public int getProviderMajorVersion() {
        return Version.MAJOR_VERSION;
    }

    /**
     * Returns the JMS provider minor version number.
     *
     * @return the JMS provider minor version number
     */
    public int getProviderMinorVersion() {
        return Version.MINOR_VERSION;
    }

    /**
     * Returns an enumeration of the JMSX property names.
     *
     * @return an Enumeration of JMSX property names
     */
    public Enumeration getJMSXPropertyNames() {
        return PROPERTY_NAMES.elements();
    }

}
