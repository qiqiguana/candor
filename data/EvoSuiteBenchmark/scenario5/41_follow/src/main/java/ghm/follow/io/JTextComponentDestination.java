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

    /**
     * @return whether caret will be automatically moved to the bottom of the text area when text is
     *         appended
     */
    public boolean autoPositionCaret() {
        return autoPositionCaret;
    }
}
