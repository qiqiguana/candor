package com.pmdesigns.jvc.tools;

public class Base64Coder {

    public static char[] encode(byte[] in) {
        return encode(in, in.length);
    }
}
