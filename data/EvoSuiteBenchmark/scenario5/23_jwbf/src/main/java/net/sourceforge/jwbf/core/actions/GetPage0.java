package net.sourceforge.jwbf.core.actions;

import net.sourceforge.jwbf.core.actions.util.HttpAction;
import net.sourceforge.jwbf.core.actions.util.ProcessException;

/**
 * Simple method to get plain HTML or XML data e.g. from custom specialpages
 * or xml newsfeeds or something else.
 *
 * @author Thomas Stock
 */
public class GetPage implements ContentProcessable {

    /**
     * @return true if
     */
    public boolean hasMoreMessages() {
        final boolean b = hasMore;
        hasMore = false;
        return b;
    }
}
