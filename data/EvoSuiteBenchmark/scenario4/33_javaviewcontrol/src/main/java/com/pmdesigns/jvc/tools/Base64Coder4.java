package com.pmdesigns.jvc.tools;

public class Base64Coder {

    // Mapping table from 6-bit nibbles to Base64 characters.
    private static char[] map1 = new char[64];

    static {
    }

    // Mapping table from Base64 characters to 6-bit nibbles.
    private static byte[] map2 = new byte[128];

    static {
    }

    /**
     * Encodes a string into Base64 format.
     * No blanks or line breaks are inserted.
     * @param s  a String to be encoded.
     * @return   A String with the Base64 encoded data.
     */
    public static String encodeString(String s);

    /**
     * Encodes a byte array into Base64 format.
     * No blanks or line breaks are inserted.
     * @param in  an array containing the data bytes to be encoded.
     * @return    A character array with the Base64 encoded data.
     */
    public static char[] encode(byte[] in);

    /**
     * Encodes a byte array into Base64 format.
     * No blanks or line breaks are inserted.
     * @param in   an array containing the data bytes to be encoded.
     * @param iLen number of bytes to process in <code>in</code>.
     * @return     A character array with the Base64 encoded data.
     */
    public static char[] encode(byte[] in, int iLen);

    /**
     * Decodes a string from Base64 format.
     * @param s  a Base64 String to be decoded.
     * @return   A String containing the decoded data.
     * @throws   IllegalArgumentException if the input is not valid Base64 encoded data.
     */
    public static String decodeString(String s);

    /**
     * Decodes a byte array from Base64 format.
     * @param s  a Base64 String to be decoded.
     * @return   An array containing the decoded data bytes.
     * @throws   IllegalArgumentException if the input is not valid Base64 encoded data.
     */
    public static byte[] decode(String s);

    /**
     * Decodes a byte array from Base64 format.
     * No blanks or line breaks are allowed within the Base64 encoded data.
     * @param in  a character array containing the Base64 encoded data.
     * @return    An array containing the decoded data bytes.
     * @throws    IllegalArgumentException if the input is not valid Base64 encoded data.
     */
    public static byte[] decode(char[] in);

    // Dummy constructor.
    private Base64Coder() {
    }
}
