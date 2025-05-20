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
 * This class implements an 'and' expression.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         Expression
 * @see         LogicalOperator
 * @see         SBool
 */
class And extends LogicalOperator {

    /**
     * The expression context, for error reporting purposes
     */
    private static final String CONTEXT = "and operator";


    /**
     * Construct a new <code>And</code> operator
     *
     * @param lhs the left hand side of the expression
     * @param rhs the right hand side of the expression
     */
    public And(final Expression lhs, final Expression rhs) {
        super("and", lhs, rhs);
    }

    /**
     * Evaluate the expression
     *
     * @param msg the message to use to obtain any header identifier and
     * property values
     * @return the evaluated result, or <code>null</code> if the value of the
     * expression is unknown
     * @throws TypeMismatchException if the expression tries to evaluate
     * mismatched types.
     */
    public final SObject evaluate(final Message msg)
        throws TypeMismatchException {
        SBool result = null;
        SBool lhs = TypeCaster.castToBool(left().evaluate(msg), CONTEXT);
        if (lhs != null) {
            if (!lhs.value()) {
                result = SBool.FALSE;
            } else {
                SBool rhs = TypeCaster.castToBool(right().evaluate(msg),
                    CONTEXT);
                result = lhs.and(rhs);
            }
        } else {
            SBool rhs = TypeCaster.castToBool(right().evaluate(msg), CONTEXT);
            if (rhs != null) {
                result = rhs.and(lhs);
            }
        }
        return result;
    }

} //-- And
