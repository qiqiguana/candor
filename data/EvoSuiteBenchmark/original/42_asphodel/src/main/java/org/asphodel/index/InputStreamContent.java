package org.asphodel.index;

import org.asphodel.parser.ContentParserException;

import java.io.InputStream;

/**
 * @author : Sun Wenju
 *         Date: Feb 10, 2008 11:29:04 PM
 */
public class InputStreamContent extends IndexeeContent{
    private InputStream inputStream;

    public InputStreamContent(InputStream inputStream) {
        this.inputStream = inputStream;
    }

/*
    public  Reader getReader() throws ContentParserException {
        StringReader stringReader = new StringReader(null);

        return null;
    }
*/

    //todo this is more convenient
    public String getContent() throws ContentParserException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
