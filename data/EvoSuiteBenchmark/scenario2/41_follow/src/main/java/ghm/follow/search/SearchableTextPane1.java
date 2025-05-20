package ghm.follow.search;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Utilities;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

public class SearchableTextPane extends JTextArea {

    /**
     * Searches for a term at the given starting position.<br>
     * <br>
     * Developer note: this method isn't currently used.
     *
     * @param term The string for which to search.
     * @param startPos Where to start.
     * @return The position where the term was found.<br>
     *         If the term is null, empty or not found, -1 is returned.
     */
    public int search(String term, int startPos) {
        int pos = 0;
        try {
            Document doc = getDocument();
            String text = doc.getText(0, doc.getLength());
            // Search for pattern
            pos = text.indexOf(term, startPos);
        } catch (BadLocationException e) {
            // just return -1;
            log.log(Level.WARNING, "BadLocationException in SearchableTextPane", e);
            pos = -1;
        }
        return pos;
    }
}
