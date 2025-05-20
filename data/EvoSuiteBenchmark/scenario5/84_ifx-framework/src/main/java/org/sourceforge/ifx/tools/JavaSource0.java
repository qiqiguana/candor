package org.sourceforge.ifx.tools;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class provides an abstraction to collect information about a
 * generated Java class file. Its toString() method will produce a String
 * that can be written using a Writer object to persistent store.
 *
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.5 $
 */
public class JavaSource {

    /**
     * Convenience methods since velocity does not understand the conditional
     * "== null". Returns true if this class implements an interface.
     *
     * @return true if this class implements an interface else false.
     */
    public boolean hasInterface() {
        return (this.getInterface() != null);
    }

    /**
     * Gets the fully qualified interface for this class if specified,
     * @return the interface name for this class.
     */
    public String getInterface();
}
