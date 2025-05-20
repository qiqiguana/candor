/* JavaCCOptions:STATIC=false,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.pmdesigns.jvc.tools;

public class SimpleCharStream {

    /**
     * Read a character.
     */
    public char readChar() throws java.io.IOException {
        if (inBuf > 0) {
            --inBuf;
            if (++bufpos == bufsize)
                bufpos = 0;
            return buffer[bufpos];
        }
        if (++bufpos >= maxNextCharInd)
            FillBuff();
        char c = buffer[bufpos];
        UpdateLineColumn(c);
        return c;
    }

    protected void FillBuff() throws java.io.IOException;

    protected void UpdateLineColumn(char c);
}
