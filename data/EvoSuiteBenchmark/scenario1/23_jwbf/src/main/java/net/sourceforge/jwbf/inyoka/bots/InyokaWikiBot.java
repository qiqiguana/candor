package net.sourceforge.jwbf.inyoka.bots;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.Set;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.core.bots.HttpBot;
import net.sourceforge.jwbf.core.bots.WikiBot;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import net.sourceforge.jwbf.core.contentRep.Userinfo;
import net.sourceforge.jwbf.inyoka.actions.GetRevision;

/**
 * This class helps you to interact with each wiki as part of <a
 * href="http://ubuntuusers.de" target="_blank">Inyoka</a>. This class offers a
 * set of methods which are defined in the package
 * net.sourceforge.jwbf.actions.inyoka.*
 *
 * @author Thomas Stock
 */
public class InyokaWikiBot extends HttpBot implements WikiBot {

    public synchronized Article getArticle(final String name) throws ActionException, ProcessException {
        return getArticle(name, 0);
    }
}
