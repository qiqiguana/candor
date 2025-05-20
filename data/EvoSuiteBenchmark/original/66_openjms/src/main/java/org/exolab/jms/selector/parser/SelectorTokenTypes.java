// $ANTLR 2.7.6 (2005-12-22): "Selector.g" -> "SelectorParser.java"$

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
 * Copyright 2000-2005 (C) Exoffice Technologies Inc. All Rights Reserved.
 */
 
    package org.exolab.jms.selector.parser;


    import org.exolab.jms.selector.Context;
    import org.exolab.jms.selector.Identifiers;
    import org.exolab.jms.selector.SelectorException;
    import org.exolab.jms.selector.Type;

/**
 * Selector parser
 *
 * @version     $Revision: 1.2 $ $Date: 2005/11/12 13:47:56 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         SelectorLexer
 * @see         SelectorTreeParser
 */

public interface SelectorTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int UNARY_MINUS = 4;
	int LITERAL_or = 5;
	int LITERAL_and = 6;
	int LITERAL_not = 7;
	int EQUAL = 8;
	int NOT_EQUAL = 9;
	int LT = 10;
	int GT = 11;
	int LE = 12;
	int GE = 13;
	int PLUS = 14;
	int MINUS = 15;
	int MULTIPLY = 16;
	int DIVIDE = 17;
	int LPAREN = 18;
	int RPAREN = 19;
	int IDENT = 20;
	int LITERAL_is = 21;
	int LITERAL_null = 22;
	int LITERAL_between = 23;
	int LITERAL_in = 24;
	int LITERAL_like = 25;
	int STRING_LITERAL = 26;
	int LITERAL_escape = 27;
	int COMMA = 28;
	int NUM_INT = 29;
	int NUM_FLOAT = 30;
	int LITERAL_false = 31;
	int LITERAL_true = 32;
	int WS = 33;
	int HEX_DIGIT = 34;
	int EXPONENT = 35;
	int FLOAT_SUFFIX = 36;
	int IDENT_START = 37;
	int IDENT_NON_START = 38;
	int IDENT_PART = 39;
}
