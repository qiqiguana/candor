import java.io.*;
import java.util.Vector;

/**
 *   Code for storing and processing individual documents of any type.
 */
public class Document {

    private String author;

    private String filename;

    private char[] rawText;

    private int size;

    /**
     * Contains current processed text*
     */
    public Vector<Character> procText;

    /**
     * Create and read in document with known text unknown author*
     */
    public Document(String filename) {
    }

    /**
     * Create and read in document with known text and known author*
     */
    public Document(String filename, String author) {
    }

    /**
     * Returns the full filename of the current document*
     */
    public String getFilename();

    /**
     * Sets the author of the current document*
     */
    public void setAuthor(String author);

    /**
     * Retrieves the author of the current document*
     */
    public String getAuthor();

    /**
     * Returns the size of the document.  Size is determined by the
     *  number of characters plus whitespace
     */
    public int getSize();

    /**
     * Returns text with preprocessing done.  Preprocessing can
     *  include stripping whitespace or normalizin the case
     */
    public Vector<Character> getProcessedText();

    /**
     * Reads text from a local file.  Exceptions are not caught
     *  by name.  Rather, all exceptions are handled through just
     *  printing the error messgae to stdout.  This should
     *  probably be changed for robustness.  The raw text of the
     *  file is stored for quick access in an array.
     */
    public void readText(String filename);

    public String toString();

    public void print();

    /**
     * Convert processed document into one really long string.
     *  I'm not quite sure yet why this ever would need to be done.
     */
    public String stringify();
}
