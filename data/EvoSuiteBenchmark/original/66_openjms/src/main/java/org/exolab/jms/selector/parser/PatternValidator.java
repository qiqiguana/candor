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

import org.exolab.jms.selector.SelectorException;


/**
 * This class validates the format of patterns and escape characters used
 * in 'like' expressions.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:45 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         SelectorAST
 * @see         SelectorParser
 */
final class PatternValidator {

    /**
     * Private constructor
     */
    private PatternValidator() {
    }

    /**
     * Validate a pattern and escape
     *
     * @param patternNode the pattern node
     * @param escapeNode the escape node. May be null.
     * @throws SelectorException if the pattern or escape node are invalid
     */
    public static void validate(final SelectorAST patternNode,
                                final SelectorAST escapeNode)
        throws SelectorException {

        String pattern = patternNode.getText();

        if (escapeNode != null) {
            String escape = escapeNode.getText();
            if (escape.length() != 1) {
                String msg = "escape must be a single character";
                throw new SelectorException(escapeNode.getContext(), msg);
            }

            char esc = escape.charAt(0);
            for (int i = 0; i < pattern.length(); ++i) {
                char ch = pattern.charAt(i);
                if (pattern.charAt(i) == esc) {
                    if (++i >= pattern.length()) {
                        String msg = "invalid pattern: no character "
                            + "following last escape character";
                        throw new SelectorException(patternNode.getContext(),
                                                    msg);
                    }
                }
            }
        }
    }

} //-- PatternValidator
