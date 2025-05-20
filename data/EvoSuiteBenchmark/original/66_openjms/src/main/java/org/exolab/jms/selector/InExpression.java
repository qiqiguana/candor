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
import java.util.Iterator;
import java.util.TreeSet;

import javax.jms.Message;


/**
 * This class implements an 'in' expression.
 * This is an expression that returns a boolean if a identifier's value is one
 * a set of supplied values. If the identifier is null (undefined), then the
 * expression evaluates to null.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         Expression
 * @see         IdentifierExpression
 * @see         SBool
 */
class InExpression extends IdentifierExpression {

    /**
     * The set of values to compare against
     */
    private final HashSet _values;


    /**
     * Construct a new <code>InExpression</code>
     *
     * @param ident the identifier
     * @param values the values to check against
     */
    public InExpression(final Identifier ident, final HashSet values) {
        super(ident);
        _values = values;
    }

    /**
     * Evaluate the expression
     *
     * @param msg the message to use to obtain any header identifier and
     * property values
     * @return the evaluated result, or <code>null</code> if the value of the
     *  expression is unknown
     * @throws TypeMismatchException if the expression tries to evaluate
     * mismatched types.
     */
    public final SObject evaluate(final Message msg)
        throws TypeMismatchException {
        SBool result = null;

        SString value = TypeCaster.castToString(identifier().evaluate(msg),
            "in expression");
        if (value != null) {
            if (_values.contains(value.getObject())) {
                result = SBool.TRUE;
            } else {
                result = SBool.FALSE;
            }
        }
        return result;
    }

    /**
     * Return a string representation of this expression.
     *
     * @return a string representation of this expression
     */
    public final String toString() {
        StringBuffer result = new StringBuffer();
        result.append('(');
        result.append(identifier().toString());
        result.append(" in (");
        int i = 0;
        TreeSet sorted = new TreeSet(_values);
        // use a TreeSet to ensure that the result is the same for all
        // runs/implementations. In general, the HashSet should be faster for
        // individual lookups.
        for (Iterator iter = sorted.iterator(); iter.hasNext(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append("'");
            result.append((String) iter.next());
            result.append("'");
        }
        result.append("))");
        return result.toString();
    }

} //-- InExpression
