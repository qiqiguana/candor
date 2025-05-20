/**  DocumentSet.java
 *   Caleb Astey - 2007
 */

import java.util.Vector;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.*;

/**A Document Set is a group of documents written by the same author.  
 * This can be used to facilitate event sets that are larger than just 
 * a single document, which may be more indicative of an author's 
 * entire body of work.
 */
public class DocumentSet {

    public Hashtable frequency;
    Vector<Document> documents;

    DocumentSet() {
	documents = new Vector<Document>();
    }

    DocumentSet(Document d) {
	documents = new Vector<Document>();
	documents.add(d);
    }

    /**Registers a new document to the list of documents by a given author.  
     * The document is appended on to the end of the list.  
     **/
    public void register(Document d) {
	documents.add(d);
    }


    /**Number of documents currently registered in this set of documents**/
    public int documentCount() {
	return documents.size();
    }

    /**Returns an individual indexed documement.  The index is given by the 
     * order in which the documents were registered with the DocumentSet
     **/
    public Document getDocument(int index) {
	return documents.elementAt(index);
    }

    /**Calculates the frequency of individual characters within the entire 
     * set of documents.  Each character is a key in a hashtable with the value
     * being the frequency of occurrance. This is legacy code rewritten and was 
     * included for completeness.
     **/  
    public void characterFrequency() {
	frequency = new Hashtable();
	for (int i = 0; i < documents.size(); i++) {
	    Vector<Character> pt = documents.elementAt(i).getProcessedText();
	    for (int j = 0; j < documents.elementAt(i).getSize(); j++) {
		char letter = pt.elementAt(j);
		if (frequency.containsKey(letter)) {
		    Integer t = (Integer)frequency.get(letter);
		    int ti = t.intValue() + 1;
		    frequency.put(letter, new Integer(ti));
		}
		else
		    frequency.put(letter, new Integer(1));
	    }
	}
    }


    /**Calculates the frequency of full  words within the entire 
     * set of documents.  Each word is a key in a hashtable with the value
     * being the frequency of occurrance. This is legacy code rewritten and was 
     * included for completeness.
     **/     
    public void wordFrequency() {
	frequency = new Hashtable();
	for (int i = 0; i < documents.size(); i++) {
	    String stDoc = documents.elementAt(i).stringify();
	    StringTokenizer st = new StringTokenizer(stDoc, " .,;:?!\"");
	    while (st.hasMoreTokens()) {
		String word = st.nextToken();
		if (frequency.containsKey(word)) {
		    Integer t = (Integer)frequency.get(word);
		    int ti = t.intValue() + 1;
		    frequency.put(word, new Integer(ti));
		}
		else
		    frequency.put(word, new Integer(1));
	    }
	}
    }

    /**Returns the top most common words in the document with the rest 
     * replaced with a placeholder.  This is also legacy code, rewritten, 
     * generalized, and replaced from the old code.  
     * Side Note:  This code should probably be moved to the EventSet class, 
     * along with the frequency analysis classes.  This will allow character
     * and word frequencies to be generalized to event frequencies, by returning
     * the N most common events, replacing the rest with a generic event.  
     **/
    public void mostCommon(int n) {
	Vector keys = new Vector();
	Vector values = new Vector();
	Vector<kvp> kvps = new Vector<kvp>();
	Enumeration ekeys = frequency.keys();
	while (ekeys.hasMoreElements()) {
	    Object temp = ekeys.nextElement();
	    kvps.add(new kvp(temp,(Integer)frequency.get(temp)));
	}
 
	Collections.sort(kvps);
	Collections.reverse(kvps);

	for (int i = 0; i < n; i++) 
	    System.out.println(kvps.elementAt(i));
	
    }

}

/**kvp -> key value pair.  This is a specific 
 * hashtable implementation for the frequency 
 * analysis portions of DocumentSet.mostCommon()
 **/
class kvp implements Comparable {
    Object key;
    int value;

    public kvp(Object key, Integer value) {
	this.key = key;
	this.value = value.intValue();
    }

    public int compareTo(Object ol) {
	if (this.value == ((kvp)ol).value)
	    return 0;
	else if (this.value < ((kvp)ol).value)
	    return -1;
	else return 1;
    }

    public String toString() {
	String t = new String();
        t = value + ":\t" + key;
	return  t;
    }
}
