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
 * Copyright 2002-2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 *
 * $Id: StateTransactionLogEntry.java,v 1.1 2004/11/26 01:51:01 tanderson Exp $
 *
 * Date			Author  Changes
 * 20/11/2001   jima    Created
 */
package org.exolab.jms.tranlog;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


public class StateTransactionLogEntry
    extends BaseTransactionLogEntry
    implements Externalizable {

    /**
     * This is the unique id used to identify the version of the class
     * for the purpose of Serialization
     */
    static final long serialVersionUID = 1;

    /**
     * State of the tranaction, which is identified in the base class
     */
    private TransactionState _state;


    /**
     * Default constuctor for serialization
     */
    public StateTransactionLogEntry() {
    }

    /**
     * Create an instance of this object for the specified transaction
     * identifier and resource identifier.
     * <p>
     * Mark as the transaction being created now
     *
     * @param txid - transaction identifier
     * @param rid - resource identifier
     */
    StateTransactionLogEntry(ExternalXid txid, String rid) {
        this(txid, rid, System.currentTimeMillis());
    }

    /**
     * Create an instance of this object for the specified transaction
     * identifier and resource identifier
     * <p>
     * Mark the transaction as being created at the specified time
     *
     * @param txid - the transaction identifier
     * @param rid - the resource identifier
     * @param created - a timestamp for this transaction
     */
    StateTransactionLogEntry(ExternalXid txid, String rid, long created) {
        super(txid, rid, created);
    }

    /**
     * Set the transaction state
     *
     * @param state
     */
    public void setState(TransactionState state) {
        _state = state;
    }

    /**
     * Get the transaction state
     *
     * @return TransactionState
     */
    public TransactionState getState() {
        return _state;
    }

    // implementation of Externalizable.writeExternal
    public void writeExternal(ObjectOutput stream)
        throws IOException {
        stream.writeLong(serialVersionUID);
        stream.writeObject(_state);
        super.writeExternal(stream);
    }

    // implementation of Externalizable.writeExternal
    public void readExternal(ObjectInput stream)
        throws IOException, ClassNotFoundException {
        long version = stream.readLong();
        if (version == serialVersionUID) {
            _state = (TransactionState) stream.readObject();
            super.readExternal(stream);
        } else {
            throw new IOException("No support for StateTransactionLogEntry " +
                "with version " + version);
        }
    }
}
