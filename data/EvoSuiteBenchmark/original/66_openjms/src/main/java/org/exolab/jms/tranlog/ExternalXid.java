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
 * $Id: ExternalXid.java,v 1.1 2004/11/26 01:51:01 tanderson Exp $
 *
 * Date			Author  Changes
 * 24/01/2002   jima    Created
 */
package org.exolab.jms.tranlog;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javax.transaction.xa.Xid;


/**
 * This class maps an external XID that is set in the transaction manager.
 * It needs to do this so that it can use it internally.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:01 $
 * @author      <a href="mailto:jima@exoffice.com">Jim Alateras</a>
 * @see         javax.transaction.xa.Xid
 */
public final class ExternalXid
    implements Xid, Externalizable {

    /**
     * This is the unique id used to identify the version of the class
     * for the purpose of Serialization
     */
    static final long serialVersionUID = 1;

    /**
     * Efficient mapping from 4 bit value to lower case hexadecimal digit.
     * gobbled from Tyrex imple
     */
    protected final static char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7',
                                                          '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * The format identifier of the distributed transaction identifier
     */
    private int _formatId;

    /**
     * The array of bytes corresponding to the global transaction identifier
     * part of the XID.
     */
    private byte[] _global;

    /**
     * The array of bytes corresponding to the branch qualifier
     * part of the XID.
     */
    private byte[] _branch;

    /**
     * Cache a trnasient instance of the stringified version of the xid
     */
    private transient String _string = null;


    /**
     * Default constructor for Serialization
     */
    public ExternalXid() {
    }

    /**
     * Create an instance of this class using the specified XID. This
     * will always create a new instance and copy the format id, global
     * transaction id and branch qualifier id to the new instance
     *
     * @param xid - the xid to use
     */
    public ExternalXid(Xid xid) {
        this(xid.getFormatId(), xid.getGlobalTransactionId(),
            xid.getBranchQualifier());
    }

    /**
     * Create an insrance of this class using the specified format id,
     * global transaction id and the branch qualifier.
     *
     * @param formatId - the format identifier
     * @param global - the global transaction identifier
     * @param branch - the branch qualifier
     */
    public ExternalXid(int formatId, byte[] global, byte[] branch) {
        _formatId = formatId;

        // assign the global transaction identifier
        if ((global == null) ||
            (global.length == 0)) {
            _global = new byte[0];
        } else {
            _global = new byte[global.length];
            System.arraycopy(global, 0, _global, 0, global.length);
        }

        // assign the branch qualifier
        if ((branch == null) ||
            (branch.length == 0)) {
            _branch = new byte[0];
        } else {
            _branch = new byte[branch.length];
            System.arraycopy(branch, 0, _branch, 0, branch.length);
        }
    }

    // implementation of Xid.getFormatId
    public int getFormatId() {
        return _formatId;
    }

    // implementation of Xid.getFormatId
    public byte[] getGlobalTransactionId() {
        return _global;
    }

    // implementation of Xid.getFormatId
    public byte[] getBranchQualifier() {
        return _branch;
    }

    /**
     * Returns the global transaction identifier in the form of
     * exid://formatId.global.branch
     *
     * @return String
     */
    public String toString() {

        if (_string == null) {
            StringBuffer buffer = new StringBuffer("xid://");
            buffer.append(HEX_DIGITS[(int) ((_formatId >> 28) & 0x0F)]);
            buffer.append(HEX_DIGITS[(int) ((_formatId >> 24) & 0x0F)]);
            buffer.append(HEX_DIGITS[(int) ((_formatId >> 20) & 0x0F)]);
            buffer.append(HEX_DIGITS[(int) ((_formatId >> 16) & 0x0F)]);
            buffer.append(HEX_DIGITS[(int) ((_formatId >> 12) & 0x0F)]);
            buffer.append(HEX_DIGITS[(int) ((_formatId >> 8) & 0x0F)]);
            buffer.append(HEX_DIGITS[(int) ((_formatId >> 4) & 0x0F)]);
            buffer.append(HEX_DIGITS[(int) (_formatId & 0x0F)]);
            buffer.append('-');
            if (_global != null && _global.length > 0) {
                for (int i = _global.length; i-- > 0;) {
                    buffer.append(HEX_DIGITS[(_global[i] & 0xF0) >> 4]);
                    buffer.append(HEX_DIGITS[(_global[i] & 0x0F)]);
                }
            }
            buffer.append('-');
            if (_branch != null && _branch.length > 0) {
                for (int i = _branch.length; i-- > 0;) {
                    buffer.append(HEX_DIGITS[(_branch[i] & 0xF0) >> 4]);
                    buffer.append(HEX_DIGITS[(_branch[i] & 0x0F)]);
                }
            }
            _string = buffer.toString();
        }

        return _string;
    }

    // override implementation of Object.hashCode
    public int hashCode() {
        return toString().hashCode();
    }

    // override implementation of Object.equals
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (obj instanceof ExternalXid) {

            Xid xid = (Xid) obj;
            // compare format ids
            if (xid.getFormatId() != _formatId) {
                return false;
            }

            // compare global transaction id
            byte[] global = xid.getGlobalTransactionId();
            if ((global == null) ||
                (global.length == 0)) {
                if ((_global != null) ||
                    (_global.length != 0)) {
                    return false;
                }
            } else {
                if (global.length != _global.length) {
                    return false;
                }

                for (int index = 0; index < global.length; index++) {
                    if (global[index] != _global[index]) {
                        return false;
                    }
                }
            }

            // compare branch qualifier
            byte[] branch = xid.getBranchQualifier();
            if ((branch == null) ||
                (branch.length == 0)) {
                if ((_branch != null) ||
                    (_branch.length != 0)) {
                    return false;
                }
            } else {
                if (branch.length != _branch.length) {
                    return false;
                }

                for (int index = 0; index < branch.length; index++) {
                    if (branch[index] != _branch[index]) {
                        return false;
                    }
                }
            }
            return true;
        }

        return false;
    }

    // implementation of Externalizable.writeExternal
    public void writeExternal(ObjectOutput stream)
        throws IOException {
        stream.writeLong(serialVersionUID);
        stream.writeInt(_formatId);

        stream.writeInt(_global.length);
        stream.write(_global);

        stream.writeInt(_branch.length);
        stream.write(_branch);

    }

    // implementation of Externalizable.writeExternal
    public void readExternal(ObjectInput stream)
        throws IOException, ClassNotFoundException {
        long version = stream.readLong();

        if (version == serialVersionUID) {
            _formatId = stream.readInt();

            _global = new byte[stream.readInt()];
            stream.read(_global);

            _branch = new byte[stream.readInt()];
            stream.read(_branch);

        } else {
            throw new IOException("No support for ExternalXid " +
                "with version " + version);
        }
    }
}
