package org.asphodel.index;

import org.asphodel.parser.ContentParserException;

/**
 * @author sunwj
 * @since 0.1
 * Date: Apr 7, 2007
 * Time: 9:50:54 PM
 */
public class PlainTextStringContent extends IndexeeContent {
    private String content;

    public PlainTextStringContent(String content) {
        this.content = content;
    }


    public String getContent() throws ContentParserException {
        return this.content;
    }
}
