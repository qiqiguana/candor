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
     * Searches for a term. If the term provided matches the last searched term, the last found
     * position is used as a starting point.<br>
     * <br>
     * Developer note: this method isn't currently used.
     *
     * @param term The string for which to search.
     * @return The position where the term was found.<br>
     *         If the term is null, empty or not found, -1 is returned.
     */
    public int search(String term) {
        if (term != null && term.length() > 0) {
            if (term.equals(lastSearchTerm)) {
                // assume to start at the beginning
                int pos = 0;
                // if there is a previous search position, start there plus the
                // length
                // of the last term so that last term again isn't found again
                if (lastSearchPos != -1) {
                    pos = lastSearchPos + lastSearchTerm.length();
                }
                lastSearchPos = search(lastSearchTerm, pos);
            } else {
                lastSearchPos = search(term, 0);
            }
        }
        // remember the term if it was found
        if (lastSearchPos == -1) {
            lastSearchTerm = null;
        } else {
            lastSearchTerm = term;
        }
        return lastSearchPos;
    }
}
