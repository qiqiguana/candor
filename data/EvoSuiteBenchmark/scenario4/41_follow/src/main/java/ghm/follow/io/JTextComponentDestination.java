package ghm.follow.io;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

/**
 * Implementation of {@link OutputDestination} which appends Strings to a {@link JTextComponent}.
 *
 * @see OutputDestination
 * @see JTextCompnent
 * @author <a href="mailto:carl.hall@gmail.com">Carl Hall</a>
 */
public class JTextComponentDestination extends FilterableOutputDestination {

    private static final Logger LOG = Logger.getLogger(JTextComponentDestination.class.getName());

    protected JTextComponent comp;

    protected boolean autoPositionCaret;

    /**
     * Construct a new JTextCompnentDestination.
     *
     * @param jTextPane
     *            text will be appended to this text area
     * @param autoPositionCaret
     *            if true, caret will be automatically moved to the bottom of the text area when
     *            text is appended
     */
    public JTextComponentDestination(JTextComponent comp, boolean autoPositionCaret) {
    }

    public JTextComponent getJTextComponent();

    public void setJTextComponent(JTextComponent comp);

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

    public void handlePrint(String s);

    public void clear();
}
