package net.sourceforge.jwbf.core.bots;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import net.sourceforge.jwbf.core.actions.ContentProcessable;
import net.sourceforge.jwbf.core.actions.Get;
import net.sourceforge.jwbf.core.actions.GetPage;
import net.sourceforge.jwbf.core.actions.HttpActionClient;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.ProcessException;

public class HttpBot {

    private HttpActionClient cc;

    private String url;

    /**
     * do nothing, but keep in mind, that you have to setup the connection
     */
    public HttpBot() {
    }

    /**
     * Design for extension.
     *
     * @param url
     *          of the host
     */
    public HttpBot(final String url) {
    }

    /**
     * Design for extension.
     *
     * @param cc
     *          a
     */
    public HttpBot(HttpActionClient cc) {
    }

    /**
     * Design for extension.
     *
     * @param url
     *          of the host
     */
    public HttpBot(final URL url) {
    }

    /**
     * Returns a {@link HttpBot} which supports only its basic methods. Use
     * {@link #getPage(String)} for an basic read of content.
     *
     * @deprecated do not use this
     * @return a
     */
    @Deprecated
    public static HttpBot getInstance();

    /**
     * @param client
     *          if you whant to add some specials
     */
    public final void setConnection(final HttpActionClient client);

    public final String getHostUrl();

    /**
     * @param a
     *          a
     * @return text
     */
    public synchronized String performAction(final ContentProcessable a);

    /**
     * @param hostUrl
     *          base url of a wiki site to connect with; example:
     *          http://www.yourOwnWiki.org/wiki/
     */
    public final void setConnection(final String hostUrl);

    /**
     * Simple method to get plain HTML or XML data e.g. from custom specialpages
     * or xml newsfeeds.
     *
     * @param u
     *          url like index.php?title=Main_Page
     * @return HTML content
     */
    public final String getPage(String u);

    /**
     * Simple method to get plain HTML or XML data e.g. from custom specialpages
     * or xml newsfeeds.
     *
     * @param u
     *          url like index.php?title=Main_Page
     * @return HTML content
     */
    public final byte[] getBytes(String u);

    /**
     * @return a
     */
    public final HttpActionClient getClient();

    /**
     * @param hostUrl
     *          like http://www.yourOwnWiki.org/wiki/
     */
    public final void setConnection(final URL hostUrl);

    /**
     * TODO check usage of hosturl
     *
     * @deprecated
     */
    @Deprecated
    public String getUrl();
}
