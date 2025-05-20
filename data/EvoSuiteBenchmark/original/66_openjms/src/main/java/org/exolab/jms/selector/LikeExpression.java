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

import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Matcher;


/**
 * This class implements a 'like' expression.
 * This is an expression that returns true if a identifier's value matches
 * a pattern
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:44 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         Identifier
 * @see         IdentifierExpression
 * @see         RegexpFactory
 */
class LikeExpression extends IdentifierExpression {

    /**
     * The regular expression
     */
    private final Pattern _regexp;

    /**
     * The pattern matcher
     */
    private final PatternMatcher _matcher;

    /**
     * The pattern
     */
    private final String _pattern;

    /**
     * The escape character. May be null.
     */
    private final String _escape;


    /**
     * Construct a new <code>LikeExpression</code>
     *
     * @param identifier the identifier
     * @param pattern the pattern
     * @param escape the escape character. May be null.
     * @throws SelectorException if <code>pattern</code> or <code>escape</code>
     * are invalid
     */
    public LikeExpression(final Identifier identifier, final String pattern,
                          final String escape) throws SelectorException {
        super(identifier);
        _pattern = pattern;
        _escape = escape;
        _regexp = getRegexp(_pattern, _escape);
        _matcher = new Perl5Matcher();
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
        SString value = TypeCaster.castToString(identifier().evaluate(msg),
            "like expression");
        if (value != null) {
            if (_matcher.matches((String) value.getObject(), _regexp)) {
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
        result.append(" like '");
        result.append(_pattern);
        result.append('\'');
        if (_escape != null) {
            result.append(" escape '");
            result.append(_escape);
            result.append('\'');
        }
        result.append(')');
        return result.toString();
    }

    /**
     * Converts the pattern and escape to an ORO <code>Pattern</code>
     *
     * @param pattern the pattern
     * @param escape the escape character. May be null
     * @return an ORO <code>Pattern</code> corresponding to
     * <code>pattern</code> and <code>escape</code>
     * @throws SelectorException if <code>pattern</code> or <code>escape</code>
     * are invalid
     */
    private Pattern getRegexp(final String pattern, final String escape)
        throws SelectorException {
        Pattern result = null;
        Character esc = null;

        if (escape != null) {
            if (escape.length() != 1) {
                throw new SelectorException("Invalid escape: " + escape);
            }
            esc = new Character(escape.charAt(0));
        }

        try {
            result = RegexpFactory.create(pattern, esc);
        } catch (InvalidRegexpException err) {
            throw new SelectorException("Invalid pattern: " + pattern);
        }
        return result;
    }

} //-- LikeExpression
