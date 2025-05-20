import java.util.Vector;
    
/**Changes length of all white spaces to 1.  Any sequence of whitespaces
 * including newline, tab, and space, will become a single space in the 
 * processed document.
 **/
public class NormalizeWhitespace extends Preprocessor {

    public Vector<Character> process(Vector<Character> procText) {
	Vector<Character> processed = new Vector<Character>();
	boolean spaceflag = false;
	for (int i = 0; i < procText.size(); i++) {
	    if (Character.isWhitespace(procText.elementAt(i)) && !spaceflag) { 
		processed.add(new Character(' '));
		spaceflag = true;
	    }
	    else if (!Character.isWhitespace(procText.elementAt(i))) {
		processed.add(procText.elementAt(i));
		spaceflag = false;
	    }
	}
	return processed;
    }
}
