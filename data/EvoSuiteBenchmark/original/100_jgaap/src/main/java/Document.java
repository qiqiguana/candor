import java.io.*;
import java.util.Vector;

/**   Code for storing and processing individual documents of any type.*/
public class Document {

    private String author;
    private String filename;
    private char[] rawText;
    private int size;

    /**Contains current processed text**/
    public Vector<Character> procText;


    /** Create and read in document with known text unknown author**/
    public Document(String filename) {
	this.author = null;
	this.filename = filename;
	readText(filename);
    }


    /**Create and read in document with known text and known author**/
    public Document(String filename, String author) {
	this.author = author;
	this.filename = filename;
	readText(filename);
    }

    /**Returns the full filename of the current document**/
    public String getFilename() {
	return filename;
    }

    /**Sets the author of the current document**/
    public void setAuthor(String author) {
	this.author = author;
    }

    /**Retrieves the author of the current document**/
    public String getAuthor() {
	return author;
    }


    /**Returns the size of the document.  Size is determined by the 
     * number of characters plus whitespace
    **/
    public int getSize() {
	return size;
    }


    /**Returns text with preprocessing done.  Preprocessing can 
     * include stripping whitespace or normalizin the case
     **/
    public Vector<Character> getProcessedText() {
	return procText;
    }

    
    /** Reads text from a local file.  Exceptions are not caught 
     *  by name.  Rather, all exceptions are handled through just
     *  printing the error messgae to stdout.  This should 
     *  probably be changed for robustness.  The raw text of the 
     *  file is stored for quick access in an array.
     **/
    public void readText(String filename) {
	int c, ctr = 0;
	try {
	    File input = new File(filename);
	    this.size = (int)input.length();
	    this.rawText = new char[size];
	    this.procText = new Vector<Character>();
	    FileInputStream fis = new FileInputStream(input);
	    while ((c = fis.read()) != -1) {
		rawText[ctr++] = (char)c;
		procText.add(new Character((char)c));
	    }
		
	}
	catch (IOException e) {
	    System.out.println(e.getMessage());
	}
    }

    public String toString() {
	String t = new String();
	t  = "Document: " + filename + "\n";
	t += "Author:   " + author + "\n";
	return t;
    }

    public void print() {
	for (Character c: procText)
	    System.out.print(c);
    }

    /**Convert processed document into one really long string. 
     * I'm not quite sure yet why this ever would need to be done.
     **/
    public String stringify() {
	String t = new String();
	for (int i = 0; i < procText.size(); i++)
	    t += (char)procText.elementAt(i);
	return t;
    }
}
