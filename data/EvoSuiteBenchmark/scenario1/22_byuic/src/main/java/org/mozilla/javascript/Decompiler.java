package org.mozilla.javascript;

/**
 * The following class save decompilation information about the source.
 * Source information is returned from the parser as a String
 * associated with function nodes and with the toplevel script.  When
 * saved in the constant pool of a class, this string will be UTF-8
 * encoded, and token values will occupy a single byte.
 *
 * Source is saved (mostly) as token numbers.  The tokens saved pretty
 * much correspond to the token stream of a 'canonical' representation
 * of the input program, as directed by the parser.  (There were a few
 * cases where tokens could have been left out where decompiler could
 * easily reconstruct them, but I left them in for clarity).  (I also
 * looked adding source collection to TokenStream instead, where I
 * could have limited the changes to a few lines in getToken... but
 * this wouldn't have saved any space in the resulting source
 * representation, and would have meant that I'd have to duplicate
 * parser logic in the decompiler to disambiguate situations where
 * newlines are important.)  The function decompile expands the
 * tokens back into their string representations, using simple
 * lookahead to correct spacing and indentation.
 *
 * Assignments are saved as two-token pairs (Token.ASSIGN, op). Number tokens
 * are stored inline, as a NUMBER token, a character representing the type, and
 * either 1 or 4 characters representing the bit-encoding of the number.  String
 * types NAME, STRING and OBJECT are currently stored as a token type,
 * followed by a character giving the length of the string (assumed to
 * be less than 2^16), followed by the characters of the string
 * inlined into the source string.  Changing this to some reference to
 * to the string in the compiled class' constant pool would probably
 * save a lot of space... but would require some method of deriving
 * the final constant pool entry from information available at parse
 * time.
 */
public class Decompiler {

    public static String decompile(String source, int flags, UintMap properties) {
        int length = source.length();
        if (length == 0) {
            return "";
        }
        int indent = properties.getInt(INITIAL_INDENT_PROP, 0);
        if (indent < 0)
            throw new IllegalArgumentException();
        int indentGap = properties.getInt(INDENT_GAP_PROP, 4);
        if (indentGap < 0)
            throw new IllegalArgumentException();
        int caseGap = properties.getInt(CASE_GAP_PROP, 2);
        if (caseGap < 0)
            throw new IllegalArgumentException();
        StringBuffer result = new StringBuffer();
        boolean justFunctionBody = (0 != (flags & Decompiler.ONLY_BODY_FLAG));
        boolean toSource = (0 != (flags & Decompiler.TO_SOURCE_FLAG));
        // Spew tokens in source, for debugging.
        // as TYPE number char
        if (printSource) {
            System.err.println("length:" + length);
            for (int i = 0; i < length; ++i) {
                // Note that tokenToName will fail unless Context.printTrees
                // is true.
                String tokenname = null;
                if (Token.printNames) {
                    tokenname = Token.name(source.charAt(i));
                }
                if (tokenname == null) {
                    tokenname = "---";
                }
                String pad = tokenname.length() > 7 ? "\t" : "\t\t";
                System.err.println(tokenname + pad + (int) source.charAt(i) + "\t'" + ScriptRuntime.escapeString(source.substring(i, i + 1)) + "'");
            }
            System.err.println();
        }
        int braceNesting = 0;
        boolean afterFirstEOL = false;
        int i = 0;
        int topFunctionType;
        if (source.charAt(i) == Token.SCRIPT) {
            ++i;
            topFunctionType = -1;
        } else {
            topFunctionType = source.charAt(i + 1);
        }
        if (!toSource) {
            // add an initial newline to exactly match js.
            result.append('\n');
            for (int j = 0; j < indent; j++) result.append(' ');
        } else {
            if (topFunctionType == FunctionNode.FUNCTION_EXPRESSION) {
                result.append('(');
            }
        }
        while (i < length) {
            switch(source.charAt(i)) {
                case Token.GET:
                case Token.SET:
                    result.append(source.charAt(i) == Token.GET ? "get " : "set ");
                    ++i;
                    i = printSourceString(source, i + 1, false, result);
                    // Now increment one more to get past the FUNCTION token
                    ++i;
                    break;
                case Token.NAME:
                case // re-wrapped in '/'s in parser...
                Token.REGEXP:
                    i = printSourceString(source, i + 1, false, result);
                    continue;
                case Token.STRING:
                    i = printSourceString(source, i + 1, true, result);
                    continue;
                case Token.NUMBER:
                    i = printSourceNumber(source, i + 1, result);
                    continue;
                case Token.TRUE:
                    result.append("true");
                    break;
                case Token.FALSE:
                    result.append("false");
                    break;
                case Token.NULL:
                    result.append("null");
                    break;
                case Token.THIS:
                    result.append("this");
                    break;
                case Token.FUNCTION:
                    // skip function type
                    ++i;
                    result.append("function ");
                    break;
                case FUNCTION_END:
                    // Do nothing
                    break;
                case Token.COMMA:
                    result.append(", ");
                    break;
                case Token.LC:
                    ++braceNesting;
                    if (Token.EOL == getNext(source, length, i))
                        indent += indentGap;
                    result.append('{');
                    break;
                case Token.RC:
                    {
                        --braceNesting;
                        /* don't print the closing RC if it closes the
                 * toplevel function and we're called from
                 * decompileFunctionBody.
                 */
                        if (justFunctionBody && braceNesting == 0)
                            break;
                        result.append('}');
                        switch(getNext(source, length, i)) {
                            case Token.EOL:
                            case FUNCTION_END:
                                indent -= indentGap;
                                break;
                            case Token.WHILE:
                            case Token.ELSE:
                                indent -= indentGap;
                                result.append(' ');
                                break;
                        }
                        break;
                    }
                case Token.LP:
                    result.append('(');
                    break;
                case Token.RP:
                    result.append(')');
                    if (Token.LC == getNext(source, length, i))
                        result.append(' ');
                    break;
                case Token.LB:
                    result.append('[');
                    break;
                case Token.RB:
                    result.append(']');
                    break;
                case Token.EOL:
                    {
                        if (toSource)
                            break;
                        boolean newLine = true;
                        if (!afterFirstEOL) {
                            afterFirstEOL = true;
                            if (justFunctionBody) {
                                /* throw away just added 'function name(...) {'
                         * and restore the original indent
                         */
                                result.setLength(0);
                                indent -= indentGap;
                                newLine = false;
                            }
                        }
                        if (newLine) {
                            result.append('\n');
                        }
                        /* add indent if any tokens remain,
                 * less setback if next token is
                 * a label, case or default.
                 */
                        if (i + 1 < length) {
                            int less = 0;
                            int nextToken = source.charAt(i + 1);
                            if (nextToken == Token.CASE || nextToken == Token.DEFAULT) {
                                less = indentGap - caseGap;
                            } else if (nextToken == Token.RC) {
                                less = indentGap;
                            } else /* elaborate check against label... skip past a
                     * following inlined NAME and look for a COLON.
                     */
                            if (nextToken == Token.NAME) {
                                int afterName = getSourceStringEnd(source, i + 2);
                                if (source.charAt(afterName) == Token.COLON)
                                    less = indentGap;
                            }
                            for (; less < indent; less++) result.append(' ');
                        }
                        break;
                    }
                case Token.DOT:
                    result.append('.');
                    break;
                case Token.NEW:
                    result.append("new ");
                    break;
                case Token.DELPROP:
                    result.append("delete ");
                    break;
                case Token.IF:
                    result.append("if ");
                    break;
                case Token.ELSE:
                    result.append("else ");
                    break;
                case Token.FOR:
                    result.append("for ");
                    break;
                case Token.IN:
                    result.append(" in ");
                    break;
                case Token.WITH:
                    result.append("with ");
                    break;
                case Token.WHILE:
                    result.append("while ");
                    break;
                case Token.DO:
                    result.append("do ");
                    break;
                case Token.TRY:
                    result.append("try ");
                    break;
                case Token.CATCH:
                    result.append("catch ");
                    break;
                case Token.FINALLY:
                    result.append("finally ");
                    break;
                case Token.THROW:
                    result.append("throw ");
                    break;
                case Token.SWITCH:
                    result.append("switch ");
                    break;
                case Token.BREAK:
                    result.append("break");
                    if (Token.NAME == getNext(source, length, i))
                        result.append(' ');
                    break;
                case Token.CONTINUE:
                    result.append("continue");
                    if (Token.NAME == getNext(source, length, i))
                        result.append(' ');
                    break;
                case Token.CASE:
                    result.append("case ");
                    break;
                case Token.DEFAULT:
                    result.append("default");
                    break;
                case Token.RETURN:
                    result.append("return");
                    if (Token.SEMI != getNext(source, length, i))
                        result.append(' ');
                    break;
                case Token.VAR:
                    result.append("var ");
                    break;
                case Token.LET:
                    result.append("let ");
                    break;
                case Token.SEMI:
                    result.append(';');
                    if (Token.EOL != getNext(source, length, i)) {
                        // separators in FOR
                        result.append(' ');
                    }
                    break;
                case Token.ASSIGN:
                    result.append(" = ");
                    break;
                case Token.ASSIGN_ADD:
                    result.append(" += ");
                    break;
                case Token.ASSIGN_SUB:
                    result.append(" -= ");
                    break;
                case Token.ASSIGN_MUL:
                    result.append(" *= ");
                    break;
                case Token.ASSIGN_DIV:
                    result.append(" /= ");
                    break;
                case Token.ASSIGN_MOD:
                    result.append(" %= ");
                    break;
                case Token.ASSIGN_BITOR:
                    result.append(" |= ");
                    break;
                case Token.ASSIGN_BITXOR:
                    result.append(" ^= ");
                    break;
                case Token.ASSIGN_BITAND:
                    result.append(" &= ");
                    break;
                case Token.ASSIGN_LSH:
                    result.append(" <<= ");
                    break;
                case Token.ASSIGN_RSH:
                    result.append(" >>= ");
                    break;
                case Token.ASSIGN_URSH:
                    result.append(" >>>= ");
                    break;
                case Token.HOOK:
                    result.append(" ? ");
                    break;
                case Token.OBJECTLIT:
                    // pun OBJECTLIT to mean colon in objlit property
                    // initialization.
                    // This needs to be distinct from COLON in the general case
                    // to distinguish from the colon in a ternary... which needs
                    // different spacing.
                    result.append(':');
                    break;
                case Token.COLON:
                    if (Token.EOL == getNext(source, length, i))
                        // it's the end of a label
                        result.append(':');
                    else
                        // it's the middle part of a ternary
                        result.append(" : ");
                    break;
                case Token.OR:
                    result.append(" || ");
                    break;
                case Token.AND:
                    result.append(" && ");
                    break;
                case Token.BITOR:
                    result.append(" | ");
                    break;
                case Token.BITXOR:
                    result.append(" ^ ");
                    break;
                case Token.BITAND:
                    result.append(" & ");
                    break;
                case Token.SHEQ:
                    result.append(" === ");
                    break;
                case Token.SHNE:
                    result.append(" !== ");
                    break;
                case Token.EQ:
                    result.append(" == ");
                    break;
                case Token.NE:
                    result.append(" != ");
                    break;
                case Token.LE:
                    result.append(" <= ");
                    break;
                case Token.LT:
                    result.append(" < ");
                    break;
                case Token.GE:
                    result.append(" >= ");
                    break;
                case Token.GT:
                    result.append(" > ");
                    break;
                case Token.INSTANCEOF:
                    result.append(" instanceof ");
                    break;
                case Token.LSH:
                    result.append(" << ");
                    break;
                case Token.RSH:
                    result.append(" >> ");
                    break;
                case Token.URSH:
                    result.append(" >>> ");
                    break;
                case Token.TYPEOF:
                    result.append("typeof ");
                    break;
                case Token.VOID:
                    result.append("void ");
                    break;
                case Token.CONST:
                    result.append("const ");
                    break;
                case Token.YIELD:
                    result.append("yield ");
                    break;
                case Token.NOT:
                    result.append('!');
                    break;
                case Token.BITNOT:
                    result.append('~');
                    break;
                case Token.POS:
                    result.append('+');
                    break;
                case Token.NEG:
                    result.append('-');
                    break;
                case Token.INC:
                    result.append("++");
                    break;
                case Token.DEC:
                    result.append("--");
                    break;
                case Token.ADD:
                    result.append(" + ");
                    break;
                case Token.SUB:
                    result.append(" - ");
                    break;
                case Token.MUL:
                    result.append(" * ");
                    break;
                case Token.DIV:
                    result.append(" / ");
                    break;
                case Token.MOD:
                    result.append(" % ");
                    break;
                case Token.COLONCOLON:
                    result.append("::");
                    break;
                case Token.DOTDOT:
                    result.append("..");
                    break;
                case Token.DOTQUERY:
                    result.append(".(");
                    break;
                case Token.XMLATTR:
                    result.append('@');
                    break;
                default:
                    // If we don't know how to decompile it, raise an exception.
                    throw new RuntimeException("Token: " + Token.name(source.charAt(i)));
            }
            ++i;
        }
        if (!toSource) {
            // add that trailing newline if it's an outermost function.
            if (!justFunctionBody)
                result.append('\n');
        } else {
            if (topFunctionType == FunctionNode.FUNCTION_EXPRESSION) {
                result.append(')');
            }
        }
        return result.toString();
    }
}
