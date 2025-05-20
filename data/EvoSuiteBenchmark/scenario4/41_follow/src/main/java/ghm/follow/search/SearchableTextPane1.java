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

    private Logger log = Logger.getLogger(SearchableTextPane.class.getName());

    private int lastSearchPos = -1;

    private String lastSearchTerm;

    private final DefaultHighlightPainter wordPainter = new DefaultHighlightPainter(Color.YELLOW);

    public SearchableTextPane(Font font, int tabSize) {
    }

    /**
     * Override this to keep the text from wrapping and to make the viewable area as wide as the
     * tabbed pane
     */
    public boolean getScrollableTracksViewportWidth();

    /**
     * Highlight <code>term</code> wherever it is found in the view. Also highlights the entire
     * line on which the term is found.
     *
     * @param term
     * @param caseSensitive
     * @param useRegularExpression
     * @return
     */
    public List<LineResult> highlight(String term, int flags);

    /**
     * Highlight a piece of text in the document
     *
     * @param start
     * @param wordEnd
     * @param highlighter
     */
    private void addHighlight(int start, int length) throws BadLocationException;

    /**
     * Removes highlights from text area
     */
    public void removeHighlights();

    /**
     * Searches for a term. If the term provided matches the last searched term, the last found
     * position is used as a starting point.<br>
     * <br>
     * Developer note: this method isn't currently used.
     *
     * @param term
     *            The string for which to search.
     * @return The position where the term was found.<br>
     *         If the term is null, empty or not found, -1 is returned.
     */
    public int search(String term);

    /**
     * Searches for a term at the given starting position.<br>
     * <br>
     * Developer note: this method isn't currently used.
     *
     * @param term
     *            The string for which to search.
     * @param startPos
     *            Where to start.
     * @return The position where the term was found.<br>
     *         If the term is null, empty or not found, -1 is returned.
     */
    public int search(String term, int startPos);

    /**
     * Converts word results from search into line results
     *
     * @param words
     * @return
     */
    private List<LineResult> convertWords2Lines(List<WordResult> words) throws BadLocationException;

    /**
     * Adds word result to line result and updates line information
     *
     * @param wordResult
     * @param lineResult
     */
    private void updateWordResult(WordResult wordResult, LineResult lineResult) throws BadLocationException;
}
