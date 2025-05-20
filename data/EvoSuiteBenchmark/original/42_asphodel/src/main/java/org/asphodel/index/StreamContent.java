package org.asphodel.index;

import org.asphodel.parser.ContentParserException;

import java.io.Reader;
import java.io.InputStream;

/**
 * User: gjkv86
 * Date: Apr 7, 2007
 * Time: 12:09:50 PM
 */
public abstract class StreamContent {
    
    public abstract Reader getExtractedReader() throws ContentParserException;

    public abstract InputStream getExtractedStream() throws ContentParserException;
}
