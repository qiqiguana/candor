import java.util.Vector;

/**Strips any HTML tags from the document, leaving only the text behind.
   TODO: Convert hexadecimal representations of characters into the actual
   characters
**/
public class StripHTML extends Preprocessor {
    
    public Vector<Character> process(Vector<Character> procText) {
	Vector<Character> processed = new Vector<Character>();
	for (int i = 0; i < procText.size(); i++) {
	    if (procText.elementAt(i).equals('<'))
		while (!procText.elementAt(i).equals('>'))
		    i++;
	    else 
		processed.add(procText.elementAt(i));
	}
	return processed;
    }
}
