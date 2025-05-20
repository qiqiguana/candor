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

    /**
     * Flag to indicate that the decompilation should omit the
     * function header and trailing brace.
     */
    public static final int ONLY_BODY_FLAG = 1 << 0;

    /**
     * Flag to indicate that the decompilation generates toSource result.
     */
    public static final int TO_SOURCE_FLAG = 1 << 1;

    /**
     * Decompilation property to specify initial ident value.
     */
    public static final int INITIAL_INDENT_PROP = 1;

    /**
     * Decompilation property to specify default identation offset.
     */
    public static final int INDENT_GAP_PROP = 2;

    /**
     * Decompilation property to specify identation offset for case labels.
     */
    public static final int CASE_GAP_PROP = 3;

    // the last RC of object literals in case of function expressions
    private static final int FUNCTION_END = Token.LAST_TOKEN + 1;

    String getEncodedSource();

    int getCurrentOffset();

    int markFunctionStart(int functionType);

    int markFunctionEnd(int functionStart);

    void addToken(int token);

    void addEOL(int token);

    void addName(String str);

    void addString(String str);

    void addRegexp(String regexp, String flags);

    void addJScriptConditionalComment(String str);

    void addPreservedComment(String str);

    void addNumber(double n);

    private void appendString(String str);

    private void append(char c);

    private void increaseSourceCapacity(int minimalCapacity);

    private String sourceToString(int offset);

    /**
     * Decompile the source information associated with this js
     * function/script back into a string.  For the most part, this
     * just means translating tokens back to their string
     * representations; there's a little bit of lookahead logic to
     * decide the proper spacing/indentation.  Most of the work in
     * mapping the original source to the prettyprinted decompiled
     * version is done by the parser.
     *
     * @param source encoded source tree presentation
     *
     * @param flags flags to select output format
     *
     * @param properties indentation properties
     */
    public static String decompile(String source, int flags, UintMap properties);

    private static int getNext(String source, int length, int i);

    private static int getSourceStringEnd(String source, int offset);

    private static int printSourceString(String source, int offset, boolean asQuotedString, StringBuffer sb);

    private static int printSourceNumber(String source, int offset, StringBuffer sb);

    private char[] sourceBuffer = new char[128];

    // nested functions source and uses function index as a reference instead.
    private int sourceTop;

    // whether to do a debug print of the source information, when decompiling.
    private static final boolean printSource = false;
}
