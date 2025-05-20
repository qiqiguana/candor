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
 * Copyright 2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: SelectorASTFactory.java,v 1.1 2005/11/12 13:47:56 tanderson Exp $
 */
package org.exolab.jms.selector.parser;

import java.lang.reflect.Constructor;
import java.util.Hashtable;

import antlr.ASTFactory;
import antlr.Token;
import antlr.collections.AST;


/**
 * This class was created to override the 4 methods in antlr.ASTFactory that use
 * {@link Class#forName}. In the case of interaction with OpenJMS,
 * the ClassLoader for the Factory class must not be higher in the tree than
 * the OpenJMS classes.  In the case of Weblogic 8.1 SP3, some antlr classes,
 * including ASTFactory are encapsulated in the weblogic.jar, the ClassLoader on
 * the antlr.ASTFactory class is the System ClassLoader.  If you have
 * openjms.jar in your EAR or WAR files, the antlr.ASTFactory class will not be
 * able to find the OpenJMS classes when it is doing
 * a Class.forName to load them.
 *
 * @author jason.michael
 * @author <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @version $Revision: 1.1 $ $Date: 2005/11/12 13:47:56 $
 */
public class SelectorASTFactory extends ASTFactory {

    /**
     * Specify an "override" for the Java AST object created for a
     * specific token.
     *
     * @since 2.7.2
     */
    public void setTokenTypeASTNodeType(int tokenType, String className)
            throws IllegalArgumentException {
        if (tokenTypeToASTClassMap == null) {
            tokenTypeToASTClassMap = new Hashtable();
        }
        if (className == null) {
            tokenTypeToASTClassMap.remove(new Integer(tokenType));
            return;
        }
        Class c = null;
        try {
            c = Class.forName(className);
            tokenTypeToASTClassMap.put(new Integer(tokenType), c);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid class, " + className);
        }
    }

    /**
     * @since 2.7.2
     */
    protected AST create(String className) {
        Class c = null;
        try {
            c = Class.forName(className);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid class, " + className);
        }
        return create(c);
    }

    /**
     * @since 2.7.2
     */
    protected AST createUsingCtor(Token token, String className) {
        Class c = null;
        AST t = null;
        try {
            c = Class.forName(className);
            Class[] tokenArgType = new Class[]{antlr.Token.class};
            Constructor ctor = c.getConstructor(tokenArgType);
            if (ctor != null) {
                t = (AST) ctor.newInstance(new Object[]{token}); // make a new one
            } else {
                // just do the regular thing if you can't find the ctor
                // Your AST must have default ctor to use this.
                t = create(c);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid class or can't make instance, " + className);
        }
        return t;
    }

    public void setASTNodeClass(String t) {
        theASTNodeType = t;
        try {
            theASTNodeTypeClass = Class.forName(t); // get class def
        } catch (Exception e) {
            // either class not found,
            // class is interface/abstract, or
            // class or initializer is not accessible.
            error("Can't find/access AST Node type" + t);
        }
    }

}