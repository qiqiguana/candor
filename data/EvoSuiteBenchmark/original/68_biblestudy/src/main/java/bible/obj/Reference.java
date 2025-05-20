package bible.obj;

import java.sql.SQLException;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

import bible.util.*;
import bible.servlet.*;

/**
 * 
 * @author  James Stauffer
 * @version $Id: Reference.java,v 1.7 2001/02/21 03:31:23 jstauffe Exp $
 */
public class Reference implements IReference {
    public static final String KEY = "Reference";

    public Reference(HttpServletRequest request) {
        this.bookId = Book.Get(request).getId();
        this.chapter = ServletUtil.GetIntParameter(request, "Chapter");
        this.verse = ServletUtil.GetIntParameter(request, "Verse");
    }

    public Reference(int book, int chapter, int verse) {
        this.bookId = book;
        this.chapter = chapter;
        this.verse = verse;
    }

    /**
     * Copies data from values if it exists, else copies from defaults
     * @author James Stauffer
     */
    public Reference(Reference defaults, Reference values) {
        this.bookId        = Choose(values.bookId,        defaults.bookId);
        this.chapter       = Choose(values.chapter,       defaults.chapter);
        this.verse         = Choose(values.verse,         defaults.verse);
    }

    public ArrayList getVerses(Translation[] translations) {
        return Verse.Get(translations, bookId, chapter, verse);
    }

    public int getBookId() {
        return bookId;
    }

    public Book getBook() {
        return Book.Get(bookId);
    }

    public int getChapterNo() {
        return chapter;
    }

    public int getVerseNo() {
        return verse;
    }

    public String toHtml() {
        return Book.Get(bookId).getName()+ " " + chapter + ":" + verse;
    }

    public String toString() {
        return getClass().getName() + ":[" + bookId + " " + chapter + ":" + verse + "]";
    }

    private int bookId;
    private int chapter;
    private int verse;

    private static int Choose(int preferred, int alternate) {
        if(preferred >= 0) {
            return preferred;
        } else {
            return alternate;
        }
    }

}

