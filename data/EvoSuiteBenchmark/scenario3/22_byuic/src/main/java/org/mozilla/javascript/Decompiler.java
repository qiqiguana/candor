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
     * Decompile the source information associated with this js
     * function/script back into a string.  For the most part, this
     * just means translating tokens back to their string
     * representations; there's a little bit of lookahead logic to
     * decide the proper spacing/indentation.  Most of the work in
     * mapping the original source to the prettyprinted decompiled
     * version is done by the parser.
     *
     * @param source encoded source tree presentation
     * @param flags flags to select output format
     * @param properties indentation properties
     */
    public static String decompile(String source, int flags, UintMap properties);
}
