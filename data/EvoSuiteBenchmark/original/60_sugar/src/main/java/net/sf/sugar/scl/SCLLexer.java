// $ANTLR 3.3 Nov 30, 2010 12:46:29 net/sf/sugar/scl/SCL.g 2013-06-10 17:15:03

	package net.sf.sugar.scl;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class SCLLexer extends Lexer {
    public static final int EOF=-1;
    public static final int T__19=19;
    public static final int VALID_NAME_CHARS=4;
    public static final int OPEN_NESTING=5;
    public static final int CLOSE_NESTING=6;
    public static final int ASSIGNMENT_OP=7;
    public static final int STRING_LITERAL=8;
    public static final int START_INCLUDE=9;
    public static final int AS_KEYWORD=10;
    public static final int END_INCLUDE=11;
    public static final int START_UNPARSED_INCLUDE=12;
    public static final int REF_CHAR=13;
    public static final int WS=14;
    public static final int QUOTE=15;
    public static final int STRING_SEQUENCE_CHAR=16;
    public static final int COMMENT=17;
    public static final int LINE_COMMENT=18;

    // delegates
    // delegators

    public SCLLexer() {;} 
    public SCLLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public SCLLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "net/sf/sugar/scl/SCL.g"; }

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:7:7: ( '.' )
            // net/sf/sugar/scl/SCL.g:7:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "OPEN_NESTING"
    public final void mOPEN_NESTING() throws RecognitionException {
        try {
            int _type = OPEN_NESTING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:259:16: ( '{' )
            // net/sf/sugar/scl/SCL.g:259:18: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OPEN_NESTING"

    // $ANTLR start "CLOSE_NESTING"
    public final void mCLOSE_NESTING() throws RecognitionException {
        try {
            int _type = CLOSE_NESTING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:261:17: ( '}' )
            // net/sf/sugar/scl/SCL.g:261:19: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CLOSE_NESTING"

    // $ANTLR start "ASSIGNMENT_OP"
    public final void mASSIGNMENT_OP() throws RecognitionException {
        try {
            int _type = ASSIGNMENT_OP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:263:17: ( ':' )
            // net/sf/sugar/scl/SCL.g:263:19: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASSIGNMENT_OP"

    // $ANTLR start "VALID_NAME_CHARS"
    public final void mVALID_NAME_CHARS() throws RecognitionException {
        try {
            int _type = VALID_NAME_CHARS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:265:19: ( ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' ) )
            // net/sf/sugar/scl/SCL.g:265:21: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '-' )
            {
            if ( input.LA(1)=='-'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VALID_NAME_CHARS"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:267:7: ( ( ' ' | '\\r' | '\\t' | '\\n' ) )
            // net/sf/sugar/scl/SCL.g:267:9: ( ' ' | '\\r' | '\\t' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "STRING_LITERAL"
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            int _type = STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:269:18: ( QUOTE ( STRING_SEQUENCE_CHAR )* QUOTE )
            // net/sf/sugar/scl/SCL.g:269:20: QUOTE ( STRING_SEQUENCE_CHAR )* QUOTE
            {
            mQUOTE(); 
            // net/sf/sugar/scl/SCL.g:269:26: ( STRING_SEQUENCE_CHAR )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='\u0000' && LA1_0<='!')||(LA1_0>='#' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // net/sf/sugar/scl/SCL.g:269:26: STRING_SEQUENCE_CHAR
            	    {
            	    mSTRING_SEQUENCE_CHAR(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            mQUOTE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING_LITERAL"

    // $ANTLR start "START_INCLUDE"
    public final void mSTART_INCLUDE() throws RecognitionException {
        try {
            int _type = START_INCLUDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:271:17: ( '[' '[' )
            // net/sf/sugar/scl/SCL.g:271:19: '[' '['
            {
            match('['); 
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "START_INCLUDE"

    // $ANTLR start "END_INCLUDE"
    public final void mEND_INCLUDE() throws RecognitionException {
        try {
            int _type = END_INCLUDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:273:15: ( ']' ']' )
            // net/sf/sugar/scl/SCL.g:273:17: ']' ']'
            {
            match(']'); 
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END_INCLUDE"

    // $ANTLR start "START_UNPARSED_INCLUDE"
    public final void mSTART_UNPARSED_INCLUDE() throws RecognitionException {
        try {
            int _type = START_UNPARSED_INCLUDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:275:25: ( 'u' '[' '[' )
            // net/sf/sugar/scl/SCL.g:275:30: 'u' '[' '['
            {
            match('u'); 
            match('['); 
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "START_UNPARSED_INCLUDE"

    // $ANTLR start "REF_CHAR"
    public final void mREF_CHAR() throws RecognitionException {
        try {
            int _type = REF_CHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:277:12: ( '~' )
            // net/sf/sugar/scl/SCL.g:277:14: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REF_CHAR"

    // $ANTLR start "QUOTE"
    public final void mQUOTE() throws RecognitionException {
        try {
            int _type = QUOTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:279:10: ( '\"' )
            // net/sf/sugar/scl/SCL.g:279:13: '\"'
            {
            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QUOTE"

    // $ANTLR start "AS_KEYWORD"
    public final void mAS_KEYWORD() throws RecognitionException {
        try {
            int _type = AS_KEYWORD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:281:14: ( ( ' as ' | ' AS ' ) )
            // net/sf/sugar/scl/SCL.g:281:16: ( ' as ' | ' AS ' )
            {
            // net/sf/sugar/scl/SCL.g:281:16: ( ' as ' | ' AS ' )
            int alt2=2;
            switch ( input.LA(1) ) {
            case ' ':
                {
                switch ( input.LA(2) ) {
                case 'a':
                    {
                    alt2=1;
                    }
                    break;
                case 'A':
                    {
                    alt2=2;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }

                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // net/sf/sugar/scl/SCL.g:281:17: ' as '
                    {
                    match(" as "); 


                    }
                    break;
                case 2 :
                    // net/sf/sugar/scl/SCL.g:281:26: ' AS '
                    {
                    match(" AS "); 


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AS_KEYWORD"

    // $ANTLR start "STRING_SEQUENCE_CHAR"
    public final void mSTRING_SEQUENCE_CHAR() throws RecognitionException {
        try {
            int _type = STRING_SEQUENCE_CHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:283:23: ( ( ( '\\\\' '\\\\' | '\\\\' '\"' ) | ( '\\u0000' .. '!' | '#' .. '[' | ']' .. '\\uFFFF' ) ) )
            // net/sf/sugar/scl/SCL.g:283:25: ( ( '\\\\' '\\\\' | '\\\\' '\"' ) | ( '\\u0000' .. '!' | '#' .. '[' | ']' .. '\\uFFFF' ) )
            {
            // net/sf/sugar/scl/SCL.g:283:25: ( ( '\\\\' '\\\\' | '\\\\' '\"' ) | ( '\\u0000' .. '!' | '#' .. '[' | ']' .. '\\uFFFF' ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\\') ) {
                alt4=1;
            }
            else if ( ((LA4_0>='\u0000' && LA4_0<='!')||(LA4_0>='#' && LA4_0<='[')||(LA4_0>=']' && LA4_0<='\uFFFF')) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // net/sf/sugar/scl/SCL.g:283:26: ( '\\\\' '\\\\' | '\\\\' '\"' )
                    {
                    // net/sf/sugar/scl/SCL.g:283:26: ( '\\\\' '\\\\' | '\\\\' '\"' )
                    int alt3=2;
                    switch ( input.LA(1) ) {
                    case '\\':
                        {
                        switch ( input.LA(2) ) {
                        case '\\':
                            {
                            alt3=1;
                            }
                            break;
                        case '\"':
                            {
                            alt3=2;
                            }
                            break;
                        default:
                            NoViableAltException nvae =
                                new NoViableAltException("", 3, 1, input);

                            throw nvae;
                        }

                        }
                        break;
                    default:
                        NoViableAltException nvae =
                            new NoViableAltException("", 3, 0, input);

                        throw nvae;
                    }

                    switch (alt3) {
                        case 1 :
                            // net/sf/sugar/scl/SCL.g:283:27: '\\\\' '\\\\'
                            {
                            match('\\'); 
                            match('\\'); 

                            }
                            break;
                        case 2 :
                            // net/sf/sugar/scl/SCL.g:283:39: '\\\\' '\"'
                            {
                            match('\\'); 
                            match('\"'); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // net/sf/sugar/scl/SCL.g:283:51: ( '\\u0000' .. '!' | '#' .. '[' | ']' .. '\\uFFFF' )
                    {
                    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING_SEQUENCE_CHAR"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:285:12: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // net/sf/sugar/scl/SCL.g:285:16: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // net/sf/sugar/scl/SCL.g:285:21: ( options {greedy=false; } : . )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='*') ) {
                    int LA5_1 = input.LA(2);

                    if ( (LA5_1=='/') ) {
                        alt5=2;
                    }
                    else if ( ((LA5_1>='\u0000' && LA5_1<='.')||(LA5_1>='0' && LA5_1<='\uFFFF')) ) {
                        alt5=1;
                    }


                }
                else if ( ((LA5_0>='\u0000' && LA5_0<=')')||(LA5_0>='+' && LA5_0<='\uFFFF')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // net/sf/sugar/scl/SCL.g:285:49: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match("*/"); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // net/sf/sugar/scl/SCL.g:287:16: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // net/sf/sugar/scl/SCL.g:287:19: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // net/sf/sugar/scl/SCL.g:287:24: (~ ( '\\n' | '\\r' ) )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='\u0000' && LA6_0<='\t')||(LA6_0>='\u000B' && LA6_0<='\f')||(LA6_0>='\u000E' && LA6_0<='\uFFFF')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // net/sf/sugar/scl/SCL.g:287:24: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            // net/sf/sugar/scl/SCL.g:287:38: ( '\\r' )?
            int alt7=2;
            switch ( input.LA(1) ) {
                case '\r':
                    {
                    alt7=1;
                    }
                    break;
            }

            switch (alt7) {
                case 1 :
                    // net/sf/sugar/scl/SCL.g:287:38: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    public void mTokens() throws RecognitionException {
        // net/sf/sugar/scl/SCL.g:1:8: ( T__19 | OPEN_NESTING | CLOSE_NESTING | ASSIGNMENT_OP | VALID_NAME_CHARS | WS | STRING_LITERAL | START_INCLUDE | END_INCLUDE | START_UNPARSED_INCLUDE | REF_CHAR | QUOTE | AS_KEYWORD | STRING_SEQUENCE_CHAR | COMMENT | LINE_COMMENT )
        int alt8=16;
        alt8 = dfa8.predict(input);
        switch (alt8) {
            case 1 :
                // net/sf/sugar/scl/SCL.g:1:10: T__19
                {
                mT__19(); 

                }
                break;
            case 2 :
                // net/sf/sugar/scl/SCL.g:1:16: OPEN_NESTING
                {
                mOPEN_NESTING(); 

                }
                break;
            case 3 :
                // net/sf/sugar/scl/SCL.g:1:29: CLOSE_NESTING
                {
                mCLOSE_NESTING(); 

                }
                break;
            case 4 :
                // net/sf/sugar/scl/SCL.g:1:43: ASSIGNMENT_OP
                {
                mASSIGNMENT_OP(); 

                }
                break;
            case 5 :
                // net/sf/sugar/scl/SCL.g:1:57: VALID_NAME_CHARS
                {
                mVALID_NAME_CHARS(); 

                }
                break;
            case 6 :
                // net/sf/sugar/scl/SCL.g:1:74: WS
                {
                mWS(); 

                }
                break;
            case 7 :
                // net/sf/sugar/scl/SCL.g:1:77: STRING_LITERAL
                {
                mSTRING_LITERAL(); 

                }
                break;
            case 8 :
                // net/sf/sugar/scl/SCL.g:1:92: START_INCLUDE
                {
                mSTART_INCLUDE(); 

                }
                break;
            case 9 :
                // net/sf/sugar/scl/SCL.g:1:106: END_INCLUDE
                {
                mEND_INCLUDE(); 

                }
                break;
            case 10 :
                // net/sf/sugar/scl/SCL.g:1:118: START_UNPARSED_INCLUDE
                {
                mSTART_UNPARSED_INCLUDE(); 

                }
                break;
            case 11 :
                // net/sf/sugar/scl/SCL.g:1:141: REF_CHAR
                {
                mREF_CHAR(); 

                }
                break;
            case 12 :
                // net/sf/sugar/scl/SCL.g:1:150: QUOTE
                {
                mQUOTE(); 

                }
                break;
            case 13 :
                // net/sf/sugar/scl/SCL.g:1:156: AS_KEYWORD
                {
                mAS_KEYWORD(); 

                }
                break;
            case 14 :
                // net/sf/sugar/scl/SCL.g:1:167: STRING_SEQUENCE_CHAR
                {
                mSTRING_SEQUENCE_CHAR(); 

                }
                break;
            case 15 :
                // net/sf/sugar/scl/SCL.g:1:188: COMMENT
                {
                mCOMMENT(); 

                }
                break;
            case 16 :
                // net/sf/sugar/scl/SCL.g:1:196: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\5\uffff\1\24\1\26\1\27\2\15\4\uffff\1\15\17\uffff";
    static final String DFA8_eofS =
        "\36\uffff";
    static final String DFA8_minS =
        "\1\0\4\uffff\1\133\1\101\1\0\1\133\1\135\4\uffff\1\52\17\uffff";
    static final String DFA8_maxS =
        "\1\uffff\4\uffff\1\133\1\141\1\uffff\1\133\1\135\4\uffff\1\57\17"+
        "\uffff";
    static final String DFA8_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\5\uffff\1\5\1\13\1\6\1\16\1\uffff\1\1\1"+
        "\2\1\3\1\4\1\12\1\5\1\15\1\6\1\14\1\7\1\10\1\11\1\13\1\17\1\20";
    static final String DFA8_specialS =
        "\1\0\6\uffff\1\1\26\uffff}>";
    static final String[] DFA8_transitionS = {
            "\11\15\2\14\2\15\1\14\22\15\1\6\1\15\1\7\12\15\1\12\1\1\1\16"+
            "\12\12\1\4\6\15\32\12\1\10\1\15\1\11\1\15\1\12\1\15\24\12\1"+
            "\5\5\12\1\2\1\15\1\3\1\13\uff81\15",
            "",
            "",
            "",
            "",
            "\1\23",
            "\1\25\37\uffff\1\25",
            "\0\30",
            "\1\31",
            "\1\32",
            "",
            "",
            "",
            "",
            "\1\34\4\uffff\1\35",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__19 | OPEN_NESTING | CLOSE_NESTING | ASSIGNMENT_OP | VALID_NAME_CHARS | WS | STRING_LITERAL | START_INCLUDE | END_INCLUDE | START_UNPARSED_INCLUDE | REF_CHAR | QUOTE | AS_KEYWORD | STRING_SEQUENCE_CHAR | COMMENT | LINE_COMMENT );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_0 = input.LA(1);

                        s = -1;
                        if ( (LA8_0=='.') ) {s = 1;}

                        else if ( (LA8_0=='{') ) {s = 2;}

                        else if ( (LA8_0=='}') ) {s = 3;}

                        else if ( (LA8_0==':') ) {s = 4;}

                        else if ( (LA8_0=='u') ) {s = 5;}

                        else if ( (LA8_0==' ') ) {s = 6;}

                        else if ( (LA8_0=='\"') ) {s = 7;}

                        else if ( (LA8_0=='[') ) {s = 8;}

                        else if ( (LA8_0==']') ) {s = 9;}

                        else if ( (LA8_0=='-'||(LA8_0>='0' && LA8_0<='9')||(LA8_0>='A' && LA8_0<='Z')||LA8_0=='_'||(LA8_0>='a' && LA8_0<='t')||(LA8_0>='v' && LA8_0<='z')) ) {s = 10;}

                        else if ( (LA8_0=='~') ) {s = 11;}

                        else if ( ((LA8_0>='\t' && LA8_0<='\n')||LA8_0=='\r') ) {s = 12;}

                        else if ( ((LA8_0>='\u0000' && LA8_0<='\b')||(LA8_0>='\u000B' && LA8_0<='\f')||(LA8_0>='\u000E' && LA8_0<='\u001F')||LA8_0=='!'||(LA8_0>='#' && LA8_0<=',')||(LA8_0>=';' && LA8_0<='@')||LA8_0=='\\'||LA8_0=='^'||LA8_0=='`'||LA8_0=='|'||(LA8_0>='\u007F' && LA8_0<='\uFFFF')) ) {s = 13;}

                        else if ( (LA8_0=='/') ) {s = 14;}

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA8_7 = input.LA(1);

                        s = -1;
                        if ( ((LA8_7>='\u0000' && LA8_7<='\uFFFF')) ) {s = 24;}

                        else s = 23;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 8, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}