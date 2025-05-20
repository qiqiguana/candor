package com.pmdesigns.jvc.tools;

public class Base64Coder {

    /**
     * Encodes a byte array into Base64 format.
     * No blanks or line breaks are inserted.
     *
     * @param in an array containing the data bytes to be encoded.
     * @param iLen number of bytes to process in <code>in</code>.
     * @return A character array with the Base64 encoded data.
     */
    public static char[] encode(byte[] in, int iLen);
}
