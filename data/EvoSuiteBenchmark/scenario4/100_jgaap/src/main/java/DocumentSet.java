import java.util.Vector;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.*;

/**
 * A Document Set is a group of documents written by the same author.
 * This can be used to facilitate event sets that are larger than just
 * a single document, which may be more indicative of an author's
 * entire body of work.
 */
public class DocumentSet {

    public Hashtable frequency;

    Vector<Document> documents;

    DocumentSet() {
    }

    DocumentSet(Document d) {
    }

    /**
     * Registers a new document to the list of documents by a given author.
     *  The document is appended on to the end of the list.
     */
    public void register(Document d);

    /**
     * Number of documents currently registered in this set of documents*
     */
    public int documentCount();

    /**
     * Returns an individual indexed documement.  The index is given by the
     *  order in which the documents were registered with the DocumentSet
     */
    public Document getDocument(int index);

    /**
     * Calculates the frequency of individual characters within the entire
     *  set of documents.  Each character is a key in a hashtable with the value
     *  being the frequency of occurrance. This is legacy code rewritten and was
     *  included for completeness.
     */
    public void characterFrequency();

    /**
     * Calculates the frequency of full  words within the entire
     *  set of documents.  Each word is a key in a hashtable with the value
     *  being the frequency of occurrance. This is legacy code rewritten and was
     *  included for completeness.
     */
    public void wordFrequency();

    /**
     * Returns the top most common words in the document with the rest
     *  replaced with a placeholder.  This is also legacy code, rewritten,
     *  generalized, and replaced from the old code.
     *  Side Note:  This code should probably be moved to the EventSet class,
     *  along with the frequency analysis classes.  This will allow character
     *  and word frequencies to be generalized to event frequencies, by returning
     *  the N most common events, replacing the rest with a generic event.
     */
    public void mostCommon(int n);
}
