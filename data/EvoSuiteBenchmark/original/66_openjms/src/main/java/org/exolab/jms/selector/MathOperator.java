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
 * This class is the base class for all expressions that are numeric binary
 * operators. These return a numeric result when evaluated, or null
 * if the result is undefined.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         Expression
 * @see         SNumber
 */
abstract class MathOperator extends BinaryOperator {

    /**
     * Construct a new <code>MathOperator</code>
     *
     * @param operator the operator
     * @param lhs the left hand side of the expression
     * @param rhs the right hand side of the expression
     */
    protected MathOperator(final String operator, final Expression lhs,
                           final Expression rhs) {
        super(operator, lhs, rhs);
    }

    /**
     * Evaluate the expression
     *
     * @param msg the message to evaluate this expression against
     * @return the result of expression
     * @throws TypeMismatchException if the result of the left or right
     * hand expressions are not numeric
     */
    public final SObject evaluate(final Message msg)
        throws TypeMismatchException {

        SNumber result = null;
        SNumber lhs = TypeCaster.castToNumber(left().evaluate(msg), context());
        if (lhs != null) {
            SNumber rhs = TypeCaster.castToNumber(right().evaluate(msg),
                context());
            if (rhs != null) {
                result = evaluate(lhs, rhs);
            }
        }
        return result;
    }

    /**
     * Evaluate the expression
     *
     * @param lhs the left hand side of the expression
     * @param rhs the right hand side of the expression
     * @return the result of expression
     */
    protected abstract SNumber evaluate(final SNumber lhs, final SNumber rhs);

    /**
     * The context of the expression, for error reporting purposes
     *
     * @return the context of the expression
     */
    private final String context() {
        return "operator " + operator();
    }

} //-- MathOperator
