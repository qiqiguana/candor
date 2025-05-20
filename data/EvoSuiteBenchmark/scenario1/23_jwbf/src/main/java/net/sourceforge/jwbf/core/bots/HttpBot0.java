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

    @Deprecated
    public static HttpBot getInstance() {
        try {
            return new HttpBot(new URL("http://localhost/"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
