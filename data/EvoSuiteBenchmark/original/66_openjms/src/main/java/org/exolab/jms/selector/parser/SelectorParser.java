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

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class SelectorParser extends antlr.LLkParser       implements SelectorTokenTypes
 {

    public void initialise() {
        setASTFactory(new SelectorASTFactory());

        // construct SelectorAST nodes
        setASTNodeClass(SelectorAST.class.getName());
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

protected SelectorParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public SelectorParser(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected SelectorParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public SelectorParser(TokenStream lexer) {
  this(lexer,2);
}

public SelectorParser(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void selector() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST selector_AST = null;
		
		try {      // for error handling
			orExpression();
			astFactory.addASTChild(currentAST, returnAST);
			match(Token.EOF_TYPE);
			selector_AST = (SelectorAST)currentAST.root;
		}
		catch (NoViableAltException error) {
			
			rethrow(error.getMessage(), error.node, error.token);
			
		}
		catch (MismatchedTokenException error) {
			
			rethrow(error.getMessage(), error.node, error.token);
			
		}
		returnAST = selector_AST;
	}
	
	public final void orExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST orExpression_AST = null;
		
		andExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop4:
		do {
			if ((LA(1)==LITERAL_or)) {
				SelectorAST tmp2_AST = null;
				tmp2_AST = (SelectorAST)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp2_AST);
				match(LITERAL_or);
				orExpression_AST = (SelectorAST)currentAST.root;
				
				orExpression_AST.setReturnType(Type.BOOLEAN); 
				
				andExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop4;
			}
			
		} while (true);
		}
		orExpression_AST = (SelectorAST)currentAST.root;
		returnAST = orExpression_AST;
	}
	
	public final void andExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST andExpression_AST = null;
		
		notExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop7:
		do {
			if ((LA(1)==LITERAL_and)) {
				SelectorAST tmp3_AST = null;
				tmp3_AST = (SelectorAST)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp3_AST);
				match(LITERAL_and);
				andExpression_AST = (SelectorAST)currentAST.root;
				
				andExpression_AST.setReturnType(Type.BOOLEAN); 
				
				notExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop7;
			}
			
		} while (true);
		}
		andExpression_AST = (SelectorAST)currentAST.root;
		returnAST = andExpression_AST;
	}
	
	public final void notExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST notExpression_AST = null;
		
		{
		switch ( LA(1)) {
		case LITERAL_not:
		{
			SelectorAST tmp4_AST = null;
			tmp4_AST = (SelectorAST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp4_AST);
			match(LITERAL_not);
			notExpression_AST = (SelectorAST)currentAST.root;
			
			notExpression_AST.setReturnType(Type.BOOLEAN); 
			
			break;
		}
		case PLUS:
		case MINUS:
		case LPAREN:
		case IDENT:
		case STRING_LITERAL:
		case NUM_INT:
		case NUM_FLOAT:
		case LITERAL_false:
		case LITERAL_true:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		expression();
		astFactory.addASTChild(currentAST, returnAST);
		notExpression_AST = (SelectorAST)currentAST.root;
		returnAST = notExpression_AST;
	}
	
	public final void expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST expression_AST = null;
		SelectorAST expr_AST = null;
		SelectorAST bool_AST = null;
		SelectorAST comp_AST = null;
		
		sumExpression();
		expr_AST = (SelectorAST)returnAST;
		{
		switch ( LA(1)) {
		case LITERAL_not:
		case LITERAL_is:
		case LITERAL_between:
		case LITERAL_in:
		case LITERAL_like:
		{
			booleanExpression(expr_AST);
			bool_AST = (SelectorAST)returnAST;
			expr_AST = bool_AST;
			break;
		}
		case EQUAL:
		case NOT_EQUAL:
		case LT:
		case GT:
		case LE:
		case GE:
		{
			comparisonExpression(expr_AST);
			comp_AST = (SelectorAST)returnAST;
			expr_AST = comp_AST;
			break;
		}
		case EOF:
		case LITERAL_or:
		case LITERAL_and:
		case RPAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		expression_AST = (SelectorAST)currentAST.root;
		
		expression_AST = expr_AST;
		
		currentAST.root = expression_AST;
		currentAST.child = expression_AST!=null &&expression_AST.getFirstChild()!=null ?
			expression_AST.getFirstChild() : expression_AST;
		currentAST.advanceChildToEnd();
		returnAST = expression_AST;
	}
	
	public final void sumExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST sumExpression_AST = null;
		
		productExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop17:
		do {
			if ((LA(1)==PLUS||LA(1)==MINUS)) {
				{
				switch ( LA(1)) {
				case PLUS:
				{
					SelectorAST tmp5_AST = null;
					tmp5_AST = (SelectorAST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp5_AST);
					match(PLUS);
					break;
				}
				case MINUS:
				{
					SelectorAST tmp6_AST = null;
					tmp6_AST = (SelectorAST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp6_AST);
					match(MINUS);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				sumExpression_AST = (SelectorAST)currentAST.root;
				
				sumExpression_AST.setReturnType(Type.NUMERIC); 
				
				productExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop17;
			}
			
		} while (true);
		}
		sumExpression_AST = (SelectorAST)currentAST.root;
		returnAST = sumExpression_AST;
	}
	
	public final void booleanExpression(
		SelectorAST lhs
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST booleanExpression_AST = null;
		
		switch ( LA(1)) {
		case LITERAL_is:
		{
			isExpression(lhs);
			astFactory.addASTChild(currentAST, returnAST);
			booleanExpression_AST = (SelectorAST)currentAST.root;
			break;
		}
		case LITERAL_not:
		case LITERAL_between:
		case LITERAL_in:
		case LITERAL_like:
		{
			{
			switch ( LA(1)) {
			case LITERAL_not:
			{
				SelectorAST tmp7_AST = null;
				tmp7_AST = (SelectorAST)astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp7_AST);
				match(LITERAL_not);
				break;
			}
			case LITERAL_between:
			case LITERAL_in:
			case LITERAL_like:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case LITERAL_between:
			{
				betweenExpression(lhs);
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_like:
			{
				likeExpression(lhs);
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case LITERAL_in:
			{
				inExpression(lhs);
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			booleanExpression_AST = (SelectorAST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = booleanExpression_AST;
	}
	
	public final void comparisonExpression(
		SelectorAST lhs
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST comparisonExpression_AST = null;
		SelectorAST eq_AST = null;
		SelectorAST ne_AST = null;
		SelectorAST lt_AST = null;
		SelectorAST gt_AST = null;
		SelectorAST le_AST = null;
		SelectorAST ge_AST = null;
		
		{
		switch ( LA(1)) {
		case EQUAL:
		{
			SelectorAST tmp8_AST = null;
			tmp8_AST = (SelectorAST)astFactory.create(LT(1));
			match(EQUAL);
			sumExpression();
			eq_AST = (SelectorAST)returnAST;
			comparisonExpression_AST = (SelectorAST)currentAST.root;
			
			comparisonExpression_AST = (SelectorAST)astFactory.make( (new ASTArray(3)).add(tmp8_AST).add(lhs).add(eq_AST)); 
			
			currentAST.root = comparisonExpression_AST;
			currentAST.child = comparisonExpression_AST!=null &&comparisonExpression_AST.getFirstChild()!=null ?
				comparisonExpression_AST.getFirstChild() : comparisonExpression_AST;
			currentAST.advanceChildToEnd();
			break;
		}
		case NOT_EQUAL:
		{
			SelectorAST tmp9_AST = null;
			tmp9_AST = (SelectorAST)astFactory.create(LT(1));
			match(NOT_EQUAL);
			sumExpression();
			ne_AST = (SelectorAST)returnAST;
			comparisonExpression_AST = (SelectorAST)currentAST.root;
			
			comparisonExpression_AST = (SelectorAST)astFactory.make( (new ASTArray(3)).add(tmp9_AST).add(lhs).add(ne_AST)); 
			
			currentAST.root = comparisonExpression_AST;
			currentAST.child = comparisonExpression_AST!=null &&comparisonExpression_AST.getFirstChild()!=null ?
				comparisonExpression_AST.getFirstChild() : comparisonExpression_AST;
			currentAST.advanceChildToEnd();
			break;
		}
		case LT:
		{
			SelectorAST tmp10_AST = null;
			tmp10_AST = (SelectorAST)astFactory.create(LT(1));
			match(LT);
			sumExpression();
			lt_AST = (SelectorAST)returnAST;
			comparisonExpression_AST = (SelectorAST)currentAST.root;
			
			comparisonExpression_AST = (SelectorAST)astFactory.make( (new ASTArray(3)).add(tmp10_AST).add(lhs).add(lt_AST)); 
			
			currentAST.root = comparisonExpression_AST;
			currentAST.child = comparisonExpression_AST!=null &&comparisonExpression_AST.getFirstChild()!=null ?
				comparisonExpression_AST.getFirstChild() : comparisonExpression_AST;
			currentAST.advanceChildToEnd();
			break;
		}
		case GT:
		{
			SelectorAST tmp11_AST = null;
			tmp11_AST = (SelectorAST)astFactory.create(LT(1));
			match(GT);
			sumExpression();
			gt_AST = (SelectorAST)returnAST;
			comparisonExpression_AST = (SelectorAST)currentAST.root;
			
			comparisonExpression_AST = (SelectorAST)astFactory.make( (new ASTArray(3)).add(tmp11_AST).add(lhs).add(gt_AST)); 
			
			currentAST.root = comparisonExpression_AST;
			currentAST.child = comparisonExpression_AST!=null &&comparisonExpression_AST.getFirstChild()!=null ?
				comparisonExpression_AST.getFirstChild() : comparisonExpression_AST;
			currentAST.advanceChildToEnd();
			break;
		}
		case LE:
		{
			SelectorAST tmp12_AST = null;
			tmp12_AST = (SelectorAST)astFactory.create(LT(1));
			match(LE);
			sumExpression();
			le_AST = (SelectorAST)returnAST;
			comparisonExpression_AST = (SelectorAST)currentAST.root;
			
			comparisonExpression_AST = (SelectorAST)astFactory.make( (new ASTArray(3)).add(tmp12_AST).add(lhs).add(le_AST)); 
			
			currentAST.root = comparisonExpression_AST;
			currentAST.child = comparisonExpression_AST!=null &&comparisonExpression_AST.getFirstChild()!=null ?
				comparisonExpression_AST.getFirstChild() : comparisonExpression_AST;
			currentAST.advanceChildToEnd();
			break;
		}
		case GE:
		{
			SelectorAST tmp13_AST = null;
			tmp13_AST = (SelectorAST)astFactory.create(LT(1));
			match(GE);
			sumExpression();
			ge_AST = (SelectorAST)returnAST;
			comparisonExpression_AST = (SelectorAST)currentAST.root;
			
			comparisonExpression_AST = (SelectorAST)astFactory.make( (new ASTArray(3)).add(tmp13_AST).add(lhs).add(ge_AST)); 
			
			currentAST.root = comparisonExpression_AST;
			currentAST.child = comparisonExpression_AST!=null &&comparisonExpression_AST.getFirstChild()!=null ?
				comparisonExpression_AST.getFirstChild() : comparisonExpression_AST;
			currentAST.advanceChildToEnd();
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		comparisonExpression_AST = (SelectorAST)currentAST.root;
		
		comparisonExpression_AST.setReturnType(Type.BOOLEAN);
		
		returnAST = comparisonExpression_AST;
	}
	
	public final void productExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST productExpression_AST = null;
		
		unaryExpression();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop21:
		do {
			if ((LA(1)==MULTIPLY||LA(1)==DIVIDE)) {
				{
				switch ( LA(1)) {
				case MULTIPLY:
				{
					SelectorAST tmp14_AST = null;
					tmp14_AST = (SelectorAST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp14_AST);
					match(MULTIPLY);
					break;
				}
				case DIVIDE:
				{
					SelectorAST tmp15_AST = null;
					tmp15_AST = (SelectorAST)astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp15_AST);
					match(DIVIDE);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				productExpression_AST = (SelectorAST)currentAST.root;
				
				productExpression_AST.setReturnType(Type.NUMERIC); 
				
				unaryExpression();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop21;
			}
			
		} while (true);
		}
		productExpression_AST = (SelectorAST)currentAST.root;
		returnAST = productExpression_AST;
	}
	
	public final void unaryExpression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST unaryExpression_AST = null;
		
		switch ( LA(1)) {
		case MINUS:
		{
			SelectorAST tmp16_AST = null;
			tmp16_AST = (SelectorAST)astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp16_AST);
			match(MINUS);
			unaryExpression_AST = (SelectorAST)currentAST.root;
			
			unaryExpression_AST.setType(UNARY_MINUS); 
			unaryExpression_AST.setReturnType(Type.NUMERIC);
			
			unaryExpression();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (SelectorAST)currentAST.root;
			break;
		}
		case PLUS:
		{
			match(PLUS);
			unaryExpression();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (SelectorAST)currentAST.root;
			break;
		}
		case LPAREN:
		case IDENT:
		case STRING_LITERAL:
		case NUM_INT:
		case NUM_FLOAT:
		case LITERAL_false:
		case LITERAL_true:
		{
			term();
			astFactory.addASTChild(currentAST, returnAST);
			unaryExpression_AST = (SelectorAST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = unaryExpression_AST;
	}
	
	public final void term() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST term_AST = null;
		Token  ident = null;
		SelectorAST ident_AST = null;
		
		switch ( LA(1)) {
		case LPAREN:
		{
			SelectorAST tmp18_AST = null;
			tmp18_AST = (SelectorAST)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp18_AST);
			match(LPAREN);
			orExpression();
			astFactory.addASTChild(currentAST, returnAST);
			SelectorAST tmp19_AST = null;
			tmp19_AST = (SelectorAST)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp19_AST);
			match(RPAREN);
			term_AST = (SelectorAST)currentAST.root;
			break;
		}
		case STRING_LITERAL:
		case NUM_INT:
		case NUM_FLOAT:
		case LITERAL_false:
		case LITERAL_true:
		{
			literal();
			astFactory.addASTChild(currentAST, returnAST);
			term_AST = (SelectorAST)currentAST.root;
			break;
		}
		case IDENT:
		{
			ident = LT(1);
			ident_AST = (SelectorAST)astFactory.create(ident);
			astFactory.addASTChild(currentAST, ident_AST);
			match(IDENT);
			
			String name = ident.getText();
			if (Identifiers.isJMSIdentifier(name)) {
			if (Identifiers.isNumeric(name)) {
			ident_AST.setReturnType(Type.NUMERIC);
			} else if (Identifiers.isString(name)) {
			ident_AST.setReturnType(Type.STRING);
			} else {
			String msg = "invalid message header identifier: " + name;
			throw new SelectorException(ident_AST.getContext(), msg);
			}
			}
			
			term_AST = (SelectorAST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = term_AST;
	}
	
	public final void literal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST literal_AST = null;
		
		switch ( LA(1)) {
		case NUM_INT:
		{
			SelectorAST tmp20_AST = null;
			tmp20_AST = (SelectorAST)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp20_AST);
			match(NUM_INT);
			literal_AST = (SelectorAST)currentAST.root;
			
			literal_AST.setReturnType(Type.NUMERIC);
			
			literal_AST = (SelectorAST)currentAST.root;
			break;
		}
		case NUM_FLOAT:
		{
			SelectorAST tmp21_AST = null;
			tmp21_AST = (SelectorAST)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp21_AST);
			match(NUM_FLOAT);
			literal_AST = (SelectorAST)currentAST.root;
			
			literal_AST.setReturnType(Type.NUMERIC);
			
			literal_AST = (SelectorAST)currentAST.root;
			break;
		}
		case STRING_LITERAL:
		{
			SelectorAST tmp22_AST = null;
			tmp22_AST = (SelectorAST)astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp22_AST);
			match(STRING_LITERAL);
			literal_AST = (SelectorAST)currentAST.root;
			
			literal_AST.setReturnType(Type.STRING); 
			
			literal_AST = (SelectorAST)currentAST.root;
			break;
		}
		case LITERAL_false:
		case LITERAL_true:
		{
			{
			switch ( LA(1)) {
			case LITERAL_false:
			{
				SelectorAST tmp23_AST = null;
				tmp23_AST = (SelectorAST)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp23_AST);
				match(LITERAL_false);
				break;
			}
			case LITERAL_true:
			{
				SelectorAST tmp24_AST = null;
				tmp24_AST = (SelectorAST)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp24_AST);
				match(LITERAL_true);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			literal_AST = (SelectorAST)currentAST.root;
			
			literal_AST.setReturnType(Type.BOOLEAN);
			
			literal_AST = (SelectorAST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = literal_AST;
	}
	
	public final void isExpression(
		SelectorAST lhs
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST isExpression_AST = null;
		Token  is = null;
		SelectorAST is_AST = null;
		Token  not = null;
		SelectorAST not_AST = null;
		Token  nul = null;
		SelectorAST nul_AST = null;
		
		is = LT(1);
		is_AST = (SelectorAST)astFactory.create(is);
		match(LITERAL_is);
		{
		switch ( LA(1)) {
		case LITERAL_not:
		{
			not = LT(1);
			not_AST = (SelectorAST)astFactory.create(not);
			match(LITERAL_not);
			break;
		}
		case LITERAL_null:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		nul = LT(1);
		nul_AST = (SelectorAST)astFactory.create(nul);
		match(LITERAL_null);
		isExpression_AST = (SelectorAST)currentAST.root;
		
		isExpression_AST = (SelectorAST)astFactory.make( (new ASTArray(3)).add(is_AST).add(lhs).add(nul_AST));
		if (not != null)
		{
		isExpression_AST = (SelectorAST)astFactory.make( (new ASTArray(2)).add(not_AST).add(isExpression_AST));
		isExpression_AST.setReturnType(Type.BOOLEAN);
		}
		isExpression_AST.setReturnType(Type.BOOLEAN);
		
		currentAST.root = isExpression_AST;
		currentAST.child = isExpression_AST!=null &&isExpression_AST.getFirstChild()!=null ?
			isExpression_AST.getFirstChild() : isExpression_AST;
		currentAST.advanceChildToEnd();
		returnAST = isExpression_AST;
	}
	
	public final void betweenExpression(
		SelectorAST lhs
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST betweenExpression_AST = null;
		Token  btw = null;
		SelectorAST btw_AST = null;
		SelectorAST sum1_AST = null;
		SelectorAST sum2_AST = null;
		
		btw = LT(1);
		btw_AST = (SelectorAST)astFactory.create(btw);
		match(LITERAL_between);
		sumExpression();
		sum1_AST = (SelectorAST)returnAST;
		match(LITERAL_and);
		sumExpression();
		sum2_AST = (SelectorAST)returnAST;
		betweenExpression_AST = (SelectorAST)currentAST.root;
		
		betweenExpression_AST = (SelectorAST)astFactory.make( (new ASTArray(4)).add(btw_AST).add(lhs).add(sum1_AST).add(sum2_AST));
		betweenExpression_AST.setReturnType(Type.BOOLEAN);
		
		currentAST.root = betweenExpression_AST;
		currentAST.child = betweenExpression_AST!=null &&betweenExpression_AST.getFirstChild()!=null ?
			betweenExpression_AST.getFirstChild() : betweenExpression_AST;
		currentAST.advanceChildToEnd();
		returnAST = betweenExpression_AST;
	}
	
	public final void likeExpression(
		SelectorAST lhs
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST likeExpression_AST = null;
		Token  like = null;
		SelectorAST like_AST = null;
		Token  pattern = null;
		SelectorAST pattern_AST = null;
		Token  esc = null;
		SelectorAST esc_AST = null;
		Token  escape = null;
		SelectorAST escape_AST = null;
		
		like = LT(1);
		like_AST = (SelectorAST)astFactory.create(like);
		match(LITERAL_like);
		pattern = LT(1);
		pattern_AST = (SelectorAST)astFactory.create(pattern);
		match(STRING_LITERAL);
		{
		switch ( LA(1)) {
		case LITERAL_escape:
		{
			esc = LT(1);
			esc_AST = (SelectorAST)astFactory.create(esc);
			match(LITERAL_escape);
			escape = LT(1);
			escape_AST = (SelectorAST)astFactory.create(escape);
			match(STRING_LITERAL);
			break;
		}
		case EOF:
		case LITERAL_or:
		case LITERAL_and:
		case RPAREN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		likeExpression_AST = (SelectorAST)currentAST.root;
		
		likeExpression_AST = (esc != null) 
		? (SelectorAST)astFactory.make( (new ASTArray(5)).add(like_AST).add(lhs).add(pattern_AST).add(esc_AST).add(escape_AST))
		: (SelectorAST)astFactory.make( (new ASTArray(3)).add(like_AST).add(lhs).add(pattern_AST));
		likeExpression_AST.setReturnType(Type.BOOLEAN);
		
		currentAST.root = likeExpression_AST;
		currentAST.child = likeExpression_AST!=null &&likeExpression_AST.getFirstChild()!=null ?
			likeExpression_AST.getFirstChild() : likeExpression_AST;
		currentAST.advanceChildToEnd();
		returnAST = likeExpression_AST;
	}
	
	public final void inExpression(
		SelectorAST lhs
	) throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST inExpression_AST = null;
		Token  in = null;
		SelectorAST in_AST = null;
		SelectorAST values_AST = null;
		
		in = LT(1);
		in_AST = (SelectorAST)astFactory.create(in);
		match(LITERAL_in);
		SelectorAST tmp26_AST = null;
		tmp26_AST = (SelectorAST)astFactory.create(LT(1));
		match(LPAREN);
		valueList();
		values_AST = (SelectorAST)returnAST;
		SelectorAST tmp27_AST = null;
		tmp27_AST = (SelectorAST)astFactory.create(LT(1));
		match(RPAREN);
		inExpression_AST = (SelectorAST)currentAST.root;
		
		inExpression_AST = (SelectorAST)astFactory.make( (new ASTArray(5)).add(in_AST).add(lhs).add(tmp26_AST).add(values_AST).add(tmp27_AST));
		inExpression_AST.setReturnType(Type.BOOLEAN);
		
		currentAST.root = inExpression_AST;
		currentAST.child = inExpression_AST!=null &&inExpression_AST.getFirstChild()!=null ?
			inExpression_AST.getFirstChild() : inExpression_AST;
		currentAST.advanceChildToEnd();
		returnAST = inExpression_AST;
	}
	
	public final void valueList() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		SelectorAST valueList_AST = null;
		
		SelectorAST tmp28_AST = null;
		tmp28_AST = (SelectorAST)astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp28_AST);
		match(STRING_LITERAL);
		{
		_loop35:
		do {
			if ((LA(1)==COMMA)) {
				match(COMMA);
				SelectorAST tmp30_AST = null;
				tmp30_AST = (SelectorAST)astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp30_AST);
				match(STRING_LITERAL);
			}
			else {
				break _loop35;
			}
			
		} while (true);
		}
		valueList_AST = (SelectorAST)currentAST.root;
		returnAST = valueList_AST;
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
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	
	}
