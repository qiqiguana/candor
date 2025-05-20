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

    private final HttpAction msg;

    private boolean hasMore = true;

    private String text = "";

    /**
     * @param u like "/index.php?title=Special:Recentchanges&feed=rss"
     * @param charset like "uft-8"
     */
    public GetPage(String u, String charset) {
    }

    /**
     * @param u like "/index.php?title=Special:Recentchanges&feed=rss"
     */
    public GetPage(String u) {
    }

    /**
     * @return true if
     */
    public boolean hasMoreMessages();

    /**
     * @see ContentProcessable#getNextMessage()
     * @return a
     */
    public HttpAction getNextMessage();

    /**
     * @see ContentProcessable#processReturningText(String, HttpAction)
     * @param s the returning text
     * @param hm the
     * @throws ProcessException on any problems with inner browser
     * @return the returning text
     */
    public String processReturningText(String s, HttpAction hm) throws ProcessException;

    /**
     * @return the requested text
     */
    public String getText();

    /**
     * {@inheritDoc}
     */
    public boolean isSelfExecuter();
}
