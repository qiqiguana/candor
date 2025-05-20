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
     * Convenience method since velocity does not understand the conditional
     * "== null". Returns true if this class inherits from a superclass.
     *
     * @return true if this class has a superclass, else false.
     */
    public boolean hasSuperClass() {
        return (this.getSuperClass() != null);
    }

    /**
     * Gets the superclass name for this class.
     * @return the superclass for this class.
     */
    public String getSuperClass();
}
