package org.asphodel.index;

import org.asphodel.parser.ContentParserException;
import org.asphodel.parser.StringContentParser;
import org.asphodel.parser.HtmlContentParser;

/**
 * @author sunwj
 * @version 0.1
 * @since 0.1
 * Date: Apr 5, 2007
 * Time: 12:43:30 PM
 * This class denotes the html format string content that can be parsed by <br/>
 * @see org.asphodel.parser.HtmlContentParser
 */
public class HtmlStringContent extends IndexeeContent {
    private String content;
    public HtmlStringContent(String content) {
       this.content = content;
    }

    //todo this is more convenient
    public String getContent() throws ContentParserException {
        StringContentParser stringContentParser = new HtmlContentParser();
        return  stringContentParser.extractPlainText(this.content);
    }
}
