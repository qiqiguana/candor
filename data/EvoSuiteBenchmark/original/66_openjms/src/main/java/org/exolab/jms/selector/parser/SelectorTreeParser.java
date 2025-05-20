// $ANTLR 2.7.6 (2005-12-22): "SelectorTree.g" -> "SelectorTreeParser.java"$

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

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


public class SelectorTreeParser extends antlr.TreeParser       implements SelectorTreeParserTokenTypes
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
public SelectorTreeParser() {
	tokenNames = _tokenNames;
}

	public final Expression  selector(AST _t) throws RecognitionException {
		Expression expr;
		
		SelectorAST selector_AST_in = (_t == ASTNULL) ? null : (SelectorAST)_t;
		SelectorAST p = null;
		
				expr = null; 
			
		
		try {      // for error handling
			p = _t==ASTNULL ? null : (SelectorAST)_t;
			expr=primaryExpression(_t);
			_t = _retTree;
			
						TypeChecker.check(p, Type.BOOLEAN);
					
		}
		catch (NoViableAltException error) {
			
					rethrow(error.getMessage(), error.node, error.token);
				
		}
		catch (MismatchedTokenException error) {
			
					rethrow(error.getMessage(), error.node, error.token);
				
		}
		_retTree = _t;
		return expr;
	}
	
	public final Expression  primaryExpression(AST _t) throws RecognitionException {
		Expression expr;
		
		SelectorAST primaryExpression_AST_in = (_t == ASTNULL) ? null : (SelectorAST)_t;
		SelectorAST p = null;
		SelectorAST orl = null;
		SelectorAST orr = null;
		SelectorAST andl = null;
		SelectorAST andr = null;
		SelectorAST eql = null;
		SelectorAST eqr = null;
		SelectorAST nel = null;
		SelectorAST ner = null;
		SelectorAST ltl = null;
		SelectorAST ltr = null;
		SelectorAST gtl = null;
		SelectorAST gtr = null;
		SelectorAST lel = null;
		SelectorAST ler = null;
		SelectorAST gel = null;
		SelectorAST ger = null;
		
				expr = null; 
				AST ast = primaryExpression_AST_in;
				SelectorAST left = null;
				SelectorAST right = null;
				Expression lhs = null;
				Expression rhs = null;
		
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case LITERAL_not:
		{
			AST __t102 = _t;
			SelectorAST tmp1_AST_in = (SelectorAST)_t;
			match(_t,LITERAL_not);
			_t = _t.getFirstChild();
			p = _t==ASTNULL ? null : (SelectorAST)_t;
			expr=primaryExpression(_t);
			_t = _retTree;
			_t = __t102;
			_t = _t.getNextSibling();
			
						TypeChecker.check(ast.getText(), p, Type.BOOLEAN);
						expr = _factory.unaryOperator(ast.getType(), expr);
					
			break;
		}
		case LITERAL_or:
		case LITERAL_and:
		{
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LITERAL_or:
			{
				AST __t104 = _t;
				SelectorAST tmp2_AST_in = (SelectorAST)_t;
				match(_t,LITERAL_or);
				_t = _t.getFirstChild();
				orl = _t==ASTNULL ? null : (SelectorAST)_t;
				lhs=primaryExpression(_t);
				_t = _retTree;
				orr = _t==ASTNULL ? null : (SelectorAST)_t;
				rhs=primaryExpression(_t);
				_t = _retTree;
				_t = __t104;
				_t = _t.getNextSibling();
				
								TypeChecker.check(ast.getText(), orl, orr, Type.BOOLEAN);
							
				break;
			}
			case LITERAL_and:
			{
				AST __t105 = _t;
				SelectorAST tmp3_AST_in = (SelectorAST)_t;
				match(_t,LITERAL_and);
				_t = _t.getFirstChild();
				andl = _t==ASTNULL ? null : (SelectorAST)_t;
				lhs=primaryExpression(_t);
				_t = _retTree;
				andr = _t==ASTNULL ? null : (SelectorAST)_t;
				rhs=primaryExpression(_t);
				_t = _retTree;
				_t = __t105;
				_t = _t.getNextSibling();
				
								TypeChecker.check(ast.getText(), andl, andr, Type.BOOLEAN);
							
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			
						expr = _factory.binaryOperator(ast.getType(), lhs, rhs);
					
			break;
		}
		case EQUAL:
		case NOT_EQUAL:
		{
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case EQUAL:
			{
				AST __t107 = _t;
				SelectorAST tmp4_AST_in = (SelectorAST)_t;
				match(_t,EQUAL);
				_t = _t.getFirstChild();
				eql = _t==ASTNULL ? null : (SelectorAST)_t;
				lhs=expression(_t);
				_t = _retTree;
				eqr = _t==ASTNULL ? null : (SelectorAST)_t;
				rhs=expression(_t);
				_t = _retTree;
				_t = __t107;
				_t = _t.getNextSibling();
				
								TypeChecker.checkComparison(ast.getText(), eql, eqr);
							
				break;
			}
			case NOT_EQUAL:
			{
				AST __t108 = _t;
				SelectorAST tmp5_AST_in = (SelectorAST)_t;
				match(_t,NOT_EQUAL);
				_t = _t.getFirstChild();
				nel = _t==ASTNULL ? null : (SelectorAST)_t;
				lhs=expression(_t);
				_t = _retTree;
				ner = _t==ASTNULL ? null : (SelectorAST)_t;
				rhs=expression(_t);
				_t = _retTree;
				_t = __t108;
				_t = _t.getNextSibling();
				
								TypeChecker.checkComparison(ast.getText(), nel, ner);
							
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			
						expr = _factory.binaryOperator(ast.getType(), lhs, rhs);
					
			break;
		}
		case LT:
		case GT:
		case LE:
		case GE:
		{
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case LT:
			{
				AST __t110 = _t;
				SelectorAST tmp6_AST_in = (SelectorAST)_t;
				match(_t,LT);
				_t = _t.getFirstChild();
				ltl = _t==ASTNULL ? null : (SelectorAST)_t;
				lhs=expression(_t);
				_t = _retTree;
				ltr = _t==ASTNULL ? null : (SelectorAST)_t;
				rhs=expression(_t);
				_t = _retTree;
				_t = __t110;
				_t = _t.getNextSibling();
				
								TypeChecker.check(ast.getText(), ltl, ltr, Type.NUMERIC);
							
				break;
			}
			case GT:
			{
				AST __t111 = _t;
				SelectorAST tmp7_AST_in = (SelectorAST)_t;
				match(_t,GT);
				_t = _t.getFirstChild();
				gtl = _t==ASTNULL ? null : (SelectorAST)_t;
				lhs=expression(_t);
				_t = _retTree;
				gtr = _t==ASTNULL ? null : (SelectorAST)_t;
				rhs=expression(_t);
				_t = _retTree;
				_t = __t111;
				_t = _t.getNextSibling();
				
								TypeChecker.check(ast.getText(), gtl, gtr, Type.NUMERIC);
							
				break;
			}
			case LE:
			{
				AST __t112 = _t;
				SelectorAST tmp8_AST_in = (SelectorAST)_t;
				match(_t,LE);
				_t = _t.getFirstChild();
				lel = _t==ASTNULL ? null : (SelectorAST)_t;
				lhs=expression(_t);
				_t = _retTree;
				ler = _t==ASTNULL ? null : (SelectorAST)_t;
				rhs=expression(_t);
				_t = _retTree;
				_t = __t112;
				_t = _t.getNextSibling();
				
								TypeChecker.check(ast.getText(), lel, ler, Type.NUMERIC);
							
				break;
			}
			case GE:
			{
				AST __t113 = _t;
				SelectorAST tmp9_AST_in = (SelectorAST)_t;
				match(_t,GE);
				_t = _t.getFirstChild();
				gel = _t==ASTNULL ? null : (SelectorAST)_t;
				lhs=expression(_t);
				_t = _retTree;
				ger = _t==ASTNULL ? null : (SelectorAST)_t;
				rhs=expression(_t);
				_t = _retTree;
				_t = __t113;
				_t = _t.getNextSibling();
				
								TypeChecker.check(ast.getText(), gel, ger, Type.NUMERIC);
							
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			
						expr = _factory.binaryOperator(ast.getType(), lhs, rhs);
					
			break;
		}
		case UNARY_MINUS:
		case PLUS:
		case MINUS:
		case MULTIPLY:
		case DIVIDE:
		case LPAREN:
		case IDENT:
		case STRING_LITERAL:
		case NUM_INT:
		case NUM_FLOAT:
		case LITERAL_false:
		case LITERAL_true:
		{
			expr=expression(_t);
			_t = _retTree;
			break;
		}
		case LITERAL_is:
		case LITERAL_between:
		case LITERAL_in:
		case LITERAL_like:
		{
			expr=booleanExpression(_t);
			_t = _retTree;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
		return expr;
	}
	
	public final Expression  expression(AST _t) throws RecognitionException {
		Expression expr;
		
		SelectorAST expression_AST_in = (_t == ASTNULL) ? null : (SelectorAST)_t;
		SelectorAST plusl = null;
		SelectorAST plusr = null;
		SelectorAST minusl = null;
		SelectorAST minusr = null;
		SelectorAST multl = null;
		SelectorAST multr = null;
		SelectorAST divl = null;
		SelectorAST divr = null;
		
				expr = null;
				AST ast = expression_AST_in;
				Expression lhs = null;
				Expression rhs = null;
			
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case PLUS:
		case MINUS:
		case MULTIPLY:
		case DIVIDE:
		{
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case PLUS:
			{
				AST __t116 = _t;
				SelectorAST tmp10_AST_in = (SelectorAST)_t;
				match(_t,PLUS);
				_t = _t.getFirstChild();
				plusl = _t==ASTNULL ? null : (SelectorAST)_t;
				lhs=expression(_t);
				_t = _retTree;
				plusr = _t==ASTNULL ? null : (SelectorAST)_t;
				rhs=expression(_t);
				_t = _retTree;
				_t = __t116;
				_t = _t.getNextSibling();
				
								TypeChecker.check(ast.getText(), plusl, plusr, Type.NUMERIC);
							
				break;
			}
			case MINUS:
			{
				AST __t117 = _t;
				SelectorAST tmp11_AST_in = (SelectorAST)_t;
				match(_t,MINUS);
				_t = _t.getFirstChild();
				minusl = _t==ASTNULL ? null : (SelectorAST)_t;
				lhs=expression(_t);
				_t = _retTree;
				minusr = _t==ASTNULL ? null : (SelectorAST)_t;
				rhs=expression(_t);
				_t = _retTree;
				_t = __t117;
				_t = _t.getNextSibling();
				
								TypeChecker.check(ast.getText(), minusl, minusr, Type.NUMERIC);
							
				break;
			}
			case MULTIPLY:
			{
				AST __t118 = _t;
				SelectorAST tmp12_AST_in = (SelectorAST)_t;
				match(_t,MULTIPLY);
				_t = _t.getFirstChild();
				multl = _t==ASTNULL ? null : (SelectorAST)_t;
				lhs=expression(_t);
				_t = _retTree;
				multr = _t==ASTNULL ? null : (SelectorAST)_t;
				rhs=expression(_t);
				_t = _retTree;
				_t = __t118;
				_t = _t.getNextSibling();
				
								TypeChecker.check(ast.getText(), multl, multr, Type.NUMERIC);
							
				break;
			}
			case DIVIDE:
			{
				AST __t119 = _t;
				SelectorAST tmp13_AST_in = (SelectorAST)_t;
				match(_t,DIVIDE);
				_t = _t.getFirstChild();
				divl = _t==ASTNULL ? null : (SelectorAST)_t;
				lhs=expression(_t);
				_t = _retTree;
				divr = _t==ASTNULL ? null : (SelectorAST)_t;
				rhs=expression(_t);
				_t = _retTree;
				_t = __t119;
				_t = _t.getNextSibling();
				
								TypeChecker.check(ast.getText(), divl, divr, Type.NUMERIC);
							
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			
						expr = _factory.binaryOperator(ast.getType(), lhs, rhs);
					
			break;
		}
		case UNARY_MINUS:
		case LPAREN:
		case IDENT:
		case STRING_LITERAL:
		case NUM_INT:
		case NUM_FLOAT:
		case LITERAL_false:
		case LITERAL_true:
		{
			expr=term(_t);
			_t = _retTree;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
		return expr;
	}
	
	public final Expression  booleanExpression(AST _t) throws RecognitionException {
		Expression expr;
		
		SelectorAST booleanExpression_AST_in = (_t == ASTNULL) ? null : (SelectorAST)_t;
		
				expr = null;
			
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case LITERAL_is:
		{
			expr=isExpression(_t);
			_t = _retTree;
			break;
		}
		case LITERAL_between:
		{
			expr=betweenExpression(_t);
			_t = _retTree;
			break;
		}
		case LITERAL_like:
		{
			expr=likeExpression(_t);
			_t = _retTree;
			break;
		}
		case LITERAL_in:
		{
			expr=inExpression(_t);
			_t = _retTree;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
		return expr;
	}
	
	public final Expression  term(AST _t) throws RecognitionException {
		Expression expr;
		
		SelectorAST term_AST_in = (_t == ASTNULL) ? null : (SelectorAST)_t;
		SelectorAST ident = null;
		
				expr = null;
			
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case UNARY_MINUS:
		{
			AST __t134 = _t;
			SelectorAST tmp14_AST_in = (SelectorAST)_t;
			match(_t,UNARY_MINUS);
			_t = _t.getFirstChild();
			expr=unaryTerm(_t);
			_t = _retTree;
			_t = __t134;
			_t = _t.getNextSibling();
			break;
		}
		case LPAREN:
		{
			SelectorAST tmp15_AST_in = (SelectorAST)_t;
			match(_t,LPAREN);
			_t = _t.getNextSibling();
			expr=primaryExpression(_t);
			_t = _retTree;
			SelectorAST tmp16_AST_in = (SelectorAST)_t;
			match(_t,RPAREN);
			_t = _t.getNextSibling();
			break;
		}
		case IDENT:
		{
			ident = (SelectorAST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			
						expr = _factory.identifier(ident.getText());
					
			break;
		}
		case STRING_LITERAL:
		case NUM_INT:
		case NUM_FLOAT:
		case LITERAL_false:
		case LITERAL_true:
		{
			expr=literal(_t);
			_t = _retTree;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
		return expr;
	}
	
	public final Expression  isExpression(AST _t) throws RecognitionException {
		Expression expr;
		
		SelectorAST isExpression_AST_in = (_t == ASTNULL) ? null : (SelectorAST)_t;
		SelectorAST id = null;
		
				expr = null;
			
		
		AST __t122 = _t;
		SelectorAST tmp17_AST_in = (SelectorAST)_t;
		match(_t,LITERAL_is);
		_t = _t.getFirstChild();
		id = (SelectorAST)_t;
		match(_t,IDENT);
		_t = _t.getNextSibling();
		SelectorAST tmp18_AST_in = (SelectorAST)_t;
		match(_t,LITERAL_null);
		_t = _t.getNextSibling();
		_t = __t122;
		_t = _t.getNextSibling();
		
					Expression ident = _factory.identifier(id.getText());
					expr = _factory.isNull(ident);
				
		_retTree = _t;
		return expr;
	}
	
	public final Expression  betweenExpression(AST _t) throws RecognitionException {
		Expression expr;
		
		SelectorAST betweenExpression_AST_in = (_t == ASTNULL) ? null : (SelectorAST)_t;
		SelectorAST e = null;
		SelectorAST s1 = null;
		SelectorAST s2 = null;
		
				expr = null;
				Expression sum1 = null;
				Expression sum2 = null;
			
		
		AST __t124 = _t;
		SelectorAST tmp19_AST_in = (SelectorAST)_t;
		match(_t,LITERAL_between);
		_t = _t.getFirstChild();
		e = _t==ASTNULL ? null : (SelectorAST)_t;
		expr=primaryExpression(_t);
		_t = _retTree;
		s1 = _t==ASTNULL ? null : (SelectorAST)_t;
		sum1=primaryExpression(_t);
		_t = _retTree;
		s2 = _t==ASTNULL ? null : (SelectorAST)_t;
		sum2=primaryExpression(_t);
		_t = _retTree;
		_t = __t124;
		_t = _t.getNextSibling();
		
					TypeChecker.check(e, Type.NUMERIC);
					TypeChecker.check(s1, Type.NUMERIC);
					TypeChecker.check(s2, Type.NUMERIC);
					expr = _factory.between(expr, sum1, sum2);
				
		_retTree = _t;
		return expr;
	}
	
	public final Expression  likeExpression(AST _t) throws RecognitionException {
		Expression expr;
		
		SelectorAST likeExpression_AST_in = (_t == ASTNULL) ? null : (SelectorAST)_t;
		SelectorAST id = null;
		SelectorAST pat = null;
		SelectorAST esc = null;
		
				expr = null;
			
		
		AST __t126 = _t;
		SelectorAST tmp20_AST_in = (SelectorAST)_t;
		match(_t,LITERAL_like);
		_t = _t.getFirstChild();
		id = (SelectorAST)_t;
		match(_t,IDENT);
		_t = _t.getNextSibling();
		pat = (SelectorAST)_t;
		match(_t,STRING_LITERAL);
		_t = _t.getNextSibling();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case LITERAL_escape:
		{
			SelectorAST tmp21_AST_in = (SelectorAST)_t;
			match(_t,LITERAL_escape);
			_t = _t.getNextSibling();
			esc = (SelectorAST)_t;
			match(_t,STRING_LITERAL);
			_t = _t.getNextSibling();
			break;
		}
		case 3:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_t = __t126;
		_t = _t.getNextSibling();
		
					TypeChecker.check(id, Type.STRING);
					PatternValidator.validate(pat, esc);
		
					Expression ident = _factory.identifier(id.getText());
					String escape = (esc != null) ? esc.getText() : null;
					expr = _factory.like(ident, pat.getText(), escape);
				
		_retTree = _t;
		return expr;
	}
	
	public final Expression  inExpression(AST _t) throws RecognitionException {
		Expression expr;
		
		SelectorAST inExpression_AST_in = (_t == ASTNULL) ? null : (SelectorAST)_t;
		SelectorAST id = null;
		
				expr = null;
				HashSet set;
			
		
		AST __t129 = _t;
		SelectorAST tmp22_AST_in = (SelectorAST)_t;
		match(_t,LITERAL_in);
		_t = _t.getFirstChild();
		id = (SelectorAST)_t;
		match(_t,IDENT);
		_t = _t.getNextSibling();
		SelectorAST tmp23_AST_in = (SelectorAST)_t;
		match(_t,LPAREN);
		_t = _t.getNextSibling();
		set=valueList(_t);
		_t = _retTree;
		SelectorAST tmp24_AST_in = (SelectorAST)_t;
		match(_t,RPAREN);
		_t = _t.getNextSibling();
		_t = __t129;
		_t = _t.getNextSibling();
		
					TypeChecker.check(id, Type.STRING);
		
					Expression ident = _factory.identifier(id.getText());
					expr = _factory.in(ident, set);
				
		_retTree = _t;
		return expr;
	}
	
	public final HashSet  valueList(AST _t) throws RecognitionException {
		HashSet set;
		
		SelectorAST valueList_AST_in = (_t == ASTNULL) ? null : (SelectorAST)_t;
		SelectorAST first = null;
		SelectorAST next = null;
		
				set = new HashSet();
			
		
		first = (SelectorAST)_t;
		match(_t,STRING_LITERAL);
		_t = _t.getNextSibling();
		set.add(first.getText());
		{
		_loop132:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_t.getType()==STRING_LITERAL)) {
				next = (SelectorAST)_t;
				match(_t,STRING_LITERAL);
				_t = _t.getNextSibling();
				set.add(next.getText());
			}
			else {
				break _loop132;
			}
			
		} while (true);
		}
		_retTree = _t;
		return set;
	}
	
	public final Expression  unaryTerm(AST _t) throws RecognitionException {
		Expression expr;
		
		SelectorAST unaryTerm_AST_in = (_t == ASTNULL) ? null : (SelectorAST)_t;
		SelectorAST minus = null;
		SelectorAST term = null;
		SelectorAST primary = null;
		
				expr = null;
				SelectorAST ast = unaryTerm_AST_in;
			
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case UNARY_MINUS:
		{
			AST __t136 = _t;
			minus = _t==ASTNULL ? null :(SelectorAST)_t;
			match(_t,UNARY_MINUS);
			_t = _t.getFirstChild();
			term = _t==ASTNULL ? null : (SelectorAST)_t;
			expr=term(_t);
			_t = _retTree;
			_t = __t136;
			_t = _t.getNextSibling();
			
						TypeChecker.check(ast.getText(), term, Type.NUMERIC);
					
			break;
		}
		case LPAREN:
		{
			SelectorAST tmp25_AST_in = (SelectorAST)_t;
			match(_t,LPAREN);
			_t = _t.getNextSibling();
			primary = _t==ASTNULL ? null : (SelectorAST)_t;
			expr=primaryExpression(_t);
			_t = _retTree;
			SelectorAST tmp26_AST_in = (SelectorAST)_t;
			match(_t,RPAREN);
			_t = _t.getNextSibling();
			
						TypeChecker.check(primary.getText(), primary, Type.NUMERIC);
						expr = _factory.unaryOperator(SelectorTokenTypes.UNARY_MINUS, 
			expr);
			
			break;
		}
		case IDENT:
		{
			SelectorAST tmp27_AST_in = (SelectorAST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			
						expr = _factory.identifier(ast.getText());
						TypeChecker.check(ast.getText(), ast, Type.NUMERIC);
						expr = _factory.unaryOperator(SelectorTokenTypes.UNARY_MINUS, 
			expr);
					
			break;
		}
		case NUM_INT:
		case NUM_FLOAT:
		{
			{
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case NUM_INT:
			{
				SelectorAST tmp28_AST_in = (SelectorAST)_t;
				match(_t,NUM_INT);
				_t = _t.getNextSibling();
				break;
			}
			case NUM_FLOAT:
			{
				SelectorAST tmp29_AST_in = (SelectorAST)_t;
				match(_t,NUM_FLOAT);
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
			}
			
						expr = _factory.literal(ast.getType(), "-" + ast.getText());
			
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
		return expr;
	}
	
	public final Expression  literal(AST _t) throws RecognitionException {
		Expression expr;
		
		SelectorAST literal_AST_in = (_t == ASTNULL) ? null : (SelectorAST)_t;
		
				expr = null;	
				AST ast = literal_AST_in;
			
		
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case NUM_INT:
		{
			SelectorAST tmp30_AST_in = (SelectorAST)_t;
			match(_t,NUM_INT);
			_t = _t.getNextSibling();
			break;
		}
		case NUM_FLOAT:
		{
			SelectorAST tmp31_AST_in = (SelectorAST)_t;
			match(_t,NUM_FLOAT);
			_t = _t.getNextSibling();
			break;
		}
		case STRING_LITERAL:
		{
			SelectorAST tmp32_AST_in = (SelectorAST)_t;
			match(_t,STRING_LITERAL);
			_t = _t.getNextSibling();
			break;
		}
		case LITERAL_false:
		{
			SelectorAST tmp33_AST_in = (SelectorAST)_t;
			match(_t,LITERAL_false);
			_t = _t.getNextSibling();
			break;
		}
		case LITERAL_true:
		{
			SelectorAST tmp34_AST_in = (SelectorAST)_t;
			match(_t,LITERAL_true);
			_t = _t.getNextSibling();
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		
					expr = _factory.literal(ast.getType(), ast.getText());
				
		_retTree = _t;
		return expr;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"UNARY_MINUS",
		"\"or\"",
		"\"and\"",
		"\"not\"",
		"=",
		"<>",
		"<",
		">",
		"<=",
		">=",
		"+",
		"-",
		"*",
		"/",
		"(",
		")",
		"an identifier",
		"\"is\"",
		"\"null\"",
		"\"between\"",
		"\"in\"",
		"\"like\"",
		"a string literal",
		"\"escape\"",
		",",
		"an integer",
		"NUM_FLOAT",
		"\"false\"",
		"\"true\"",
		"WS",
		"HEX_DIGIT",
		"EXPONENT",
		"FLOAT_SUFFIX",
		"IDENT_START",
		"IDENT_NON_START",
		"IDENT_PART"
	};
	
	}
	
