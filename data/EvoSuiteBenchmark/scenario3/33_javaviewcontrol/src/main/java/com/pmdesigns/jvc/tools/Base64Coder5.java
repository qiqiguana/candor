package com.pmdesigns.jvc.tools;

public class Base64Coder {

    /**
     * Decodes a byte array from Base64 format.
     * No blanks or line breaks are allowed within the Base64 encoded data.
     *
     * @param in a character array containing the Base64 encoded data.
     * @return An array containing the decoded data bytes.
     * @throws IllegalArgumentException if the input is not valid Base64 encoded data.
     */
    public static byte[] decode(char[] in);
}
