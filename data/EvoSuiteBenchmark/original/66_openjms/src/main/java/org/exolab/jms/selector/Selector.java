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

import java.io.StringReader;

import javax.jms.InvalidSelectorException;
import javax.jms.Message;

import org.exolab.jms.selector.parser.SelectorLexer;
import org.exolab.jms.selector.parser.SelectorParser;
import org.exolab.jms.selector.parser.SelectorTreeParser;


/**
 * This class enables messages to be filtered using a message selector.
 * This is a String whose syntax is based on a subset of the SQL92
 * conditional expression syntax.
 *
 * A selector can contain:
 * <ul>
 * <li>Literals:</li>
 * <ul>
 *   <li>A string literal is enclosed in single quotes with an included
 *       single quote represented by doubled single quote such as 'literal'
 *       and 'literal''s'; like Java <i>String</i> literals these use the
 *       unicode character encoding.
 *   </li>
 *   <li>An exact numeric literal is a numeric value without a decimal point
 *       such as 57, -957, +62; numbers in the range of Java <i>long</i> are
 *       supported. Exact numeric literals use the Java integer literal syntax.
 *   </li>
 *   <li>An approximate numeric literal is a numeric value in scientific
 *       notation such as 7E3, -57.9E2 or a numeric value with a decimal such
 *       as 7., -95.7, +6.2; numbers in the range of Java <i>double</i>
 *       are supported. Approximate literals use the Java floating point
 *       literal syntax.
 *   </li>
 *   <li>The boolean literals <i>TRUE </i>and <i>FALSE</i>.</li>
 * </ul>
 * <li>Identifiers:</li>
 * <ul>
 *   <li>Identifiers use the Java identifier syntax. They are case sensitive.
 *   </li>
 *   <li>Identifiers cannot be the names <i>NULL</i>, <i>TRUE</i>, or
 *       <i>FALSE</i>.
 *   </li>
 *   <li>Identifiers cannot be <i>NOT, AND, OR, BETWEEN, LIKE, IN</i>, and
 *       <i>IS</i>.
 *   </li>
 *   <li>Identifiers are either header field references or property references.
 *   </li>
 *   <br>Message header field references are restricted to
 *       <i>JMSDeliveryMode</i>, <i>JMSPriority</i>, <i>JMSMessageID</i>,
 *       <i>JMSTimestamp</i>, <i>JMSCorrelationID</i>, and <i>JMSType</i>.
 *       <i>JMSMessageID</i>, <i>JMSCorrelationID</i>, and <i>JMSType</i>
 *       values may be <i>null</i> and if so are treated as a NULL value.
 *   <li>Any name beginning with 'JMSX' is a JMS defined property name.</li>
 *   <li>Any name beginning with 'JMS_' is a provider-specific property name.
 *   </li>
 *   <li>Any name that does not begin with 'JMS' is an application-specific
 *       property name. If a property is referenced that does not exist in a
 *       message its value is NULL. If it does exist, its value is the
 *       corresponding property value.
 *   </li>
 * </ul>
 * <li>Expressions:</li>
 * <ul>
 *   <li>A selector is a conditional expression; a selector that evaluates to
 *       true matches; a selector that evaluates to false or unknown does not
 *       match.
 *   </li>
 *   <li>Arithmetic expressions are composed of themselves, arithmetic
 *       operations, identifiers with numeric values and numeric literals.
 *   </li>
 *   <li>Conditional expressions are composed of themselves, comparison
 *       operations, logical operations, identifiers with boolean values and
 *       boolean literals.
 *   </li>
 *   <li>Standard bracketing () for ordering expression evaluation is
 *       supported.
 *   </li>
 *   <li>Logical operators in precedence order: NOT, AND, OR.</li>
 *   <li>Comparison operators: =, >, >=, &lt;, &lt;=, &lt;> (not equal).
 *   </li>
 *   <li>Only <i>like </i>type values can be compared. One exception is that it
 *       is valid to compare exact numeric values and approximate numeric
 *       values (the type conversion required is defined by the rules of Java
 *       numeric promotion). If the comparison of non-like type values is
 *       attempted, the selector is always false.
 *   </li>
 *   <li><i>String</i> and <i>Boolean</i> comparison is restricted to = and
 *       &lt;>. Two strings are equal if and only if they contain the same
 *       sequence of characters.
 *   </li>
 * </ul>
 * <li>Arithmetic operators in precedence order:</li>
 * <ul>
 *   <li>+, - unary</li>
 *   <li>*, / multiplication and division</li>
 *   <li>+, - addition and subtraction</li>
 *   <li>Arithmetic operations use Java numeric promotion.</li>
 * </ul>
 *
 * <li><i>arithmetic-expr1 </i>[NOT] BETWEEN <i>arithmetic-expr2 </i>AND<i>
 *     arithmetic-expr3</i> comparison operator
 * </li>
 *   <ul>
 *     <li>age BETWEEN 15 and 19 is equivalent to age >= 15 AND age &lt;= 19
 *     </li>
 *     <li>age NOT BETWEEN 15 and 19 is equivalent to age &lt; 15 OR age > 19
 *     </li>
 *   </ul>
 *   <li><i>identifier </i>[NOT] IN (<i>string-literal1, string-literal2,...
 *       </i>)
 *   </li>
 *   <br>comparison operator where identifier has a <i>String</i> or NULL
 *       value.
 *   <ul>
 *     <li>Country IN ('UK', 'US', 'France') is true for 'UK' and false for
 *         'Peru'. It is equivalent to the expression (Country = ' UK') OR
 *         (Country = ' US') OR (Country = ' France')
 *     </li>
 *     <li>Country NOT IN (' UK', 'US', 'France') is false for 'UK' and true
 *         for 'Peru'. It is equivalent to the expression NOT ((Country = 'UK')
 *         OR (Country = 'US') OR (Country = 'France'))
 *     </li>
 *     <li>If <i>identifier </i>of an IN or NOT IN operation is NULL the value
 *         of the operation is unknown.
 *     </li>
 *   </ul>
 *   <li><i>identifier </i>[NOT] LIKE <i>pattern-value</i> [ESCAPE
 *       <i>escape-character</i>]
 *   </li>
 *   <br>comparison operator, where <i>identifier</i> has a <i>String</i>
 *       value; <i>pattern-value</i> is a string literal where '_' stands for
 *       any single character; '%' stands for any sequence of characters
 *       (including the empty sequence); and all other characters stand for
 *       themselves. The optional <i>escape-character</i> is a single character
 *       string literal whose character is used to escape the special meaning
 *       of the '_' and '%' in <i>pattern-value</i>.
 *   <ul>
 *     <li><i>phone LIKE '12%3'</i> is true for '123', '12993' and false for
 *         '1234'
 *     </li>
 *     <li><i>word LIKE 'l_se'</i> is true for 'lose' and false for 'loose'
 *     </li>
 *     <li><i>underscored LIKE '\_%' ESCAPE '\'</i> is true for '_foo' and
 *         false for 'bar'
 *     </li>
 *     <li><i>phone NOT LIKE '12%3'</i> is false for '123' and '12993' and
 *         true for '1234'
 *     </li>
 *     <li>If <i>identifier</i> of a LIKE or NOT LIKE operation is NULL the
 *         value of the operation is unknown.
 *     </li>
 *   </ul>
 *   <li><i>identifier</i> IS NULL</li>
 *   <br>comparison operator tests for a null header field value, or a
 *       missing property value.
 *   <ul>
 *     <li><i>prop_name</i> IS NULL</li>
 *     <li><i>identifier</i> IS NOT NULL comparison operator tests for the
 *         existence of a non null header field value or property value.
 *     </li>
 *     <li><i>prop_name</i> IS NOT NULL</li>
 *   </ul>
 * </ul></ul>
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 */
public class Selector {

    /**
     * The 'compiled' expression
     */
    private final Expression _evaluator;


    /**
     * Construct a message selector that selects messages based on the
     * supplied expression.
     *
     * @param       expression      the conditional expression
     * @throws      InvalidSelectorException if expression is invalid
     */
    public Selector(final String expression) throws InvalidSelectorException {
        try {
            if (expression == null || expression.length() == 0) {
                // always return true for null or empty expressions
                _evaluator = Literal.booleanLiteral(true);
            } else {
                SelectorLexer lexer = new SelectorLexer(
                    new StringReader(expression));
                lexer.initialise();

                SelectorParser parser = new SelectorParser(lexer);
                parser.initialise();
                parser.selector(); // start parsing at the selector rule

                SelectorTreeParser builder = new SelectorTreeParser();
                builder.initialise(new DefaultExpressionFactory());
                _evaluator = builder.selector(parser.getAST());
            }
        } catch (Exception exception) {
            throw new InvalidSelectorException(exception.toString());
        }
    }

    /**
     * Return if message is selected by the expression
     *
     * @param message the message
     * @return <code>true</code> if the message is selected, otherwise
     * <code>false</code>
     */
    public boolean selects(final Message message) {
        boolean result = false;
        try {
            SObject value = _evaluator.evaluate(message);
            if (value instanceof SBool) {
                result = ((SBool) value).value();
            }
        } catch (TypeMismatchException ignore) {
        }
        return result;
    }

} //-- Selector
