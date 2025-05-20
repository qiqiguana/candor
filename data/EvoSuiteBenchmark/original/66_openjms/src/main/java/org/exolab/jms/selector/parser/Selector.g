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
}

class SelectorParser extends Parser;

options 
{
    exportVocab = Selector;         // call this vocabulary "Selector"
    k = 2;                          // two token lookahead
    buildAST = true;                // build tree
    ASTLabelType = "SelectorAST";   // cast nodes to SelectorAST
    defaultErrorHandler = false;    // abort parsing on error
}

tokens 
{
    // tokens used in tree generation
    UNARY_MINUS;
}

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
}

selector
    :   orExpression
        EOF!
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

orExpression
    :   andExpression 
        ( "or"^ 
            { 
                ##.setReturnType(Type.BOOLEAN); 
            } 
            andExpression 
        )*
    ;

andExpression
    :   notExpression 
        ( "and"^ 
            { 
                ##.setReturnType(Type.BOOLEAN); 
            } 
            notExpression 
        )*
    ;

notExpression
    :   ( "not"^ 
            { 
                ##.setReturnType(Type.BOOLEAN); 
            } 
        ) ? 
        expression
    ;

expression!
    :   expr:sumExpression 
        (
            bool:booleanExpression[#expr] { #expr = #bool; }
        |   comp:comparisonExpression[#expr] { #expr = #comp; }
        )?
        {
            ## = #expr;
        }
    ;

comparisonExpression![SelectorAST lhs]
    :   (   EQUAL eq:sumExpression 
            { 
                ## = #(EQUAL, lhs, #eq); 
            }
        |   NOT_EQUAL ne:sumExpression 
            { 
                ## = #(NOT_EQUAL, lhs, #ne); 
            }
        |   LT lt:sumExpression 
            { 
                ## = #(LT, lhs, #lt); 
            }
        |   GT gt:sumExpression 
            { 
                ## = #(GT, lhs, #gt); 
            }
        |   LE le:sumExpression 
            { 
                ## = #(LE, lhs, #le); 
            }
        |   GE ge:sumExpression 
            { 
                ## = #(GE, lhs, #ge); 
            }
        )
        {
            ##.setReturnType(Type.BOOLEAN);
        }
    ;

sumExpression
    :   productExpression 
        (
            ( PLUS^ | MINUS^ ) 
            { 
                ##.setReturnType(Type.NUMERIC); 
            }
            productExpression 
        )*
    ;

productExpression
    :   unaryExpression 
        (( MULTIPLY^ | DIVIDE^ ) 
            { 
                ##.setReturnType(Type.NUMERIC); 
            }
            unaryExpression )*
    ;

unaryExpression
    :   MINUS^ 
        { 
            ##.setType(UNARY_MINUS); 
            ##.setReturnType(Type.NUMERIC);
        } 
        unaryExpression
    |   PLUS! unaryExpression
    |   term
    ;

term
    :   LPAREN orExpression RPAREN
    |   literal
    |   ident:IDENT
        {
            String name = ident.getText();
            if (Identifiers.isJMSIdentifier(name)) {
                if (Identifiers.isNumeric(name)) {
                    #ident.setReturnType(Type.NUMERIC);
                } else if (Identifiers.isString(name)) {
                    #ident.setReturnType(Type.STRING);
                } else {
                    String msg = "invalid message header identifier: " + name;
                    throw new SelectorException(#ident.getContext(), msg);
                }
            }
        }
    ;

booleanExpression[SelectorAST lhs]
    :   isExpression[lhs]
    |   ( "not"^ ) ? 
        (   betweenExpression[lhs]
        |   likeExpression[lhs]
        |   inExpression[lhs]
        )
    ;

isExpression![SelectorAST lhs]
    :   is:"is" ( not:"not" )? nul:"null"
        {
            ## = #(#is, lhs, #nul);
            if (not != null)
            {
                ## = #(#not, ##);
                ##.setReturnType(Type.BOOLEAN);
            }
            ##.setReturnType(Type.BOOLEAN);
        }
    ;

betweenExpression![SelectorAST lhs]
    :   btw:"between" sum1:sumExpression "and" sum2:sumExpression
        { 
            ## = #(#btw, lhs, #sum1, #sum2);
            ##.setReturnType(Type.BOOLEAN);
        }
    ;

inExpression![SelectorAST lhs]
    :   in:"in" LPAREN values:valueList RPAREN
        {
            ## = #(#in, lhs, LPAREN, #values, RPAREN);
            ##.setReturnType(Type.BOOLEAN);
        }
    ;

likeExpression![SelectorAST lhs]
    :   like:"like" pattern:STRING_LITERAL 
            ( esc:"escape" escape:STRING_LITERAL )?
        {
            ## = (esc != null) 
                ? #(#like, lhs, #pattern, #esc, #escape)
                : #(#like, lhs, #pattern);
            ##.setReturnType(Type.BOOLEAN);
        }
    ;

valueList
    :   STRING_LITERAL ( COMMA! STRING_LITERAL )*
    ;

literal
    :   NUM_INT 
        {
            ##.setReturnType(Type.NUMERIC);
        }
    |   NUM_FLOAT
        {
            ##.setReturnType(Type.NUMERIC);
        }
    |   STRING_LITERAL
        {
            ##.setReturnType(Type.STRING); 
        }
    |   ( "false" | "true" )
        {
            ##.setReturnType(Type.BOOLEAN);
        }
    ;

//----------------------------------------------------------------------------
// The Selector scanner
//----------------------------------------------------------------------------
class SelectorLexer extends Lexer;

options 
{
    testLiterals = false;          // don't automatically test for literals
    k = 2;                         // two characters of lookahead
    charVocabulary='\u0003'..'\uFFFE'; 
    // force vocabulary to be all characters (except special ones that ANTLR 
    // uses internally (0 to 2)
    caseSensitiveLiterals = false; // keywords are case insensitive
}


{
    public void initialise()
    {
        setColumn(1); // CharScanner sets the column to 0 - bug? TODO
    }
}

// TOKENS
LPAREN            options { paraphrase = "("; }  : '('  ;
RPAREN            options { paraphrase = ")"; }  : ')'  ;
COMMA             options { paraphrase = ","; }  : ','  ;
EQUAL             options { paraphrase = "="; }  : '='  ;
NOT_EQUAL         options { paraphrase = "<>"; } : "<>" ;
DIVIDE            options { paraphrase = "/"; }  : '/'  ;
PLUS              options { paraphrase = "+"; }  : '+'  ;
MINUS             options { paraphrase = "-"; }  : '-'  ;
MULTIPLY          options { paraphrase = "*"; }  : '*'  ;
GE                options { paraphrase = ">="; } : ">=" ;
GT                options { paraphrase = ">"; }  : ">"  ;
LE                options { paraphrase = "<="; } : "<=" ;
LT                options { paraphrase = "<"; }  : '<'  ;

// Much of the following lifted from the ANTLR grammar for java

// Whitespace - ignored
WS      :   (    ' '
        |    '\t'
        |    '\f'
        // handle newlines
        |   (    "\r\n"  // Evil DOS
            |    '\r'    // Macintosh
            |    '\n'    // Unix (the right way)
            )
            { 
                newline(); 
            }
        )
        { 
            $setType(Token.SKIP); 
        }
    ;


STRING_LITERAL
    options
    {
        paraphrase = "a string literal";
    }
    :   '\''!
        ( '\'' '\''!
        | ~('\''|'\n'|'\r')
        )*
        '\''!
    ;

// hexadecimal digit (again, note it's protected!)
protected
HEX_DIGIT
    :   ('0'..'9'|'A'..'F'|'a'..'f')
    ;


// an identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer
IDENT
    options 
    { 
        testLiterals = true; 
        paraphrase = "an identifier";
    }
    :   (IDENT_START) (IDENT_PART)*
    ;


// a numeric literal
NUM_INT
    options
    {
        paraphrase = "an integer";
    }
    { 
        boolean isDecimal = false; 
    }
    :   '.' (('0'..'9')+ (EXPONENT)? (FLOAT_SUFFIX)? 
            { $setType(NUM_FLOAT); } )?
    |   (    '0' { isDecimal = true; } // special case for just '0'
            (   ('x'|'X')
                (                                        // hex
                    // the 'e'|'E' and float suffix stuff look
                    // like hex digits, hence the (...)+ doesn't
                    // know when to stop: ambig.  ANTLR resolves
                    // it correctly by matching immediately.  It
                    // is therefor ok to hush warning.
                    options { warnWhenFollowAmbig=false; }
                :    HEX_DIGIT
                )+
            |   ('0'..'7')+                              // octal
            )?
        |   ('1'..'9') ('0'..'9')* { isDecimal = true; } // non-zero decimal
        )
        (// only check to see if it's a float if looks like decimal so far
            {isDecimal}?
            (    '.' ('0'..'9')* (EXPONENT)? (FLOAT_SUFFIX)?
            |    EXPONENT (FLOAT_SUFFIX)?
            |    FLOAT_SUFFIX
            )
            { $setType(NUM_FLOAT); }
        )?
    ;


// a couple of protected methods to assist in matching floating point numbers
protected
EXPONENT
    :   ('e'|'E') ('+'|'-')? ('0'..'9')+
    ;

protected
FLOAT_SUFFIX
    :   'f'|'F'|'d'|'D'
    ;

// protected methods for matching unicode identifiers
protected 
IDENT_START
    :   ('$'|'A'..'Z'|'_'|'a'..'z'|'\u00A2'..'\u00A5'|'\u00AA'|'\u00B5'
        |'\u00BA'|'\u00C0'..'\u00D6'|'\u00D8'..'\u00F6'|'\u00F8'..'\u01F5'
        |'\u01FA'..'\u0217'|'\u0250'..'\u02A8'|'\u02B0'..'\u02B8'
        |'\u02BB'..'\u02C1'|'\u02D0'..'\u02D1'|'\u02E0'..'\u02E4'|'\u037A'
        |'\u0386'|'\u0388'..'\u038A'|'\u038C'|'\u038E'..'\u03A1'
        |'\u03A3'..'\u03CE'|'\u03D0'..'\u03D6'|'\u03DA'|'\u03DC'|'\u03DE'
        |'\u03E0'|'\u03E2'..'\u03F3'|'\u0401'..'\u040C'|'\u040E'..'\u044F'
        |'\u0451'..'\u045C'|'\u045E'..'\u0481'|'\u0490'..'\u04C4'
        |'\u04C7'..'\u04C8'|'\u04CB'..'\u04CC'|'\u04D0'..'\u04EB'
        |'\u04EE'..'\u04F5'|'\u04F8'..'\u04F9'|'\u0531'..'\u0556'|'\u0559'
        |'\u0561'..'\u0587'|'\u05D0'..'\u05EA'|'\u05F0'..'\u05F2'
        |'\u0621'..'\u063A'|'\u0640'..'\u064A'|'\u0671'..'\u06B7'
        |'\u06BA'..'\u06BE'|'\u06C0'..'\u06CE'|'\u06D0'..'\u06D3'|'\u06D5'
        |'\u06E5'..'\u06E6'|'\u0905'..'\u0939'|'\u093D'|'\u0958'..'\u0961'
        |'\u0985'..'\u098C'|'\u098F'..'\u0990'|'\u0993'..'\u09A8'
        |'\u09AA'..'\u09B0'|'\u09B2'|'\u09B6'..'\u09B9'|'\u09DC'..'\u09DD'
        |'\u09DF'..'\u09E1'|'\u09F0'..'\u09F3'|'\u0A05'..'\u0A0A'
        |'\u0A0F'..'\u0A10'|'\u0A13'..'\u0A28'|'\u0A2A'..'\u0A30'
        |'\u0A32'..'\u0A33'|'\u0A35'..'\u0A36'|'\u0A38'..'\u0A39'
        |'\u0A59'..'\u0A5C'|'\u0A5E'|'\u0A72'..'\u0A74'|'\u0A85'..'\u0A8B'
        |'\u0A8D'|'\u0A8F'..'\u0A91'|'\u0A93'..'\u0AA8'|'\u0AAA'..'\u0AB0'
        |'\u0AB2'..'\u0AB3'|'\u0AB5'..'\u0AB9'|'\u0ABD'|'\u0AE0'
        |'\u0B05'..'\u0B0C'|'\u0B0F'..'\u0B10'|'\u0B13'..'\u0B28'
        |'\u0B2A'..'\u0B30'|'\u0B32'..'\u0B33'|'\u0B36'..'\u0B39'|'\u0B3D'
        |'\u0B5C'..'\u0B5D'|'\u0B5F'..'\u0B61'|'\u0B85'..'\u0B8A'
        |'\u0B8E'..'\u0B90'|'\u0B92'..'\u0B95'|'\u0B99'..'\u0B9A'|'\u0B9C'
        |'\u0B9E'..'\u0B9F'|'\u0BA3'..'\u0BA4'|'\u0BA8'..'\u0BAA'
        |'\u0BAE'..'\u0BB5'|'\u0BB7'..'\u0BB9'|'\u0C05'..'\u0C0C'
        |'\u0C0E'..'\u0C10'|'\u0C12'..'\u0C28'|'\u0C2A'..'\u0C33'
        |'\u0C35'..'\u0C39'|'\u0C60'..'\u0C61'|'\u0C85'..'\u0C8C'
        |'\u0C8E'..'\u0C90'|'\u0C92'..'\u0CA8'|'\u0CAA'..'\u0CB3'
        |'\u0CB5'..'\u0CB9'|'\u0CDE'|'\u0CE0'..'\u0CE1'|'\u0D05'..'\u0D0C'
        |'\u0D0E'..'\u0D10'|'\u0D12'..'\u0D28'|'\u0D2A'..'\u0D39'
        |'\u0D60'..'\u0D61'|'\u0E01'..'\u0E2E'|'\u0E30'|'\u0E32'..'\u0E33'
        |'\u0E3F'..'\u0E46'|'\u0E81'..'\u0E82'|'\u0E84'|'\u0E87'..'\u0E88'
        |'\u0E8A'|'\u0E8D'|'\u0E94'..'\u0E97'|'\u0E99'..'\u0E9F'
        |'\u0EA1'..'\u0EA3'|'\u0EA5'|'\u0EA7'|'\u0EAA'..'\u0EAB'
        |'\u0EAD'..'\u0EAE'|'\u0EB0'|'\u0EB2'..'\u0EB3'|'\u0EBD'
        |'\u0EC0'..'\u0EC4'|'\u0EC6'|'\u0EDC'..'\u0EDD'|'\u0F40'..'\u0F47'
        |'\u0F49'..'\u0F69'|'\u10A0'..'\u10C5'|'\u10D0'..'\u10F6'
        |'\u1100'..'\u1159'|'\u115F'..'\u11A2'|'\u11A8'..'\u11F9'
        |'\u1E00'..'\u1E9B'|'\u1EA0'..'\u1EF9'|'\u1F00'..'\u1F15'
        |'\u1F18'..'\u1F1D'|'\u1F20'..'\u1F45'|'\u1F48'..'\u1F4D'
        |'\u1F50'..'\u1F57'|'\u1F59'|'\u1F5B'|'\u1F5D'|'\u1F5F'..'\u1F7D'
        |'\u1F80'..'\u1FB4'|'\u1FB6'..'\u1FBC'|'\u1FBE'|'\u1FC2'..'\u1FC4'
        |'\u1FC6'..'\u1FCC'|'\u1FD0'..'\u1FD3'|'\u1FD6'..'\u1FDB'
        |'\u1FE0'..'\u1FEC'|'\u1FF2'..'\u1FF4'|'\u1FF6'..'\u1FFC'
        |'\u203F'..'\u2040'|'\u207F'|'\u20A0'..'\u20AC'|'\u2102'|'\u2107'
        |'\u210A'..'\u2113'|'\u2115'|'\u2118'..'\u211D'|'\u2124'|'\u2126'
        |'\u2128'|'\u212A'..'\u2131'|'\u2133'..'\u2138'|'\u2160'..'\u2182'
        |'\u3005'|'\u3007'|'\u3021'..'\u3029'|'\u3031'..'\u3035'
        |'\u3041'..'\u3094'|'\u309B'..'\u309E'|'\u30A1'..'\u30FA'
        |'\u30FC'..'\u30FE'|'\u3105'..'\u312C'|'\u3131'..'\u318E'
        |'\u4E00'..'\u9FA5'|'\uAC00'..'\uD7A3'|'\uF900'..'\uFA2D'
        |'\uFB00'..'\uFB06'|'\uFB13'..'\uFB17'|'\uFB1F'..'\uFB28'
        |'\uFB2A'..'\uFB36'|'\uFB38'..'\uFB3C'|'\uFB3E'|'\uFB40'..'\uFB41'
        |'\uFB43'..'\uFB44'|'\uFB46'..'\uFBB1'|'\uFBD3'..'\uFD3D'
        |'\uFD50'..'\uFD8F'|'\uFD92'..'\uFDC7'|'\uFDF0'..'\uFDFB'
        |'\uFE33'..'\uFE34'|'\uFE4D'..'\uFE4F'|'\uFE69'|'\uFE70'..'\uFE72'
        |'\uFE74'|'\uFE76'..'\uFEFC'|'\uFF04'|'\uFF21'..'\uFF3A'|'\uFF3F'
        |'\uFF41'..'\uFF5A'|'\uFF66'..'\uFFBE'|'\uFFC2'..'\uFFC7'
        |'\uFFCA'..'\uFFCF'|'\uFFD2'..'\uFFD7'|'\uFFDA'..'\uFFDC'
        |'\uFFE0'..'\uFFE1'|'\uFFE5'..'\uFFE6')
    ;

protected
IDENT_NON_START
    :   ('\u0001'..'\u0008'|'\u000E'..'\u001B'|'0'..'9'|'\u007F'..'\u009F'
        |'\u0300'..'\u0345'|'\u0360'..'\u0361'|'\u0483'..'\u0486'
        |'\u0591'..'\u05A1'|'\u05A3'..'\u05B9'|'\u05BB'..'\u05BD'|'\u05BF'
        |'\u05C1'..'\u05C2'|'\u05C4'|'\u064B'..'\u0652'|'\u0660'..'\u0669'
        |'\u0670'|'\u06D6'..'\u06DC'|'\u06DF'..'\u06E4'|'\u06E7'..'\u06E8'
        |'\u06EA'..'\u06ED'|'\u06F0'..'\u06F9'|'\u0901'..'\u0903'|'\u093C'
        |'\u093E'..'\u094D'|'\u0951'..'\u0954'|'\u0962'..'\u0963'
        |'\u0966'..'\u096F'|'\u0981'..'\u0983'|'\u09BC'|'\u09BE'..'\u09C4'
        |'\u09C7'..'\u09C8'|'\u09CB'..'\u09CD'|'\u09D7'|'\u09E2'..'\u09E3'
        |'\u09E6'..'\u09EF'|'\u0A02'|'\u0A3C'|'\u0A3E'..'\u0A42'
        |'\u0A47'..'\u0A48'|'\u0A4B'..'\u0A4D'|'\u0A66'..'\u0A71'
        |'\u0A81'..'\u0A83'|'\u0ABC'|'\u0ABE'..'\u0AC5'|'\u0AC7'..'\u0AC9'
        |'\u0ACB'..'\u0ACD'|'\u0AE6'..'\u0AEF'|'\u0B01'..'\u0B03'|'\u0B3C'
        |'\u0B3E'..'\u0B43'|'\u0B47'..'\u0B48'|'\u0B4B'..'\u0B4D'
        |'\u0B56'..'\u0B57'|'\u0B66'..'\u0B6F'|'\u0B82'..'\u0B83'
        |'\u0BBE'..'\u0BC2'|'\u0BC6'..'\u0BC8'|'\u0BCA'..'\u0BCD'|'\u0BD7'
        |'\u0BE7'..'\u0BEF'|'\u0C01'..'\u0C03'|'\u0C3E'..'\u0C44'
        |'\u0C46'..'\u0C48'|'\u0C4A'..'\u0C4D'|'\u0C55'..'\u0C56'
        |'\u0C66'..'\u0C6F'|'\u0C82'..'\u0C83'|'\u0CBE'..'\u0CC4'
        |'\u0CC6'..'\u0CC8'|'\u0CCA'..'\u0CCD'|'\u0CD5'..'\u0CD6'
        |'\u0CE6'..'\u0CEF'|'\u0D02'..'\u0D03'|'\u0D3E'..'\u0D43'
        |'\u0D46'..'\u0D48'|'\u0D4A'..'\u0D4D'|'\u0D57'|'\u0D66'..'\u0D6F'
        |'\u0E31'|'\u0E34'..'\u0E3A'|'\u0E47'..'\u0E4E'|'\u0E50'..'\u0E59'
        |'\u0EB1'|'\u0EB4'..'\u0EB9'|'\u0EBB'..'\u0EBC'|'\u0EC8'..'\u0ECD'
        |'\u0ED0'..'\u0ED9'|'\u0F18'..'\u0F19'|'\u0F20'..'\u0F29'|'\u0F35'
        |'\u0F37'|'\u0F39'|'\u0F3E'..'\u0F3F'|'\u0F71'..'\u0F84'
        |'\u0F86'..'\u0F8B'|'\u0F90'..'\u0F95'|'\u0F97'|'\u0F99'..'\u0FAD'
        |'\u0FB1'..'\u0FB7'|'\u0FB9'|'\u200C'..'\u200F'|'\u202A'..'\u202E'
        |'\u206A'..'\u206F'|'\u20D0'..'\u20DC'|'\u20E1'|'\u302A'..'\u302F'
        |'\u3099'..'\u309A'|'\uFB1E'|'\uFE20'..'\uFE23'|'\uFEFF'
        |'\uFF10'..'\uFF19')
    ;

protected
IDENT_PART
    :   (IDENT_START | IDENT_NON_START)
    ;
