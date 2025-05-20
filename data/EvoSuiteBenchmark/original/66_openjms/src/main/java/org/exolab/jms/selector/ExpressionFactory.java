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

import java.util.HashSet;


/**
 * This interface specifies the methods needed to create all of the
 * expressions supported by the message selector. It exists solely to
 * decouple the expression evaluation classes from the parser.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         Expression
 * @see         org.exolab.jms.selector.parser.SelectorTokenTypes
 * @see         org.exolab.jms.selector.parser.SelectorTreeParser
 */
public interface ExpressionFactory {

    /**
     * Create a binary operator expression
     *
     * @param operator the operator token type from SelectorTokenTypes
     * @param left the left-hand side of the binary expression
     * @param right the right-hand side of the binary expression
     * @return a new binary expression
     * @throws SelectorException if the operator is not a valid binary operator
     */
    Expression binaryOperator(int operator, Expression left, Expression right)
        throws SelectorException;

    /**
     * Create an unary operator expression
     *
     * @param operator the operator token type from SelectorTokenTypes
     * @param operand the expression to apply the operator to
     * @return a new unary expression
     * @throws SelectorException if the operator is not a valid unary operator
     */
    Expression unaryOperator(int operator, Expression operand)
        throws SelectorException;

    /**
     * Create an identifier expression
     *
     * @param name the name of the identifier
     * @return a new identifier expression
     * @throws SelectorException is name is not a valid identifier
     */
    Expression identifier(String name) throws SelectorException;

    /**
     * Create an 'is null' expression
     *
     * @param identifier the identifer expression to apply the 'is null' test
     * @return an 'is null' expression
     * @throws SelectorException for any error
     */
    Expression isNull(Expression identifier) throws SelectorException;

    /**
     * Create a 'like' expression
     *
     * @param identifier the identifer to apply the 'like' test to
     * @param pattern the search pattern
     * @param escape the escape character. This may be null
     * @return a new 'like' expression
     * @throws SelectorException if the pattern or escape is invalid
     */
    Expression like(Expression identifier, String pattern, String escape)
        throws SelectorException;

    /**
     * Create a 'between' expression that returns the result of:<br/>
     * <code>num1 >= num2 and num1 <= num3</code>
     * when evaluated
     *
     * @param num1 an arithmethic expression
     * @param num2 an arithmethic expression
     * @param num3 an arithmethic expression
     * @return a new 'between' expression
     * @throws SelectorException for any error
     */
    Expression between(Expression num1, Expression num2, Expression num3)
        throws SelectorException;

    /**
     * Create an 'in' expression
     *
     * @param identifier string identifer to apply the 'in' test to
     * @param set the set of string values to compare against
     * @return a new 'in' expression
     * @throws SelectorException for any error
     */
    Expression in(Expression identifier, HashSet set) throws SelectorException;

    /**
     * Create a literal expression
     *
     * @param type the operator token type from SelectorTokenTypes
     * @param text the literal text
     * @return a new literal expression
     * @throws SelectorException if type is not a valid literal type
     */
    Expression literal(int type, String text) throws SelectorException;

} //-- ExpressionFactory
