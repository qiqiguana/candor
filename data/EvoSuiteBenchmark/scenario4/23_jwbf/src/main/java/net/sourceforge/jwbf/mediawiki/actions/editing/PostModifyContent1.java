package net.sourceforge.jwbf.mediawiki.actions.editing;

import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_09;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_10;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_11;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_12;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_13;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_14;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_15;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_16;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.jwbf.core.actions.Get;
import net.sourceforge.jwbf.core.actions.Post;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.HttpAction;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.core.bots.util.JwbfException;
import net.sourceforge.jwbf.core.contentRep.ContentAccessable;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import net.sourceforge.jwbf.mediawiki.actions.MediaWiki;
import net.sourceforge.jwbf.mediawiki.actions.util.MWAction;
import net.sourceforge.jwbf.mediawiki.actions.util.SupportedBy;
import net.sourceforge.jwbf.mediawiki.actions.util.VersionException;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

/**
 * Writes an article.
 *
 * @author Thomas Stock
 */
@Slf4j
@SupportedBy({ MW1_09, MW1_10, MW1_11, MW1_12, MW1_13, MW1_14, MW1_15, MW1_16 })
public class PostModifyContent extends MWAction {

    private boolean first = true;

    private boolean second = true;

    private final ContentAccessable a;

    private Hashtable<String, String> tab = new Hashtable<String, String>();

    private MediaWikiBot bot;

    private GetApiToken apiReq = null;

    private HttpAction apiGet = null;

    private HttpAction initOldGet = null;

    private Post postModify = null;

    private boolean apiEdit = false;

    /**
     * @param bot a
     * @param a the
     * @throws ProcessException a
     * @throws ActionException a
     */
    public PostModifyContent(MediaWikiBot bot, final SimpleArticle a) throws ActionException, ProcessException {
    }

    /**
     * {@inheritDoc}
     */
    public HttpAction getNextMessage();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMoreMessages();

    /**
     * {@inheritDoc}
     */
    @Override
    public String processReturningText(String s, HttpAction hm) throws ProcessException;

    /**
     * @param text
     *            where to search
     * @param tab
     *            tabel with required values
     */
    private void getWpValues(final String text, Hashtable<String, String> tab);

    /**
     * @param a a
     * @param b a
     * @return true if one or both sets are <code>null</code> or the intersection of sets is empty.
     */
    @SuppressWarnings("unchecked")
    public static boolean isIntersectionEmpty(Set<?> a, Set<?> b);
}
