header
{
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

	import java.util.HashSet;

	import org.exolab.jms.selector.Context;
	import org.exolab.jms.selector.Expression;
	import org.exolab.jms.selector.ExpressionFactory;
	import org.exolab.jms.selector.SelectorException;
	import org.exolab.jms.selector.Type;

/**
 * Selector tree parser
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:45 $
 * @author      <a href="mailto:tma@netspace.net.au">Tim Anderson</a>
 * @see         SelectorParser
 * @see         SelectorTreeParser
 */
}

class SelectorTreeParser extends TreeParser;

options 
{
	importVocab = Selector;
	ASTLabelType = "SelectorAST";   // cast AST nodes to SelectorAST
	defaultErrorHandler = false;    // abort parsing on error
}

{
	/**
	 * The factory for creating expressions
	 */
	private ExpressionFactory _factory;

	public void initialise(ExpressionFactory factory) {
        _factory = factory;
    }

	private void rethrow(String msg, AST node, Token token)
		throws SelectorException {
        if (node != null) {
            throw new SelectorException(((SelectorAST) node).getContext(), 
				                        msg);
        } else {
			Context context = new Context(token.getLine(), token.getColumn());
			throw new SelectorException(context, msg);
        }
    }
}

selector returns [Expression expr]
    { 
		expr = null; 
	}
	:	expr = p:primaryExpression
		{
			TypeChecker.check(p, Type.BOOLEAN);
		}
	;
    exception
    catch [NoViableAltException error] 
    {
		rethrow(error.getMessage(), error.node, error.token);
	}
    catch [MismatchedTokenException error]
    {
		rethrow(error.getMessage(), error.node, error.token);
	}

primaryExpression returns [Expression expr]
    { 
		expr = null; 
		AST ast = ##_in;
		SelectorAST left = null;
		SelectorAST right = null;
		Expression lhs = null;
		Expression rhs = null;
    }
	:   #("not" expr = p:primaryExpression)
		{
			TypeChecker.check(ast.getText(), p, Type.BOOLEAN);
			expr = _factory.unaryOperator(ast.getType(), expr);
		}
	|
		(	#("or" lhs = orl:primaryExpression rhs = orr:primaryExpression)
			{ 
				TypeChecker.check(ast.getText(), orl, orr, Type.BOOLEAN);
			}
		|   #("and" lhs = andl:primaryExpression rhs = andr:primaryExpression)
			{
				TypeChecker.check(ast.getText(), andl, andr, Type.BOOLEAN);
			}
		)	
		{
			expr = _factory.binaryOperator(ast.getType(), lhs, rhs);
		}
	|
		(   #(EQUAL lhs = eql:expression rhs = eqr:expression)
			{
				TypeChecker.checkComparison(ast.getText(), eql, eqr);
			}
		|	#(NOT_EQUAL lhs = nel:expression rhs = ner:expression)
			{
				TypeChecker.checkComparison(ast.getText(), nel, ner);
			}
		)
		{
			expr = _factory.binaryOperator(ast.getType(), lhs, rhs);
		}
	|
		(	#(LT lhs = ltl:expression rhs = ltr:expression)
			{
				TypeChecker.check(ast.getText(), ltl, ltr, Type.NUMERIC);
			}
		|   #(GT lhs = gtl:expression rhs = gtr:expression)
			{
				TypeChecker.check(ast.getText(), gtl, gtr, Type.NUMERIC);
			}
		|	#(LE lhs = lel:expression rhs = ler:expression)
			{
				TypeChecker.check(ast.getText(), lel, ler, Type.NUMERIC);
			}
		|   #(GE lhs = gel:expression rhs = ger:expression)
			{
				TypeChecker.check(ast.getText(), gel, ger, Type.NUMERIC);
			}
		)
		{
			expr = _factory.binaryOperator(ast.getType(), lhs, rhs);
		}
	|	expr = expression
	|   expr = booleanExpression		
	;

expression returns [Expression expr]
	{
		expr = null;
		AST ast = ##_in;
		Expression lhs = null;
		Expression rhs = null;
	}
    :	(	#(PLUS lhs = plusl:expression rhs = plusr:expression)
			{
				TypeChecker.check(ast.getText(), plusl, plusr, Type.NUMERIC);
			}
		|	#(MINUS lhs = minusl:expression rhs = minusr:expression)
			{
				TypeChecker.check(ast.getText(), minusl, minusr, Type.NUMERIC);
			}
		|   #(MULTIPLY lhs = multl:expression rhs = multr:expression)
			{
				TypeChecker.check(ast.getText(), multl, multr, Type.NUMERIC);
			}
		|   #(DIVIDE lhs = divl:expression rhs = divr:expression)
			{
				TypeChecker.check(ast.getText(), divl, divr, Type.NUMERIC);
			}
		)
		{
			expr = _factory.binaryOperator(ast.getType(), lhs, rhs);
		}
	|   expr = term
	;

booleanExpression returns [Expression expr]
	{
		expr = null;
	}
	: expr = isExpression
	| expr = betweenExpression
	| expr = likeExpression
	| expr = inExpression
	;

isExpression returns [Expression expr]
	{
		expr = null;
	}
	:	#("is" id:IDENT "null")
		{
			Expression ident = _factory.identifier(#id.getText());
			expr = _factory.isNull(ident);
		}
	;

betweenExpression returns [Expression expr]
	{
		expr = null;
		Expression sum1 = null;
		Expression sum2 = null;
	}
	:   #("between" expr = e:primaryExpression 
					sum1 = s1:primaryExpression 
					sum2 = s2:primaryExpression )
		{
			TypeChecker.check(e, Type.NUMERIC);
			TypeChecker.check(s1, Type.NUMERIC);
			TypeChecker.check(s2, Type.NUMERIC);
			expr = _factory.between(expr, sum1, sum2);
		}
	;

likeExpression returns [Expression expr]
	{
		expr = null;
	}
	:	#("like" id:IDENT pat:STRING_LITERAL ( "escape" esc:STRING_LITERAL )? )
		{
			TypeChecker.check(id, Type.STRING);
			PatternValidator.validate(pat, esc);

			Expression ident = _factory.identifier(#id.getText());
			String escape = (esc != null) ? esc.getText() : null;
			expr = _factory.like(ident, #pat.getText(), escape);
		}
	;

inExpression returns [Expression expr]
	{
		expr = null;
		HashSet set;
	}
	:	#("in" id:IDENT LPAREN set = valueList RPAREN)
		{
			TypeChecker.check(id, Type.STRING);

			Expression ident = _factory.identifier(#id.getText());
			expr = _factory.in(ident, set);
		}
	;

valueList returns [HashSet set] 
	{ 
		set = new HashSet();
	}
	:	first:STRING_LITERAL { set.add(#first.getText()); }
		( next:STRING_LITERAL { set.add(#next.getText()); } ) *
	;

term returns [Expression expr]
	{
		expr = null;
	}
	:   #(UNARY_MINUS expr = unaryTerm)
	|	LPAREN expr = primaryExpression RPAREN
	|	ident:IDENT
		{ 
			expr = _factory.identifier(ident.getText());
		}
    |	expr = literal
	;

unaryTerm returns [Expression expr]
	{
		expr = null;
		SelectorAST ast = ##_in;
	}
	:   #(minus:UNARY_MINUS expr = term:term)
		{
			TypeChecker.check(ast.getText(), term, Type.NUMERIC);
		}
	|	LPAREN expr = primary:primaryExpression RPAREN
        {
			TypeChecker.check(primary.getText(), primary, Type.NUMERIC);
			expr = _factory.unaryOperator(SelectorTokenTypes.UNARY_MINUS, 
                                          expr);
        }
	|	IDENT
		{ 
			expr = _factory.identifier(ast.getText());
			TypeChecker.check(ast.getText(), ast, Type.NUMERIC);
			expr = _factory.unaryOperator(SelectorTokenTypes.UNARY_MINUS, 
                                          expr);
		}
    |   ( NUM_INT | NUM_FLOAT )
        {
			expr = _factory.literal(ast.getType(), "-" + ast.getText());
        }
	;


literal returns [Expression expr]
	{
		expr = null;	
		AST ast = ##_in;
	}
	:	( 	NUM_INT 
		|	NUM_FLOAT
		|	STRING_LITERAL
		|	"false"
		|	"true"
		)
		{
			expr = _factory.literal(ast.getType(), ast.getText());
		}
	;


