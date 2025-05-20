package org.asphodel.index;

import org.asphodel.parser.ContentParserException;

import java.io.Reader;

/**
 * @author sunwj
 * @since 0.1
 *        Date: Apr 7, 2007
 *        Time: 12:20:08 PM
 *        This represents the content to be indexed
 */
public abstract class IndexeeContent {



 //   public abstract Reader getReader() throws ContentParserException;

    //todo this is more convenient
    public abstract String getContent() throws ContentParserException;

}
