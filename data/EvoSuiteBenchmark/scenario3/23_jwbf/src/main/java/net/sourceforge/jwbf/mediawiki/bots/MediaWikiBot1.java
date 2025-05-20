package net.sourceforge.jwbf.mediawiki.bots;

import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.jwbf.core.actions.ContentProcessable;
import net.sourceforge.jwbf.core.actions.HttpActionClient;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.core.bots.HttpBot;
import net.sourceforge.jwbf.core.bots.WikiBot;
import net.sourceforge.jwbf.core.bots.util.JwbfException;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.core.contentRep.ContentAccessable;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import net.sourceforge.jwbf.core.contentRep.Userinfo;
import net.sourceforge.jwbf.mediawiki.actions.MediaWiki;
import net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version;
import net.sourceforge.jwbf.mediawiki.actions.editing.GetRevision;
import net.sourceforge.jwbf.mediawiki.actions.editing.PostDelete;
import net.sourceforge.jwbf.mediawiki.actions.editing.PostModifyContent;
import net.sourceforge.jwbf.mediawiki.actions.login.PostLogin;
import net.sourceforge.jwbf.mediawiki.actions.login.PostLoginOld;
import net.sourceforge.jwbf.mediawiki.actions.meta.GetUserinfo;
import net.sourceforge.jwbf.mediawiki.actions.meta.GetVersion;
import net.sourceforge.jwbf.mediawiki.actions.meta.Siteinfo;
import net.sourceforge.jwbf.mediawiki.actions.util.VersionException;
import net.sourceforge.jwbf.mediawiki.contentRep.LoginData;

/**
 * This class helps you to interact with each <a href="http://www.mediawiki.org"
 * target="_blank">MediaWiki</a>. This class offers a <b>basic set</b> of
 * methods which are defined in the package net.sourceforge.jwbf.actions.mw.*
 *
 * How to use:
 *
 * <pre>
 * MediaWikiBot b = new MediaWikiBot(&quot;http://yourwiki.org&quot;);
 * b.login(&quot;Username&quot;, &quot;Password&quot;);
 * System.out.println(b.readContent(&quot;Main Page&quot;).getText());
 * </pre>
 *
 * <b>How to find the correct wikiurl</b>
 * <p>
 * The correct wikiurl is sometimes not easy to find, because some wikiadmis
 * uses url rewriting rules. In this cases the correct url is the one, which
 * gives you access to <code>api.php</code>. E.g. Compare
 *
 * <pre>
 * http://www.mediawiki.org/wiki/api.php
 * http://www.mediawiki.org/w/api.php
 * </pre>
 *
 * Thus the correct wikiurl is: <code>http://www.mediawiki.org/w/</code>
 * </p>
 *
 * @author Thomas Stock
 * @author Tobias Knerr
 * @author Justus Bisser
 * @see MediaWikiAdapterBot
 */
@Slf4j
public class MediaWikiBot implements WikiBot {

    /**
     * @param name of article in a mediawiki like "Main Page"
     * @return a content representation of requested article, never null
     * @see GetRevision
     */
    public synchronized Article getArticle(final String name);
}
