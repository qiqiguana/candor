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
     * @see ContentProcessable#processReturningText(String, HttpAction)
     * @param s the returning text
     * @param hm the
     * @throws ProcessException on any problems with inner browser
     * @return the returning text
     */
    public String processReturningText(String s, HttpAction hm) throws ProcessException;
}
