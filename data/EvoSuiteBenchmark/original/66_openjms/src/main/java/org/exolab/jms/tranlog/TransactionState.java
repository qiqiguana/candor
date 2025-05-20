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
 * Copyright 2001 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 *
 * $Id: TransactionState.java,v 1.1 2004/11/26 01:51:01 tanderson Exp $
 *
 * Date			Author  Changes
 * 20/11/2001   jima    Created
 */
package org.exolab.jms.tranlog;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


/**
 * This class defines the various states of a transaction that a
 * {@link org.exolab.jms.messagemgr.ResourceManager} participates in..
 *
 * Opened       transaction is in an open state
 * Prepared     transaction is in a prepared state
 * Closed       transaction is in a closed state
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:01 $
 * @author      <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 **/
public final class TransactionState
    implements Externalizable {

    /**
     * This is the unique id used to identify the version of the class
     * for the purpose of Serialization
     */
    static final long serialVersionUID = 1;

    /**
     * The state of the transaction as a string.
     */
    private String _state;

    /**
     * The state of the transaction as an integer
     */
    private int _ord;


    /**
     * Default constructor for Serialization
     */
    public TransactionState() {
    }

    /**
     * Limit the creation scope of this object. All instances of this
     * class are created at class initialisation time.
     *
     * @param state - the state as a string
     * @param ord - the ordinal value of the state
     */
    private TransactionState(String state, int ord) {
        _state = state;
        _ord = ord;
    }

    /**
     * Returns the ordinal value for this state
     *
     * @return      int
     */
    public int getOrd() {
        return _ord;
    }

    /**
     * Returns the name of this state
     *
     * @return       String
     */
    public String toString() {
        return _state;
    }

    /**
     * Return true iff the two specified object are of the same type
     * and their ordinal values are identical.
     *
     * @param       obj         object to compare against
     * @return      boolean     true if objects are equivalent
     */
    public boolean equals(Object obj) {
        boolean result = false;

        if ((obj instanceof TransactionState) &&
            ((TransactionState) obj).getOrd() == getOrd()) {
            result = true;
        }

        return result;
    }

    /**
     * Check if the transaction state is set to opened
     *
     * @return boolean - ture if it is
     */
    public boolean isOpened() {
        return _ord == OPENED.getOrd();
    }

    /**
     * Check if the transaction state is set to prepared
     *
     * @return boolean - ture if it is
     */
    public boolean isPrepared() {
        return _ord == PREPARED.getOrd();
    }

    /**
     * Check if the transaction state is set to closed
     *
     * @return boolean - ture if it is
     */
    public boolean isClosed() {
        return _ord == CLOSED.getOrd();
    }


    // implementation of Externalizable.writeExternal
    public void writeExternal(ObjectOutput stream)
        throws IOException {
        stream.writeLong(serialVersionUID);
        stream.writeObject(_state);
        stream.writeInt(_ord);
    }

    // implementation of Externalizable.writeExternal
    public void readExternal(ObjectInput stream)
        throws IOException, ClassNotFoundException {
        long version = stream.readLong();
        if (version == serialVersionUID) {
            _state = (String) stream.readObject();
            _ord = stream.readInt();
        } else {
            throw new IOException("No support for TransactionState " +
                "with version " + version);
        }
    }

    /**
     * The public ordinal values for each of the enumerates states
     */
    public static final int OPENED_ORD = 1;
    public static final int PREPARED_ORD = 2;
    public static final int CLOSED_ORD = 3;

    /**
     * Instatiate instances related to each of the enumerations. New enumerations
     * must be added to the end
     */
    public static final TransactionState OPENED = new TransactionState("opened", OPENED_ORD);
    public static final TransactionState PREPARED = new TransactionState("prepared", PREPARED_ORD);
    public static final TransactionState CLOSED = new TransactionState("closed", CLOSED_ORD);
}
