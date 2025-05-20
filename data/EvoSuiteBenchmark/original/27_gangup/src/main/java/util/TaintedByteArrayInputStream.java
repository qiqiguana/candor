/* $Id: TaintedByteArrayInputStream.java,v 1.2 2004/04/27 19:26:22 bja Exp $ */

package util;

import java.io.ByteArrayInputStream;

public class TaintedByteArrayInputStream extends ByteArrayInputStream {
    public TaintedByteArrayInputStream() { this(new byte[] {}); }
    public TaintedByteArrayInputStream(byte[] buf) { super(buf); }
    /**
     * Sets the internal byte array to the specified byte array. 
     * @param buf the byte array to use.
     */
    public void setByteArray(byte[] buf) { 
	this.buf = buf; 
        this.pos = 0;
	this.count = buf.length;
	this.mark = 0;
    }
}
