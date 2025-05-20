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
 * Copyright 2000-2001,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 */

package org.exolab.jms.selector;


/**
 * Utility methods for JMS message header identifiers
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public final class Identifiers {

    /**
     * Prefix for identifiers available via message methods
     */
    public static final String JMS_PREFIX = "JMS";

    /**
     * Prefix for identifiers specified by the JMS standard
     */
    public static final String JMSX_PREFIX = "JMSX";

    /**
     * Prefix for provider specific identifiers
     */
    public static final String JMS_PROVIDER_PREFIX = "JMS_";

    /**
     * The JMSDeliveryMode identifier
     */
    public static final String JMS_DELIVERY_MODE = "JMSDeliveryMode";

    /**
     * The JMSPriority identifier
     */
    public static final String JMS_PRIORITY = "JMSPriority";

    /**
     * The JMSMessageID identifier
     */
    public static final String JMS_MESSAGE_ID = "JMSMessageID";

    /**
     * The JMSTimestamp identifier
     */
    public static final String JMS_TIMESTAMP = "JMSTimestamp";

    /**
     * The JMSCorrelationID identifier
     */
    public static final String JMS_CORRELATION_ID = "JMSCorrelationID";

    /**
     * The JMSType identifier
     */
    public static final String JMS_TYPE = "JMSType";

    /**
     * The persistent delivery mode
     */
    public static final String PERSISTENT = "PERSISTENT";

    /**
     * The non-persistent delivery mode
     */
    public static final String NON_PERSISTENT = "NON_PERSISTENT";


    /**
     * Private constructor
     */
    private Identifiers() {
    }

    /**
     * Determines if an identifier is a JMS identifier (not a JMSX or
     * provider specific identifier)
     *
     * @param name the identifier name
     * @return <code>true</code> if the identifier is a JMS identifier,
     * otherwise <code>false</code>
     */
    public static boolean isJMSIdentifier(final String name) {
        boolean result = false;
        if (name.startsWith(JMS_PREFIX)
            && !(name.startsWith(JMSX_PREFIX)
                 || name.startsWith(JMS_PROVIDER_PREFIX))) {
            result = true;
        }
        return result;
    }

    /**
     * Determines if a JMS identifier is valid in selectors
     *
     * @param name the identifier name
     * @return <code>true</code> if the identifier may be selected upon,
     * otherwise <code>false</code>
     */
    public static boolean isQueryableJMSIdentifier(final String name) {
        return (name.equals(JMS_DELIVERY_MODE)
                || name.equals(JMS_PRIORITY)
                || name.equals(JMS_TIMESTAMP)
                || name.equals(JMS_MESSAGE_ID)
                || name.equals(JMS_CORRELATION_ID)
                || name.equals(JMS_TYPE));
    }

    /**
     * Determines if a JMS identifier is a string
     *
     * @param name the identifier name
     * @return <code>true</code> if the identifier is a string, otherwise
     * <code>false</code>
     */
    public static boolean isString(final String name) {
        return (name.equals(JMS_MESSAGE_ID)
                || name.equals(JMS_CORRELATION_ID)
                || name.equals(JMS_TYPE)
                || name.equals(JMS_DELIVERY_MODE));
        // delivery mode is an integer which must be mapped to a string
        // (see '3.8.1.3 Special Notes' of the spec).
    }

    /**
     * Determines if a JMS identifier is numeric
     *
     * @param name the identifier name
     * @return <code>true</code> if the identifier is numeric, otherwise
     * <code>false</code>
     */
    public static boolean isNumeric(final String name) {
        return (name.equals(JMS_PRIORITY)
                || name.equals(JMS_TIMESTAMP));
    }

} //-- Identifiers
