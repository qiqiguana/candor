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

    /**
     * Simple method to get plain HTML or XML data e.g. from custom specialpages
     * or xml newsfeeds.
     *
     * @param u url like index.php?title=Main_Page
     * @return HTML content
     */
    public final String getPage(String u) {
        try {
            URL url = new URL(u);
            setConnection(url.getProtocol() + "://" + url.getHost());
        } catch (MalformedURLException e) {
            throw new ActionException(e);
        }
        GetPage gp = new GetPage(u);
        try {
            performAction(gp);
        } catch (ProcessException e) {
            throw new ActionException(e);
        }
        return gp.getText();
    }
}
