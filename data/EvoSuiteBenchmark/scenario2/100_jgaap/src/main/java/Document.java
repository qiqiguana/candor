import java.io.*;
import java.util.Vector;

/**
 *   Code for storing and processing individual documents of any type.
 */
public class Document {

    /**
     * Convert processed document into one really long string.
     * I'm not quite sure yet why this ever would need to be done.
     */
    public String stringify() {
        String t = new String();
        for (int i = 0; i < procText.size(); i++) t += (char) procText.elementAt(i);
        return t;
    }
}
