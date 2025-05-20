package bible.obj;

import java.sql.SQLException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import bible.util.*;
import bible.servlet.*;

/**
 * 
 * @author  James Stauffer
 * @version $Id: References.java,v 1.7 2001/02/21 03:32:21 jstauffe Exp $
 */
public class References {
    public static final String KEY = "References";

    public References(HttpServletRequest request) throws ParseException {
        translations = Translation.Get(request);
System.out.println("References translations=" + Util.ToString(translations, false, ","));
        
        String userInput = ServletUtil.GetStringParameter(request, KEY);
        System.out.println("References userInput=" + userInput);
        StringTokenizer st = new StringTokenizer(userInput.trim(), "-,;", true);
        String nextToken = null;
        try {
            boolean range = false;//On 2nd part of a range
            Reference lastRef = null;//use to remember the first part of a range
            while(st.hasMoreTokens()) {
                nextToken = st.nextToken();
System.out.println("References nextToken=" + nextToken);
                if(nextToken.equals("-")) {//range
                    range = true;
                } else if(nextToken.equals(",") || nextToken.equals(";")) {//addition
                    range = false;
                } else {//should be a reference
                    Reference ref = GetReference(nextToken.trim(), (lastRef != null) ? (lastRef.getVerseNo() >= 0) : true);//last ref had a verse set or no last rep
                    if(lastRef != null) {//use lastRef as the base
                        ref = new Reference(lastRef, ref);
                    }
System.out.println("References ref=" + ref);
                    if(range) {
                        refs.set(refs.size() - 1, new ReferenceRange(lastRef, ref));//replace last with the range
                    } else {//may be addition
                        refs.add(ref);
                    }
                    lastRef = ref;
                    range = false;
System.out.println("References lastRef=" + lastRef);
                }
            }
        } catch(NoSuchElementException e) {
            throw new NoSuchElementException("nextToken='" + nextToken + "', userInput='" 
                + userInput + "' " + e.getMessage());
        }

System.out.println("References refs=" + refs);
    }

    public ArrayList getVerses() {
        ArrayList verses = new ArrayList();
        for(int index = 0; index < refs.size(); index++) {
            verses.addAll(((IReference)refs.get(index)).getVerses(translations));
        }

        return verses;
    }

    /**
     * @param expectVerse determines what to do if there is not a semicolon.  true = verse.  false = chapter
     * @author James Stauffer
     */
    private final static Reference GetReference(String str, boolean expectVerse) throws ParseException {
        int spaceIndex = str.lastIndexOf(" ");
        int bookId = -1;
        if(spaceIndex > 0) {
            bookId = Book.Get(str.substring(0, spaceIndex).trim()).getId();
            str = str.substring(spaceIndex + 1);
        }

        int colonIndex = str.indexOf(":");
        int verseNo = -1;
        if(colonIndex >= 0) {
            verseNo = Integer.parseInt(str.substring(colonIndex + 1));
            str = str.substring(0, colonIndex);
        } else if(expectVerse) {
            verseNo = Integer.parseInt(str);
            str = "";
        }

        int chapter = -1;
        if(str.length() > 0) {
            chapter = Integer.parseInt(str);
        }
        return new Reference(bookId, chapter, verseNo);
    }

    private final static Integer GetInteger(String value) {
        try {
            return new Integer(value);
        } catch(NumberFormatException e) {
            return null;
        }
    }

    private ArrayList refs = new ArrayList();
    private Translation[] translations;
}

