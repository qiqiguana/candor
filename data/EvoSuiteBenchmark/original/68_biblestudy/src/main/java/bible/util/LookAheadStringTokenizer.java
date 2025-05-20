package bible.util;

import java.util.*;

/**
 * Allows peeking at the next and next next tokens
 * @author    James Stauffer
 */
public class LookAheadStringTokenizer extends StringTokenizer {

    public LookAheadStringTokenizer(String str) {
        super(str);
        finishConstruction();
    }

    public LookAheadStringTokenizer(String str, String delim) {
        super(str, delim);
        finishConstruction();
    }

    public LookAheadStringTokenizer(String str, String delim, boolean returnTokens) {
        super(str, delim, returnTokens);
        finishConstruction();
    }

    public Object nextElement() {
        return nextToken();
    }

    public String nextToken() {
        if(nextToken == null) {
            throw new NoSuchElementException(toString());
        }
        String returnValue = nextToken;
        nextToken = nextNextToken;
        if(super.hasMoreElements()) {
            nextNextToken = super.nextToken();
        } else {
            nextNextToken = null;
        }
        return returnValue;
    }

    public String nextTokenPeek() {
        if(nextToken == null) {
            throw new NoSuchElementException(toString());
        }
        return nextToken;
    }

    public String nextNextTokenPeek() {
        if(nextNextToken == null) {
            throw new NoSuchElementException(toString());
        }
        return nextNextToken;
    }

    public boolean hasMoreTokens() {
        return nextToken != null;
    }

    private String nextToken;
    private String nextNextToken;

    private void finishConstruction() {
        if(super.hasMoreElements()) {
            nextToken = super.nextToken();
            if(super.hasMoreElements()) {
                nextNextToken = super.nextToken();
            }
        }
    }

}

