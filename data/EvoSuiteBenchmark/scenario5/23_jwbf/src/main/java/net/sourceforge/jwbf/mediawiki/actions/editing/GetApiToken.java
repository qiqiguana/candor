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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMoreMessages() {
        return first;
    }
}
