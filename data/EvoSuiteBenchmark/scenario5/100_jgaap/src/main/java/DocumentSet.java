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

    /**
     * Number of documents currently registered in this set of documents*
     */
    public int documentCount() {
        return documents.size();
    }
}
