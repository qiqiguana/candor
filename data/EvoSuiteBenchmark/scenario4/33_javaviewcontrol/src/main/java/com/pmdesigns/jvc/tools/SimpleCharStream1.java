/* JavaCCOptions:STATIC=false,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.pmdesigns.jvc.tools;

public class SimpleCharStream {

    /**
     * Whether parser is static.
     */
    public static final boolean staticFlag = false;

    int bufsize;

    int available;

    int tokenBegin;

    /**
     * Position in buffer.
     */
    public int bufpos = -1;

    protected int[] bufline;

    protected int[] bufcolumn;

    protected int column = 0;

    protected int line = 1;

    protected boolean prevCharIsCR = false;

    protected boolean prevCharIsLF = false;

    protected java.io.Reader inputStream;

    protected char[] buffer;

    protected int maxNextCharInd = 0;

    protected int inBuf = 0;

    protected int tabSize = 8;

    protected void setTabSize(int i);

    protected int getTabSize(int i);

    protected void ExpandBuff(boolean wrapAround);

    protected void FillBuff() throws java.io.IOException;

    /**
     * Start.
     */
    public char BeginToken() throws java.io.IOException;

    protected void UpdateLineColumn(char c);

    /**
     * Read a character.
     */
    public char readChar() throws java.io.IOException;

    public int getColumn();

    public int getLine();

    /**
     * Get token end column number.
     */
    public int getEndColumn();

    /**
     * Get token end line number.
     */
    public int getEndLine();

    /**
     * Get token beginning column number.
     */
    public int getBeginColumn();

    /**
     * Get token beginning line number.
     */
    public int getBeginLine();

    /**
     * Backup a number of characters.
     */
    public void backup(int amount);

    /**
     * Constructor.
     */
    public SimpleCharStream(java.io.Reader dstream, int startline, int startcolumn, int buffersize) {
    }

    /**
     * Constructor.
     */
    public SimpleCharStream(java.io.Reader dstream, int startline, int startcolumn) {
    }

    /**
     * Constructor.
     */
    public SimpleCharStream(java.io.Reader dstream) {
    }

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.Reader dstream, int startline, int startcolumn, int buffersize);

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.Reader dstream, int startline, int startcolumn);

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.Reader dstream);

    /**
     * Constructor.
     */
    public SimpleCharStream(java.io.InputStream dstream, String encoding, int startline, int startcolumn, int buffersize) throws java.io.UnsupportedEncodingException {
    }

    /**
     * Constructor.
     */
    public SimpleCharStream(java.io.InputStream dstream, int startline, int startcolumn, int buffersize) {
    }

    /**
     * Constructor.
     */
    public SimpleCharStream(java.io.InputStream dstream, String encoding, int startline, int startcolumn) throws java.io.UnsupportedEncodingException {
    }

    /**
     * Constructor.
     */
    public SimpleCharStream(java.io.InputStream dstream, int startline, int startcolumn) {
    }

    /**
     * Constructor.
     */
    public SimpleCharStream(java.io.InputStream dstream, String encoding) throws java.io.UnsupportedEncodingException {
    }

    /**
     * Constructor.
     */
    public SimpleCharStream(java.io.InputStream dstream) {
    }

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.InputStream dstream, String encoding, int startline, int startcolumn, int buffersize) throws java.io.UnsupportedEncodingException;

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.InputStream dstream, int startline, int startcolumn, int buffersize);

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.InputStream dstream, String encoding) throws java.io.UnsupportedEncodingException;

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.InputStream dstream);

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.InputStream dstream, String encoding, int startline, int startcolumn) throws java.io.UnsupportedEncodingException;

    /**
     * Reinitialise.
     */
    public void ReInit(java.io.InputStream dstream, int startline, int startcolumn);

    /**
     * Get token literal value.
     */
    public String GetImage();

    /**
     * Get the suffix.
     */
    public char[] GetSuffix(int len);

    /**
     * Reset buffer when finished.
     */
    public void Done();

    /**
     * Method to adjust line and column numbers for the start of a token.
     */
    public void adjustBeginLineColumn(int newLine, int newCol);
}
