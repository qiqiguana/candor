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

import antlr.CommonAST;
import antlr.CommonToken;
import antlr.Token;
import org.exolab.jms.selector.Context;
import org.exolab.jms.selector.Type;


/**
 * Selector AST node. This adds expression type and context information used
 * by the tree parser to validate expressions.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:45 $
 * @author      <a href="mailto:tima@intalio.com">Tim Anderson</a>
 * @see         Context
 * @see         SelectorParser
 * @see         SelectorTreeParser
 * @see         Type
 */
public class SelectorAST extends CommonAST {

    /**
     * The return type
     */
    private Type _type;

    /**
     * The context of the node
     */
    private Context _context;


    /**
     * Construct a new <code>SelectorAST</code>
     */
    public SelectorAST() {
        _type = Type.UNDEFINED;
    }

    /**
     * Initialise this with a token
     *
     * @param token the token
     */
    public void initialize(final Token token) {
        super.initialize(token);
        _context = new Context(((CommonToken) token).getLine(),
            ((CommonToken) token).getColumn());
    }

    /**
     * Sets the return type of the expression
     *
     * @param type the return type
     */
    public void setReturnType(final Type type) {
        _type = type;
    }

    /**
     * Returns the return type of the expression
     *
     * @return the return type of the expression
     */
    public Type getReturnType() {
        return _type;
    }

    /**
     * Returns the context of the expression
     *
     * @return the context of the expression
     */
    public Context getContext() {
        return _context;
    }

} //-- SelectorAST
