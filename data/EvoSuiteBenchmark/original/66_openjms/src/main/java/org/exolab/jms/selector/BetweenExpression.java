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
 * This class implements a 'between' expression.
 * This is a boolean expression of the form:
 *    numeric-expr1 between numeric-expr2 and numeric-expr3
 * This is equivalent to:
 *    numeric-expr1 >= numeric-expr2 and numeric-expr1 <= numeric-expr3
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         Expression
 * @see         SBool
 */
class BetweenExpression implements Expression {

    /**
     *  Corresponds to numeric-expr1
     */
    private final Expression _num1;

    /**
     *  Corresponds to numeric-expr2
     */
    private final Expression _num2;

    /**
     *  Corresponds to numeric-expr3
     */
    private final Expression _num3;

    /**
     * The expression context, for error reporting purposes
     */
    private static final String CONTEXT = "between expression";


    /**
     * Construct an expression that returns num1 >= num2 and num1 <= num3
     * when evaluated
     *
     * @param       num1            arithmetic expression
     * @param       num2            arithmetic expression representing the
     *                              lower bound
     * @param       num3            arithmetic expression representing the
     *                              upper bound
     */
    public BetweenExpression(final Expression num1, final Expression num2,
                             final Expression num3) {
        _num1 = num1;
        _num2 = num2;
        _num3 = num3;
    }

    /**
     * Evaluate the 'between' expression
     *
     * @param msg the message to evalulate the expression against
     * @return an SBool instance
     * @throws TypeMismatchException if any of the expressions are not numeric
     */
    public final SObject evaluate(final Message msg)
        throws TypeMismatchException {
        SBool result = null;
        SNumber val1 = TypeCaster.castToNumber(_num1.evaluate(msg), CONTEXT);
        if (val1 != null) {
            SNumber val2 = TypeCaster.castToNumber(_num2.evaluate(msg),
                CONTEXT);
            if (val2 != null) {
                SNumber val3 = TypeCaster.castToNumber(_num3.evaluate(msg),
                    CONTEXT);
                if (val3 != null) {
                    if (val1.greaterEqual(val2).value()
                        && val1.lessEqual(val3).value()) {
                        result = SBool.TRUE;
                    } else {
                        result = SBool.FALSE;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Return a string representation of this expression
     *
     * @return a string representation of this expression
     */
    public final String toString() {
        StringBuffer result = new StringBuffer();
        result.append('(');
        result.append(_num1.toString());
        result.append(" between ");
        result.append(_num2.toString());
        result.append(" and ");
        result.append(_num3.toString());
        result.append(')');
        return result.toString();
    }

} //-- BetweenExpression
