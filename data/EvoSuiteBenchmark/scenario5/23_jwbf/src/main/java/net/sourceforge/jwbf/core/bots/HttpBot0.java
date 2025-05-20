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
     * Returns a {@link HttpBot} which supports only its basic methods. Use
     * {@link #getPage(String)} for an basic read of content.
     *
     * @deprecated do not use this
     * @return a
     */
    @Deprecated
    public static HttpBot getInstance() {
        try {
            return new HttpBot(new URL("http://localhost/"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
