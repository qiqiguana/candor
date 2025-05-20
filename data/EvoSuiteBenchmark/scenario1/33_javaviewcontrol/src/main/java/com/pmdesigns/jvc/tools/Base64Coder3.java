package com.pmdesigns.jvc.tools;

public class Base64Coder {

    public static String decodeString(String s) {
        return new String(decode(s));
    }
}
