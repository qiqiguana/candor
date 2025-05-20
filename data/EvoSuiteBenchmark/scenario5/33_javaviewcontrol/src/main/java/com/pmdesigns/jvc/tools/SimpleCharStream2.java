/* JavaCCOptions:STATIC=false,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.pmdesigns.jvc.tools;

public class SimpleCharStream {

    /**
     * Get token literal value.
     */
    public String GetImage() {
        if (bufpos >= tokenBegin)
            return new String(buffer, tokenBegin, bufpos - tokenBegin + 1);
        else
            return new String(buffer, tokenBegin, bufsize - tokenBegin) + new String(buffer, 0, bufpos + 1);
    }
}
