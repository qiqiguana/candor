package com.pmdesigns.jvc.tools;

public class Base64Coder {

    public static String encodeString(String s) {
        return new String(encode(s.getBytes()));
    }
}
