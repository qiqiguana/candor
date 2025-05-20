package org.asphodel.parser;

import au.id.jericho.lib.html.Source;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * @author : sunwj
 * @since 0.1
 *        Date: Mar 31, 2007
 *        Time: 10:03:07 PM
 *        <p/>
 *        html content parser using Jericho
 * todo does it need to devided into 2 different implementation?
 */
public class HtmlContentParser implements StringContentParser, StreamContentParser {

    /**
     * @param textContent content as a text format,the real format of the content can be xml,html,xhtml....
     * @return plain text without extra tags or elements
     */
    public String extractPlainText(String textContent) throws ContentParserException;
}
