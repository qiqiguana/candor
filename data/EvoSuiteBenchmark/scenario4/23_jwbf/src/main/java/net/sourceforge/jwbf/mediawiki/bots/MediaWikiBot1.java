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

    private LoginData login = null;

    private Version version = null;

    private Userinfo ui = null;

    private boolean loginChangeUserInfo = false;

    private boolean loginChangeVersion = false;

    private boolean useEditApi = true;

    @Inject
    private HttpBot bot;

    /**
     * These chars are not allowed in article names.
     */
    public static final char[] INVALID_LABEL_CHARS = "[]{}<>|".toCharArray();

    private static final int DEFAULT_READ_PROPERTIES = GetRevision.CONTENT | GetRevision.COMMENT | GetRevision.USER | GetRevision.TIMESTAMP | GetRevision.IDS | GetRevision.FLAGS;

    private static final Set<String> emptySet = Collections.unmodifiableSet(new HashSet<String>());

    /**
     * use this constructor, if you want to work with IoC.
     */
    public MediaWikiBot() {
    }

    /**
     * @param u
     *          wikihosturl like "http://www.mediawiki.org/w/"
     */
    public MediaWikiBot(final URL u) {
    }

    /**
     * @param client
     *          a
     */
    public MediaWikiBot(final HttpActionClient client) {
    }

    public MediaWikiBot(final String url) {
    }

    /**
     * @param url
     *          wikihosturl like "http://www.mediawiki.org/w/"
     * @param testHostReachable
     *          if true, test if host reachable
     */
    public MediaWikiBot(URL url, boolean testHostReachable) {
    }

    /**
     * Performs a Login.
     *
     * @param username
     *          the username
     * @param passwd
     *          the password
     * @param domain
     *          login domain (Special for LDAPAuth extention to authenticate
     *          against LDAP users)
     * @see PostLogin
     * @see PostLoginOld
     */
    public void login(final String username, final String passwd, final String domain);

    /**
     * TODO mv doc
     *
     * Performs a Login. Actual old cookie login works right, because is pending
     * on {@link #writeContent(ContentAccessable)}
     *
     * @param username
     *          the username
     * @param passwd
     *          the password
     * @see PostLogin
     * @see PostLoginOld
     */
    public void login(final String username, final String passwd);

    /**
     * @param name
     *          of article in a mediawiki like "Main Page"
     * @param properties
     *          {@link GetRevision}
     * @return a content representation of requested article, never null
     * @see GetRevision
     */
    public synchronized Article getArticle(final String name, final int properties);

    /**
     * {@inheritDoc}
     */
    public synchronized SimpleArticle readData(final String name, final int properties);

    /**
     * {@inheritDoc}
     */
    public SimpleArticle readData(String name);

    /**
     * @param name
     *          of article in a mediawiki like "Main Page"
     * @return a content representation of requested article, never null
     * @see GetRevision
     */
    public synchronized Article getArticle(final String name);

    /**
     * {@inheritDoc}
     */
    public synchronized void writeContent(final SimpleArticle simpleArticle);

    /**
     * @return true if
     */
    public final boolean isLoggedIn();

    /**
     * {@inheritDoc}
     */
    public Userinfo getUserinfo();

    /**
     * {@inheritDoc}
     */
    public void delete(String title);

    public synchronized String performAction(ContentProcessable a);

    private HttpBot getBot();

    /**
     * @return the
     * @throws IllegalStateException
     *           if no version was found.
     * @see #getSiteinfo()
     */
    @Nonnull
    public Version getVersion() throws IllegalStateException;

    /**
     * @return a
     * @throws ActionException
     *           on problems with http, cookies and io
     * @see Siteinfo
     */
    @Nonnull
    public Siteinfo getSiteinfo();

    /**
     * @return the
     */
    public final boolean isEditApi();

    /**
     * @param useEditApi
     *          Set to false, to force editing without the API.
     */
    public final void useEditApi(boolean useEditApi);

    /**
     * {@inheritDoc}
     */
    public final String getWikiType();

    public String getHostUrl();
}
