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
 *
 * $Id: Type.java,v 1.1 2004/11/26 01:50:43 tanderson Exp $
 *
 * Date         Author  Changes
 * 02/26/2000   jimm    Created
 */


package org.exolab.jms.message;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;


/**
 * Wrapper for JMSType
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:43 $
 * @author      <a href="mailto:mourikis@exolab.org">Jim Mourikis</a>
 */
public class Type implements Externalizable {

    // Version Id used for streaming
    static final long serialVersionUID = 1;

    private String type_;

    public Type() {

    }

    public Type(String type) {
        type_ = type;
    }

    // Marshall out
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(serialVersionUID);
        out.writeInt(type_.length());
        out.writeChars(type_);
    }


    // Marshall in
    public void readExternal(ObjectInput in)
        throws IOException, ClassNotFoundException {
        long version = in.readLong();
        if (version == serialVersionUID) {
            int len = in.readInt();
            int i;
            StringBuffer buf = new StringBuffer(len);
            for (i = 0; i < len; i++) {
                buf.append(in.readChar());
            }
            type_ = buf.toString();
        } else {
            throw new IOException("Incorrect version enountered: " +
                version + " This version = " +
                serialVersionUID);
        }
    }

    public String getType() {
        return type_;
    }

    // Comparisons
    public boolean isEqual(Type toCompare) {
        return (type_.equals(toCompare.type_));
    }
}

