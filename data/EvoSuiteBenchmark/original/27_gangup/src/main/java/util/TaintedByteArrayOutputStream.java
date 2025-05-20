/* $Id: TaintedByteArrayOutputStream.java,v 1.2 2004/04/27 19:26:22 bja Exp $ */

package util;

import java.io.ByteArrayOutputStream;

public class TaintedByteArrayOutputStream extends ByteArrayOutputStream {
    public TaintedByteArrayOutputStream() { }
    public TaintedByteArrayOutputStream(int capacity) {	super(capacity); }
    /**
     * Returns a reference to the internal byte array. It's not a good idea 
     * to modify the returned array unless you know what you're doing. 
     * @return the internal byte array, very dangerous!
     */
    public byte[] getByteArray() { return buf; }
}
