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
 * Copyright 2000-2001,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 */

package org.exolab.jms.selector;


/**
 * This class encapsulates the context of a parsed token.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         SelectorException
 * @see         org.exolab.jms.selector.parser.SelectorAST
 */
public final class Context {

    /**
     * The line of the parsed token
     */
    private final int _line;

    /**
     * The column of the parsed token
     */
    private final int _column;


    /**
     * Construct a new <code>Context</code>
     *
     * @param line the line where the token was encountered
     * @param column the column where the token was encountered
     */
    public Context(final int line, final int column) {
        _line = line;
        _column = column;
    }

    /**
     * Returns the line where the token was encountered
     *
     * @return the line number
     */
    public int getLine() {
        return _line;
    }

    /**
     * Returns the column where the token was encountered
     *
     * @return the column number
     */
    public int getColumn() {
        return _column;
    }

    /**
     * Returns a string representation of this
     *
     * @return a string representation of this
     */
    public String toString() {
        return "line " + _line + ", column " + _column;
    }

} //-- Context
