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
    private static Log log = LogFactory.getLog(HtmlContentParser.class);


    /**
     * @param textContent content as a text format,the real format of the content can be xml,html,xhtml....
     * @return plain text without extra tags or elements
     */
    public String extractPlainText(String textContent) throws ContentParserException {
        Source contentSource = new Source(textContent);
        return contentSource.extractText();
    }

    /***/
    public String extractText(Reader reader) throws ContentParserException {
        try {
            Source contentSource = new Source(reader);
            return contentSource.extractText();
        } catch (IOException e) {
            log.error("when parsing from reader,", e);
            throw new ContentParserException(e);
        }
    }


    public String extractText(InputStream inputStream) throws ContentParserException {
        try {
            Source contentSource = new Source(inputStream);
            return contentSource.extractText();
        } catch (IOException e) {
            log.error("when parsing from inputStrem", e);
            throw new ContentParserException(e);
        }
    }

    public String extractText(URL url) throws ContentParserException {
        try {
            Source contentSource = new Source(url);
            return contentSource.extractText();
        } catch (IOException e) {
            log.error("when parsing from url", e);
            throw new ContentParserException(e);
        }
    }
}
