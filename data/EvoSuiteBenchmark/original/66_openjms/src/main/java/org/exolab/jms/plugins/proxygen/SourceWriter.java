/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2003-2004 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: SourceWriter.java,v 1.1 2004/11/26 01:51:14 tanderson Exp $
 */
package org.exolab.jms.plugins.proxygen;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;


/**
 * Helper class for generating formatted Java source
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:51:14 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class SourceWriter extends BufferedWriter {

    /**
     * The current indent
     */
    private int _indent = 0;

    /**
     * The number of spaces to indent
     */
    private final int _spaces = 4;

    /**
     * Determines if at the start of a new line
     */
    private boolean _startOfLine = true;


    /**
     * Construct a new <code>SourceWriter</code>
     *
     * @param writer the writer to write to
     */
    public SourceWriter(Writer writer) {
        super(writer);
    }

    /**
     * Increments the indentation
     */
    public void incIndent() {
        ++_indent;
    }

    /**
     * Decrements the indentation
     */
    public void decIndent() {
        --_indent;
    }

    /**
     * Write a single character
     *
     * @param ch the character to write
     * @throws IOException for any I/O error
     */
    public void write(int ch) throws IOException {
        indent();
        super.write(ch);
    }

    /**
     * Write a portion of an array of characters.
     *
     * @param buffer the buffer to write
     * @param offset the offset into <code>buffer</code>
     * @param length the no. of characters to write
     * @throws IOException for any I/O error
     */
    public void write(char[] buffer, int offset, int length)
        throws IOException {
        indent();
        super.write(buffer, offset, length);
    }

    /**
     * Write a portion of a String.
     *
     * @param string the String to write
     * @param offset the offset into <code>string</code>
     * @param length the no. of characters to write
     * @throws IOException for any I/O error
     */
    public void write(String string, int offset, int length)
        throws IOException {
        indent();
        super.write(string, offset, length);
    }

    /**
     * Write a line separator
     *
     * @throws IOException for any I/O error
     */
    public void writeln() throws IOException {
        newLine();
    }

    /**
     * Write a String terminated by a new line
     *
     * @param string the String to write
     * @throws IOException for any I/O error
     */
    public void writeln(String string) throws IOException {
        write(string);
        newLine();
    }

    /**
     * Write a new line, and increase the indent
     *
     * @throws IOException for any I/O error
     */
    public void writelnInc() throws IOException {
        newLine();
        incIndent();
    }

    /**
     * Write a String terminated by a new line, and increase the indent
     *
     * @param string the String to write
     * @throws IOException for any I/O error
     */
    public void writelnInc(String string) throws IOException {
        writeln(string);
        incIndent();
    }

    /**
     * Write a new line, and decrease the indent
     *
     * @throws IOException for any I/O error
     */
    public void writelnDec() throws IOException {
        newLine();
        decIndent();
    }

    /**
     * Write a String terminated by a new line, and decrease the indent
     *
     * @param string the String to write
     * @throws IOException for any I/O error
     */
    public void writelnDec(String string) throws IOException {
        writeln(string);
        decIndent();
    }

    /**
     * Write a line separator
     *
     * @throws IOException for any error
     */
    public void newLine() throws IOException {
        super.newLine();
        _startOfLine = true;
    }

    /**
     * Indents iff at the start of the line
     *
     * @throws IOException for any I/O error
     */
    protected void indent() throws IOException {
        if (_startOfLine) {
            int count = _indent * _spaces;
            for (int i = 0; i < count; ++i) {
                super.write(' ');
            }
            _startOfLine = false;
        }
    }

}
