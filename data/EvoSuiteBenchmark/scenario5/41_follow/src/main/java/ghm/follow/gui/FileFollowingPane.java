package ghm.follow.gui;

import ghm.follow.FileFollower;
import ghm.follow.io.JTextComponentDestination;
import ghm.follow.io.OutputDestination;
import ghm.follow.search.SearchableTextPane;
import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * A component which allows one to view a text file to which information is being asynchronously
 * appended.
 *
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class FileFollowingPane extends JScrollPane {

    /**
     * Returns whether caret is automatically repositioned to the end of the text area when text is
     * appended to the followed file
     *
     * @return whether caret is automatically repositioned on append
     */
    public boolean autoPositionCaret() {
        return destination.autoPositionCaret();
    }
}

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
    public boolean autoPositionCaret();
}
