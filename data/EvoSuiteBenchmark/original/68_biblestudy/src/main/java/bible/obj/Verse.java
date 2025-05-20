package bible.obj;

import java.sql.SQLException;
import java.io.IOException;
import java.util.*;

import bible.util.*;

/**
 * 
 *
 * @author  James Stauffer
 * @version $Id: Verse.java,v 1.5 2001/02/21 03:09:55 jstauffe Exp $
 */
public class Verse implements Identifible {

    public static Verse New(int id) {
        Verse verse = (Verse)verseCache.get(new Integer(id));
        if(verse == null) {
            verse = new Verse(id);
        }
        return verse;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getValue() {//Identifible interface
        return getText();
    }

    public TranslationReference getTranslationReference() {
        return transRef;
    }

    public Reference getReference() {
        return transRef.getReference();
    }

    public String toString() {
        return getClass().getName() + ":[" + id + " " + transRef + " " + text + "]";
    }

    public static int GetId(Translation translation, Book book, int chapterId, int verseId) {
        return GetId(translation.getId(), book.getId(), chapterId, verseId);
    }

    public static ArrayList Get(Translation[] translations, int bookId, int chapterId, int verseId) {
        ArrayList verses = new ArrayList(translations.length);
        for(int index = 0; index < translations.length; index++) {
            verses.add(New(GetId(translations[index].getId(), bookId, chapterId, verseId)));
        }
        return verses;
    }

    public static ArrayList Get(Translation[] translations, Reference begin, Reference end) {
System.out.println("Verse.Get translations=" + Util.ToString(translations));
        ArrayList verses = new ArrayList();
        DbResult result = null;
        String query = "select Verse_ID from Verse where "
            + ((translations != null && translations.length > 0) ? ("Translation_ID in (" + Util.ToString(Util.ToIntArray(translations)) + ") and ") : "")
            + "Book_ID >= "  + begin.getBookId()    + " and Book_ID <= "  + end.getBookId()
            + " and Chapter >= "  + begin.getChapterNo() + " and Chapter <= "  + end.getChapterNo()
            + " and Verse_No >= " + begin.getVerseNo()   + " and Verse_No <= " + end.getVerseNo();
System.out.println("Verse.Get query=" + query);
        try {
            result = Database.Query(query);
            while(result.next()) {
                verses.add(New(result.getInt("Verse_ID")));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DbResult.Close(result);
        }
        return verses;
    }

    /**
     * Looks up the id in the 4-way HashMap(with a base of translations).
     * HashMap sizes are chosen to be prime.
     * @author James Stauffer
     */
    public static int GetId(int translationId, int bookId, int chapterId, int verseId) {
        translationId = Translation.Normalize(translationId);
        HashMap translation = (HashMap)translations.get(new Integer(translationId));
        if(translation == null) {
            translation = new HashMap(11);
            translations.put(new Integer(translationId), translation);
        }

        HashMap book = (HashMap)translation.get(new Integer(bookId));
        if(book == null) {
            book = new HashMap(97);
            translation.put(new Integer(bookId), book);
        }

        HashMap chapter = (HashMap)book.get(new Integer(chapterId));
        if(chapter == null) {
            chapter =  new HashMap(47);
            book.put(new Integer(chapterId), chapter);
        }

        Integer id = (Integer)chapter.get(new Integer(verseId));
        int intId;
        if(id == null) {
            intId = ReadId(translationId, bookId, chapterId, verseId);
            if(intId > 0) {
                chapter.put(new Integer(verseId), new Integer(intId));
            }
        } else {
            intId = id.intValue();
        }
System.out.println("Verse.GetId(" + translationId + ", " + bookId + ", " + chapterId + ", " + verseId + ")=" + intId);
        return intId;
    }

    private int id;
    private String text;
    private TranslationReference transRef;
    private static HashMap translations = new HashMap();//Used to lookup an id
    private static WeakHashMap verseCache = new WeakHashMap();
    private static final String COLUMNS = "Verse_ID, Text, Translation_ID, Book_ID, Chapter, Verse_No";

    private Verse(int id) {
        DbResult result = null;
        String query = "select " + COLUMNS + " from Verse where Verse_ID = " + id;
        try {
            result = Database.Query(query);
            if(result.next()) {
                complete(result);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DbResult.Close(result);
        }
    }

    private Verse(DbResult result) throws SQLException {
        complete(result);
    }

    private void complete(DbResult result) throws SQLException {
        id            = result.getInt("Verse_ID");
        text          = result.getString("Text");
        transRef =  new TranslationReference(Translation.Get(result.getInt("Translation_ID")), 
            new Reference(result.getInt("Book_ID"), result.getInt("Chapter"), result.getInt("Verse_No")));
        verseCache.put(new Integer(id), this);
    }

    /**
     * Reads the id from the Database.  (Also caches the Verse);
     * @author James Stauffer
     */
    private static int ReadId(int translationId, int bookId, int chapterId, int verseId) {
        int id = -1;
        DbResult result = null;
        String query = "select " + COLUMNS + " from Verse where Translation_ID = " + translationId
            + " and Book_ID = " + bookId + " and Chapter = " + chapterId + " and Verse_No = " + verseId;
        try {
            result = Database.Query(query);
            if(result.next()) {
                id = (new Verse(result)).getId();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            DbResult.Close(result);
        }
        return id;
    }

}

