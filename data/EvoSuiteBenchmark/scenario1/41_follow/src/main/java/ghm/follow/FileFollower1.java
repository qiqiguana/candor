package ghm.follow;

import ghm.follow.io.OutputDestination;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Instances of this class 'follow' a particular text file, assmebling that
 * file's characters into Strings and sending them to instances of
 * {@link OutputDestination}. The name and behavior of this class are inspired
 * by the '-f' (follow) flag of the UNIX command 'tail'.
 *
 * @see OutputDestination
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class FileFollower {

    public boolean removeOutputDestination(OutputDestination outputDestination) {
        return outputDestinations.remove(outputDestination);
    }
}
