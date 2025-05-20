package net.sourceforge.jwbf.mediawiki.actions.queries;

import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_11;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_12;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_13;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_14;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_15;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_16;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.jwbf.core.actions.Get;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.HttpAction;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.mediawiki.actions.util.MWAction;
import net.sourceforge.jwbf.mediawiki.actions.util.SupportedBy;
import net.sourceforge.jwbf.mediawiki.actions.util.VersionException;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import net.sourceforge.jwbf.mediawiki.contentRep.LogItem;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

/**
 * List log events, filtered by time range, event type, user type, or the page
 * it applies to. Ordered by event timestamp. Parameters: letype (flt), lefrom
 * (paging timestamp), leto (flt), ledirection (dflt=older), leuser (flt),
 * letitle (flt), lelimit (dflt=10, max=500/5000)
 *
 * api.php ? action=query & list=logevents      - List last 10 events of any type
 *
 * TODO This is a semi-complete extension point
 *
 * @author Thomas Stock
 */
@Slf4j
@SupportedBy({ MW1_11, MW1_12, MW1_13, MW1_14, MW1_15, MW1_16 })
public class LogEvents extends MWAction implements Iterator<LogItem>, Iterable<LogItem> {

    public static final String BLOCK = "block";

    public static final String PROTECT = "protect";

    public static final String RIGHTS = "rights";

    public static final String DELETE = "delete";

    public static final String UPLOAD = "upload";

    public static final String MOVE = "move";

    public static final String IMPORT = "mport";

    public static final String PATROL = "patrol";

    public static final String MERGE = "merge";

    private final int limit;

    private Get msg;

    private final MediaWikiBot bot;

    /* first run variable */
    private boolean init = true;

    private boolean selvEx = true;

    /**
     * Collection that will contain the result (titles of articles linking to
     * the target) after performing the action has finished.
     */
    private Collection<LogItem> logCollection = new Vector<LogItem>();

    private Iterator<LogItem> logIterator = null;

    private final String[] type;

    private String nextPageInfo = "";

    private boolean hasMoreResults = true;

    /**
     * @param bot a
     * @param type of like {@link #MOVE}
     * @throws VersionException if incompatible with this version
     */
    public LogEvents(MediaWikiBot bot, String type) throws VersionException {
    }

    /**
     * @param bot a
     * @param type of like {@link #MOVE}
     * @throws VersionException if incompatible with this version
     */
    public LogEvents(MediaWikiBot bot, String[] type) throws VersionException {
    }

    /**
     * @param bot a
     * @param limit of events
     * @param type of like {@link #MOVE}
     * @throws VersionException if incompatible with this version
     */
    public LogEvents(MediaWikiBot bot, int limit, String type) throws VersionException {
    }

    /**
     * @param bot a
     * @param limit of events
     * @param type of like {@link #MOVE}
     * @throws VersionException if incompatible with this version
     */
    public LogEvents(MediaWikiBot bot, int limit, String[] type) throws VersionException {
    }

    /**
     * generates the next MediaWiki-request (GetMethod) and adds it to msgs.
     *
     * @param logtype
     *            type of log, like upload
     * @return a
     */
    private Get generateRequest(String... logtype);

    /**
     * generates the next MediaWiki-request (GetMethod) and adds it to msgs.
     *
     * @param logtype
     *            type of log, like upload
     * @return a
     */
    private Get generateContinueRequest(String[] logtype, String continueing);

    /**
     * {@inheritDoc}
     */
    @Override
    public String processAllReturningText(final String s) throws ProcessException;

    /**
     * picks the article name from a MediaWiki api response.
     *
     * @param s
     *            text for parsing
     */
    private void parseArticleTitles(String s);

    /**
     * gets the information about a follow-up page from a provided api response.
     * If there is one, a new request is added to msgs by calling generateRequest.
     *
     * @param s   text for parsing
     */
    private void parseHasMore(final String s);

    @SuppressWarnings("unchecked")
    private void findContent(final Element root);

    private void prepareCollection();

    /**
     * {@inheritDoc}
     */
    public HttpAction getNextMessage();

    /**
     * {@inheritDoc}
     */
    public boolean hasNext();

    /**
     * {@inheritDoc}
     */
    public LogItem next();

    /**
     * {@inheritDoc}
     */
    public void remove();

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Iterator<LogItem> iterator();

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object clone() throws CloneNotSupportedException;

    /**
     * {@inheritDoc}
     * @deprecated see super
     */
    @Deprecated
    @Override
    public boolean isSelfExecuter();
}
