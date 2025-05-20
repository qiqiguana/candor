package net.sourceforge.jwbf.mediawiki.actions.editing;

import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_11;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_12;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_13;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_14;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_15;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_16;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.jwbf.core.actions.Get;
import net.sourceforge.jwbf.core.actions.Post;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.HttpAction;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.mediawiki.actions.MediaWiki;
import net.sourceforge.jwbf.mediawiki.actions.util.MWAction;
import net.sourceforge.jwbf.mediawiki.actions.util.SupportedBy;
import net.sourceforge.jwbf.mediawiki.actions.util.VersionException;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import net.sourceforge.jwbf.mediawiki.contentRep.SimpleFile;

/**
 * <p>
 * To allow your bot to upload media in your MediaWiki. Add at least the following line
 * to your MediaWiki's LocalSettings.php:<br>
 *
 * <pre>
 * $wgEnableUploads = true;
 * </pre>
 *
 * For more details see also
 * <a href="http://www.mediawiki.org/wiki/Help:Configuration_settings#Uploads">Upload Config</a>
 *
 * @author Justus Bisser
 * @author Thomas Stock
 */
@Slf4j
@SupportedBy({ MW1_11, MW1_12, MW1_13, MW1_14, MW1_15, MW1_16 })
public class FileUpload extends MWAction {

    private final Get g;

    private boolean first = true;

    private boolean second = true;

    private final SimpleFile a;

    private Post msg;

    /**
     * @param a the
     * @param bot a
     * @throws ActionException on problems with file
     * @throws VersionException on wrong MediaWiki version
     */
    public FileUpload(final SimpleFile a, MediaWikiBot bot) throws ActionException, VersionException {
    }

    /**
     * @param filename to uplad
     * @param bot a
     * @throws ActionException on problems with file
     * @throws VersionException on wrong MediaWiki version
     */
    public FileUpload(MediaWikiBot bot, String filename) throws ActionException, VersionException {
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
    public String processAllReturningText(String s) throws ProcessException;
}
