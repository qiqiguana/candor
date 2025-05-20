package org.asphodel.parser;

/**
 * @author sunwj
 * @since 0.1
 *        Date: Apr 1, 2007
 *        Time: 12:46:37 PM
 *        only parse the content denote a string
 */
public interface StringContentParser {
    /**
     * 
     * @param textContent content as a text format,the real format of the content can be xml,html,xhtml....
     * @return plain text without extra tags or elements
     * */
    public String extractPlainText(String textContent) throws ContentParserException;
}
