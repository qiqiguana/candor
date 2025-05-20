package com.pmdesigns.jvc.tools;

public class Base64Coder {

    public static byte[] decode(String s) {
        return decode(s.toCharArray());
    }
}
