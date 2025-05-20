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

import javax.jms.Message;


/**
 * This class implements a literal expression.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         Expression
 */
class Literal implements Expression {

    /**
     * The literal value
     */
    private SObject _value = null;

    /**
     * Construct a new <code>Literal</code> expression
     *
     * @param value the value of the literal
     */
    protected Literal(final SObject value) {
        _value = value;
    }

    /**
     * Construct an approximate numeric literal expression
     *
     * @param text the literal text
     * @return an approximate numeric literal expression
     * @throws SelectorException if <code>text</code> is not a valid
     * approximate numeric literal
     */
    public static Literal approxNumericLiteral(final String text)
        throws SelectorException {
        SDouble value = null;
        try {
            value = new SDouble(Double.parseDouble(text));
        } catch (NumberFormatException exception) {
            throw new SelectorException("invalid float: " + text);
        }
        return new Literal(value);
    }

    /**
     * Construct an exact numeric literal expression
     *
     * @param text the literal text
     * @return an exact numeric literal expression
     * @throws SelectorException if <code>text</code> is not a valid exact
     * numeric literal
     */
    public static Literal exactNumericLiteral(final String text)
        throws SelectorException {
        SLong value = null;
        try {
            value = new SLong(Long.decode(text).longValue());
        } catch (NumberFormatException exception) {
            throw new SelectorException("invalid integer: " + text);
        }
        return new Literal(value);
    }

    /**
     * Construct a string literal expression
     *
     * @param text the literal text
     * @return a string literal expression
     */
    public static Literal stringLiteral(final String text) {
        return new Literal(new SString(text)) {

            public final String toString() {
                return "'" + this.getValue().toString() + "'";
            }
        };
    }

    /**
     * Construct a boolean literal expression
     *
     * @param value the boolean literal value
     * @return a boolean literal expression
     */
    public static Literal booleanLiteral(final boolean value) {
        SBool bool = SBool.FALSE;
        if (value) {
            bool = SBool.TRUE;
        }
        return new Literal(bool);
    }

    /**
     * Evaluate the expression
     *
     * @param msg the message to use to obtain any header identifier and
     * property values
     * @return the evaluated result, or <code>null</code> if the value of the
     *  expression is unknown
     */
    public final SObject evaluate(final Message msg) {
        return _value;
    }

    /**
     * Return a string representation of the literal
     *
     * @return a string representation of the literal
     */
    public String toString() {
        return getValue().toString();
    }

    /**
     * Returns the value of the literal
     *
     * @return the value of the literal
     */
    protected final SObject getValue() {
        return _value;
    }

} //-- Literal
