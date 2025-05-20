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

    /**
     * @return whether caret will be automatically moved to the bottom of the text area when text is
     *         appended
     */
    public boolean autoPositionCaret();
}
