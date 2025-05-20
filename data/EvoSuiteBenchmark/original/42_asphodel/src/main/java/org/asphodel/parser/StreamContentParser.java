package org.asphodel.parser;

import java.io.Reader;
import java.io.InputStream;
import java.net.URL;

/**
 * @author: sunwj
 * @since 0.1
 * Date: Mar 23, 2007
 * Time: 4:15:36 PM
 *
 * parser to parse the content of the given stream resource to </br>
 * extract string content as text or stream.
 */
public interface StreamContentParser {
    /***/
    public String extractText(Reader reader)throws ContentParserException;

    public String extractText(InputStream inputStream)throws ContentParserException;
    public String extractText(URL url)throws ContentParserException;
/*    
    public String extractStream(InputStream inputStream);
    public String extractStream(URL url);
*/

}
