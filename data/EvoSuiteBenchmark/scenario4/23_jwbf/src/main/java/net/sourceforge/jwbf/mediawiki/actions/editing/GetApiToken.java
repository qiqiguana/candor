package net.sourceforge.jwbf.mediawiki.actions.editing;

import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_12;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_13;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_14;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_15;
import static net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version.MW1_16;
import java.io.IOException;
import java.io.StringReader;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.jwbf.core.actions.Get;
import net.sourceforge.jwbf.core.actions.util.HttpAction;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.core.contentRep.Userinfo;
import net.sourceforge.jwbf.mediawiki.actions.MediaWiki;
import net.sourceforge.jwbf.mediawiki.actions.MediaWiki.Version;
import net.sourceforge.jwbf.mediawiki.actions.util.MWAction;
import net.sourceforge.jwbf.mediawiki.actions.util.SupportedBy;
import net.sourceforge.jwbf.mediawiki.actions.util.VersionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

/**
 * Action class using the MediaWiki-<a
 * href="http://www.mediawiki.org/wiki/API:Changing_wiki_content"
 * >Editing-API</a>. <br />
 * Its job is to get the token for some actions like delete or edit.
 *
 * @author Max Gensthaler
 * @author Thomas Stock
 */
@Slf4j
@SupportedBy({ MW1_12, MW1_13, MW1_14, MW1_15, MW1_16 })
public final class GetApiToken extends MWAction {

    // to support different URIs for different actions.
    public enum Intoken {

        DELETE,
        EDIT,
        MOVE,
        PROTECT,
        EMAIL,
        BLOCK,
        UNBLOCK,
        IMPORT
    }

    private String token = "";

    private boolean first = true;

    private Intoken intoken = null;

    private Get msg;

    /**
     * Constructs a new <code>GetToken</code> action.
     *
     * @param intoken
     *          type to get the token for
     * @param title
     *          title of the article to generate the token for
     * @param si
     *          site info object
     * @param ui
     *          user info object
     * @throws VersionException
     *           if this action is not supported of the MediaWiki version
     *           connected to
     */
    public GetApiToken(Intoken intoken, String title, Version v, Userinfo ui) throws VersionException {
    }

    /**
     * Generates the next MediaWiki API token and adds it to <code>msgs</code>.
     *
     * @param intoken
     *          type to get the token for
     * @param title
     *          title of the article to generate the token for
     */
    private void generateTokenRequest(Intoken intoken, String title);

    /**
     * Returns the requested token after parsing the result from MediaWiki.
     *
     * @return the requested token
     */
    protected String getToken();

    /**
     * {@inheritDoc}
     */
    @Override
    public String processReturningText(String s, HttpAction hm) throws ProcessException;

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
     * Processing the XML {@link Document} returned from the MediaWiki API.
     *
     * @param doc
     *          XML <code>Document</code>
     * @throws JDOMException
     *           thrown if the document could not be parsed
     */
    private void process(Document doc);
}
