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
 * Copyright 2000,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 */

package org.exolab.jms.selector;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;


/**
 * This class implements an identifier. When evaluated, this returns
 * the value of named header identifier or property, or null if the
 * identifier is null or the property does not exist.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         org.exolab.jms.selector.Expression
 * @see         org.exolab.jms.selector.Identifiers
 */
class Identifier implements Expression {

    /**
     * The identifier name
     */
    private final String _name;

    /**
     * If true the identifier is a header identifier, otherwise it is an user
     * identifier
     */
    private final boolean _headerField;

    /**
     * Persistent delivery mode
     */
    private static final SString PERSISTENT =
        new SString(Identifiers.PERSISTENT);

    /**
     * Non-persistent delivery mode
     */
    private static final SString NON_PERSISTENT =
        new SString(Identifiers.NON_PERSISTENT);


    /**
     * Construct a new <code>Identifier</code>
     *
     * @param name the identifier name
     * @throws SelectorException if the identifier cannot be queried by
     * selectors
     */
    public Identifier(final String name) throws SelectorException {
        _name = name;

        if (Identifiers.isJMSIdentifier(_name)) {
            if (!Identifiers.isQueryableJMSIdentifier(_name)) {
                throw new SelectorException("Invalid header field: " + _name);
            }
            _headerField = true;
        } else {
            _headerField = false;
        }
    }

    /**
     * Evaluate the expression
     *
     * @param msg the message to use to obtain any header identifier and
     * property values
     * @return the evaluated result, or <code>null</code> if the value of the
     *  expression is unknown
     */
    public final SObject evaluate(final Message msg) {
        SObject value = null;
        try {
            if (_headerField) {
                if (_name.equals(Identifiers.JMS_DELIVERY_MODE)) {
                    value = deliveryMode(msg.getJMSDeliveryMode());
                } else if (_name.equals(Identifiers.JMS_PRIORITY)) {
                    value = new SLong(msg.getJMSPriority());
                } else if (_name.equals(Identifiers.JMS_TIMESTAMP)) {
                    value = new SLong(msg.getJMSTimestamp());
                } else if (_name.equals(Identifiers.JMS_MESSAGE_ID)) {
                    String id = msg.getJMSMessageID();
                    if (id != null) {
                        value = new SString(id);
                    }
                } else if (_name.equals(Identifiers.JMS_CORRELATION_ID)) {
                    String id = msg.getJMSCorrelationID();
                    if (id != null) {
                        value = new SString(id);
                    }
                } else if (_name.equals(Identifiers.JMS_TYPE)) {
                    String type = msg.getJMSType();
                    if (type != null) {
                        value = new SString(type);
                    }
                }
            } else {
                value = SObjectFactory.create(msg.getObjectProperty(_name));
            }
        } catch (JMSException ignore) {
            // do nothing
        }
        return value;
    }

    /**
     * Return a string representation of this expression.
     *
     * @return a string representation of this expression
     */
    public final String toString() {
        return _name;
    }

    /**
     * Converts the delivery mode to a string
     *
     * @param mode the delivery mode. One of
     * <code>DeliveryMode.PERSISTENT</code> or
     * <code>DeliveryMode.NON_PERSISTENT</code>
     * @return the stringified representation of the delivery mode
     */
    private SString deliveryMode(final int mode) {
        SString result = PERSISTENT;
        if (mode == DeliveryMode.NON_PERSISTENT) {
            result = NON_PERSISTENT;
        }
        return result;
    }

}
