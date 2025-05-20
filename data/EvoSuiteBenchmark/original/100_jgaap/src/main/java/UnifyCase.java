import java.util.Vector;

/**Elimintes the need for case sensitivity by making all the letters in the
 * document the same case.  For example, "The", "THE", and "the" all become
 * "the".
 **/
public class UnifyCase extends Preprocessor {
    
    public Vector<Character> process(Vector<Character> procText) {
	for (int i = 0; i < procText.size(); i++) 
	    procText.set(i, Character.toLowerCase(procText.elementAt(i)));
	return procText;
    }
}


