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
 * $Id: BaseTransactionLogEntry.java,v 1.1 2004/11/26 01:51:01 tanderson Exp $
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
 * This is the base entry that is streamed into the transaction log file.
 * All other transaction log entry classes must extend this class.
 * <p>
 * Each entry has the associated XID and the created time stamp, along with
 * the identity of the resource that created the entry
 */
public abstract class BaseTransactionLogEntry implements Externalizable {

    /**
     * This is the unique id used to identify the version of the class
     * for the purpose of Serialization
     */
    static final long serialVersionUID = 2;

    /**
     * This is the transaction identity that this entry belongs too
     */
    private ExternalXid _externalXid = null;

    /**
     * This is the resource identity that this entry belongs too
     */
    private String _resourceId;

    /**
     * This is a time stamp when the entry was created
     */
    private long _created = -1;

    /**
     * This is the time that the transaction expires in ms. It is initialized
     * to zero, which means that it never expired
     */
    private long _expiryTime = 0;


    /**
     * Default constructor for serialization
     */
    BaseTransactionLogEntry() {
    }

    /**
     * Instantiate an instance of  this class with a transaction identifier
     * resource identifier and created date
     *
     * @param txid - the transaction identifier
     * @param rid - the resource identifier
     * @param created - timestamp for this entry
     */
    BaseTransactionLogEntry(ExternalXid txid, String rid, long created) {
        _externalXid = txid;
        _resourceId = rid;
        _created = created;
    }

    /**
     * Create a new instance populating it with the state of the specified
     * object
     *
     * @param copy - object to copy
     */
    BaseTransactionLogEntry(BaseTransactionLogEntry copy) {
        _externalXid = new ExternalXid(copy._externalXid);
        _resourceId = copy._resourceId;
        _created = copy._created;
        _expiryTime = copy._expiryTime;
    }

    /**
     * Set the transaction identifier
     *
     * @param txid - the transaction identifier
     */
    public void setExternalXid(ExternalXid txid) {
        _externalXid = txid;
    }

    /**
     * Get the transaction identifier
     *
     * @return ExternalXid
     */
    public ExternalXid getExternalXid() {
        return _externalXid;
    }

    /**
     * Set the resource identity for the entry
     *
     * @param rid - the resource identity
     */
    public void setResourceId(String rid) {
        _resourceId = rid;
    }

    /**
     * Get the resource identifier
     *
     * @return the resource identifier
     */
    public String getResourceId() {
        return _resourceId;
    }

    /**
     * Set the time in ms that this record was created
     *
     * @param time - time in ms
     */
    public void setCreated(long time) {
        _created = time;
    }

    /**
     * Set the created time of this entry to now
     */
    public void setCreated() {
        _created = System.currentTimeMillis();
    }

    /**
     * Return the time that this entry was created
     *
     * @return long
     */
    public long getCreated() {
        return _created;
    }


    /**
     * Set the expiry time for this transaction, which is an absolute time in
     * milliseconds.
     *
     * @param long - absolute expiry time
     */
    public void setExpiryTime(long time) {
        _expiryTime = time;
    }

    /**
     * Retrieve the expiry time of this transaction
     *
     * @return long
     */
    public long getExpiryTime() {
        return _expiryTime;
    }

    /**
     * Check whether the trnasaction has expired
     *
     * @return boolean - true if it has expired; false otherwise
     */
    public boolean transactionExpired() {
        return System.currentTimeMillis() > _expiryTime;
    }

    // implementation of Externalizable.writeExternal
    public void writeExternal(ObjectOutput stream)
        throws IOException {
        if (isValid()) {
            stream.writeLong(serialVersionUID);
            stream.writeObject(_externalXid);
            stream.writeUTF(_resourceId);
            stream.writeLong(_created);
        } else {
            throw new IOException("writeExternal : entry has invalid state");
        }
    }

    // implementation of Externalizable.writeExternal
    public void readExternal(ObjectInput stream)
        throws IOException, ClassNotFoundException {
        long version = stream.readLong();
        if (version == serialVersionUID) {
            _externalXid = (ExternalXid) stream.readObject();
            _resourceId = stream.readUTF();
            _created = stream.readLong();
            if (!isValid()) {
                throw new IOException("readExternal : entry has invalid state");
            }
        } else {
            throw new IOException("No support for BaseTransactionLogEntry " +
                "with version " + version);
        }
    }

    /**
     * Verify that this record has a valid state, which is denoted by
     * _externalXid , resourceId and _created being not equal to -1.
     *
     * @return boolean - true if the entry is valid
     */
    boolean isValid() {
        return ((_externalXid != null) &&
            (_resourceId != null) &&
            (_created != -1));
    }

}

