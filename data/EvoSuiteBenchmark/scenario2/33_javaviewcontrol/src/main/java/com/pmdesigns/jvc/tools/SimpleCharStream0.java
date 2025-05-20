/* JavaCCOptions:STATIC=false,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.pmdesigns.jvc.tools;

public class SimpleCharStream {

    /**
     * Start.
     */
    public char BeginToken() throws java.io.IOException {
        tokenBegin = -1;
        char c = readChar();
        tokenBegin = bufpos;
        return c;
    }
}
