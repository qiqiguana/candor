package com.pmdesigns.jvc.tools;

public class Base64Coder {

    /**
     * Decodes a string from Base64 format.
     *
     * @param s a Base64 String to be decoded.
     * @return A String containing the decoded data.
     * @throws IllegalArgumentException if the input is not valid Base64 encoded data.
     */
    public static String decodeString(String s) {
        return new String(decode(s));
    }

    /**
     * Decodes a byte array from Base64 format.
     * @param s  a Base64 String to be decoded.
     * @return   An array containing the decoded data bytes.
     * @throws   IllegalArgumentException if the input is not valid Base64 encoded data.
     */
    public static byte[] decode(String s);
}
