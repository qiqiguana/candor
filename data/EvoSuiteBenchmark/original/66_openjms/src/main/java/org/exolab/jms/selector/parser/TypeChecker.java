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

package org.exolab.jms.selector.parser;

import org.exolab.jms.selector.Identifiers;
import org.exolab.jms.selector.Type;
import org.exolab.jms.selector.TypeMismatchException;


/**
 * Utility class for performing type checking
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:45 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         SelectorAST
 * @see         SelectorTreeParser
 * @see         Type
 * @see         TypeMismatchException
 */
final class TypeChecker {

    /**
     * Private constructor
     */
    private TypeChecker() {
    }

    /**
     * Checks the return type of an expression against the expected type
     *
     * @param node the expression node
     * @param expected the expected type of the expression
     * @throws TypeMismatchException if the return type doesn't match that
     * expected
     */
    public static void check(final SelectorAST node, final Type expected)
        throws TypeMismatchException {

        Type type = node.getReturnType();
        if (type != expected && type != Type.UNDEFINED) {
            String msg = "expecting a " + expected + " expression, found a "
                + type;
            throw new TypeMismatchException(node.getContext(), msg);
        }
    }

    /**
     * Checks the return type of an expression against the expected type
     *
     * @param operator the expression operator
     * @param node the expression node
     * @param expected the expected type of the expression
     * @throws TypeMismatchException if the return type doesn't match that
     * expected
     */
    public static void check(final String operator, final SelectorAST node,
                             final Type expected)
        throws TypeMismatchException {

        Type type = node.getReturnType();
        if (type != expected && type != Type.UNDEFINED) {
            String msg = "expecting a " + expected
                + " expression for operator " + operator + ", found a " + type;
            throw new TypeMismatchException(node.getContext(), msg);
        }
    }

    /**
     * Checks the types of left and right hand sides of an expression against
     * the expected type
     *
     * @param operator the expression operator
     * @param left the left hand side of the expression
     * @param right the right hand side of the expression
     * @param expected the expected type of the expression
     * @throws TypeMismatchException if a type doesn't match that expected
     */
    public static void check(final String operator, final SelectorAST left,
                             final SelectorAST right, final Type expected)
        throws TypeMismatchException {

        check(operator, left, expected);
        check(operator, right, expected);
    }

    /**
     * Verifies if two expressions can be compared
     *
     * @param operator the comparison operator
     * @param left the left hand side of the expression
     * @param right the right hand side of the expression
     * @throws TypeMismatchException if the expressions can't be compared
     */
    public static void checkComparison(final String operator,
                                       final SelectorAST left,
                                       final SelectorAST right)
        throws TypeMismatchException {

        Type lhs = left.getReturnType();
        Type rhs = right.getReturnType();

        if (lhs == Type.UNDEFINED || rhs == Type.UNDEFINED) {
            // can't evaluate this at parse time.
        } else if (lhs == Type.STRING && rhs == Type.STRING) {
            checkStringComparison(operator, left, right);
        } else if ((lhs == Type.STRING && rhs != Type.STRING)
                   || (lhs == Type.BOOLEAN && rhs != Type.BOOLEAN)
                   || (lhs == Type.NUMERIC && rhs != Type.NUMERIC)) {
            String msg = "expecting a " + lhs + " expression for operator "
                + operator + ", found a " + rhs;
            throw new TypeMismatchException(right.getContext(), msg);
        }
    }

    /**
     * Verifies if two string expressions can be compared
     *
     * @param operator the comparison operator
     * @param left the left hand side of the expression
     * @param right the right hand side of the expression
     * @throws TypeMismatchException if the expressions can't be compared
     */
    public static void checkStringComparison(final String operator,
                                             final SelectorAST left,
                                             final SelectorAST right)
        throws TypeMismatchException {
        if (left.getType() == SelectorTokenTypes.IDENT
            && right.getType() == SelectorTokenTypes.STRING_LITERAL) {
            checkIdentifierComparison(left, right);
        } else if (left.getType() == SelectorTokenTypes.STRING_LITERAL
                   && right.getType() == SelectorTokenTypes.IDENT) {
            checkIdentifierComparison(right, left);
        }
    }

    /**
     * Verifies that an identifier may be compared to a string literal
     *
     * @param identifier the identifier
     * @param literal the string literal
     * @throws TypeMismatchException if the expressions can't be compared
     */
    public static void checkIdentifierComparison(final SelectorAST identifier,
                                                 final SelectorAST literal)
        throws TypeMismatchException {
        if (identifier.getText().equals(Identifiers.JMS_DELIVERY_MODE)) {
            String value = literal.getText();
            if (!value.equals(Identifiers.PERSISTENT)
                && !value.equals(Identifiers.NON_PERSISTENT)) {
                String msg = "Cannot compare JMSDeliveryMode with '"
                    + value + "'";
                throw new TypeMismatchException(identifier.getContext(), msg);
            }
        }
    }

} //-- TypeChecker
