package net.sourceforge.jwbf.mediawiki.actions.queries;

import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_11;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_12;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_13;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_14;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_15;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_16;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.jwbf.core.actions.Get;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.HttpAction;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.core.bots.util.JwbfException;
import net.sourceforge.jwbf.mediawiki.actions.util.SupportedBy;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import net.sourceforge.jwbf.mediawiki.contentRep.CategoryItem;

/**
 * A specialization of {@link CategoryMembers} with contains
 * {@link CategoryItem}s.
 *
 * @author Thomas Stock
 */
@Slf4j
@SupportedBy({ MW1_11, MW1_12, MW1_13, MW1_14, MW1_15, MW1_16 })
public class CategoryMembersFull extends CategoryMembers implements Iterable<CategoryItem>, Iterator<CategoryItem> {

    private Get msg;

    /**
     * Collection that will contain the result (titles of articles linking to the
     * target) after performing the action has finished.
     */
    private Collection<CategoryItem> titleCollection = new ArrayList<CategoryItem>();

    private Iterator<CategoryItem> titleIterator;

    /**
     * @throws ActionException
     *           on any kind of http or version problems
     * @throws ProcessException
     *           on inner problems like a version mismatch
     */
    public CategoryMembersFull(MediaWikiBot bot, String categoryName, int... namespaces) throws ProcessException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addCatItem(String title, int pageid, int ns);

    /**
     * {@inheritDoc}
     */
    public HttpAction getNextMessage();

    /**
     * {@inheritDoc}
     */
    public Iterator<CategoryItem> iterator();

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object clone() throws CloneNotSupportedException;

    private void prepareCollection();

    /**
     * {@inheritDoc}
     */
    @Override
    public String processAllReturningText(String s) throws ProcessException;

    /**
     * {@inheritDoc}
     */
    public boolean hasNext();

    /**
     * {@inheritDoc}
     */
    public CategoryItem next();

    /**
     * {@inheritDoc}
     */
    public void remove();

    @Override
    protected void finalizeParse();
}
