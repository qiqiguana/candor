package ghm.follow.io;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 * Implementation of {@link OutputDestination} which appends Strings to a {@link JTextPane}.
 *
 * @see OutputDestination
 * @see JTextPane
 * @author <a href="mailto:carl.hall@gmail.com">Carl Hall</a>
 */
public class JTextPaneDestination implements OutputDestination {

    private Logger log = Logger.getLogger(JTextPaneDestination.class.getName());

    protected JTextPane jTextPane;

    protected boolean autoPositionCaret;

    /**
     * Construct a new JTextPaneDestination.
     *
     * @param jTextPane
     *            text will be appended to this text area
     * @param autoPositionCaret
     *            if true, caret will be automatically moved to the bottom of the text area when
     *            text is appended
     */
    public JTextPaneDestination(JTextPane jTextPane, boolean autoPositionCaret) {
    }

    public JTextPane getJTextPane();

    public void setJTextArea(JTextPane jTextPane);

    /**
     * Add a filtered view to this destination. Filtered views show only a subset of the total
     * output based on filter conditions.
     *
     * @since 1.8.0
     */
    public void addFilteredView();

    /**
     * Remove a filtered view
     *
     * @since 1.8.0
     */
    public void removeFilteredView();

    /**
     * @return whether caret will be automatically moved to the bottom of the text area when text is
     *         appended
     */
    public boolean autoPositionCaret();

    /**
     * @param autoPositionCaret
     *            if true, caret will be automatically moved to the bottom of the text area when
     *            text is appended
     */
    public void setAutoPositionCaret(boolean autoPositionCaret);

    public void print(String s);

    public void clear();
}
