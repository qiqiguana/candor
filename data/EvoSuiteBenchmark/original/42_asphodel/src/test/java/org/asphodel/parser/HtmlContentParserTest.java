package org.asphodel.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;

import java.io.Reader;
import java.io.StringReader;

/**
 * @author sunwj
 * @since 0.1
 *        Date: Mar 23, 2007
 *        Time: 4:24:51 PM
 *        Test the logic of HtmlContentParser
 */
public class HtmlContentParserTest {
    private static Log log = LogFactory.getLog(HtmlContentParserTest.class);

    @Test
    public void testExtractTextFormReader() {
        StringContentParser contentParser = new HtmlContentParser();
        String htmlContent ="<PRE>\n" +
                "public <A HREF=\"../../java/io/File.html\" title=\"class in java.io\">File</A>[] <B>listFiles</B>(<A HREF=\"../../java/io/FilenameFilter.html\" title=\"interface in java.io\">FilenameFilter</A>&copy;filter)</PRE>";
        String plainTextContent ="public File[] listFiles(FilenameFilter©filter)";
        try {
            String extractedText=contentParser.extractPlainText(htmlContent);
            assertEquals(plainTextContent, new String(extractedText.getBytes("utf-8")));
//            assertEquals(plainTextContent, contentParser.extractPlainText(htmlContent));
        } catch (Exception e) {
            log.error("when extract content from reader ", e);
            throw new RuntimeException(e);
        }

    }

    /**
     *why it can not parse the '&nbsp;':in fact,the symbol has been parsed correctly,but it can not be displayed on the console
     * while it can be wirte to the text file correctly
     * todo while &nbsp does not equals to ' '; 
     */
    @Test
    public void testExtractTextFormReaderForSpecialChars() throws Exception{
        StreamContentParser contentParser = new HtmlContentParser();
//        String htmlContent ="a&copy;b";
        String htmlContent ="a&copy;b";
//        String htmlContent ="Copyright &copy; 2007 Sohu.com Inc. ";
        String plainTextContent ="a©b";
        Reader reader = new StringReader(htmlContent);

        try {
            assertEquals(plainTextContent,new String(contentParser.extractText(reader).getBytes("utf-8")));
        } catch (ContentParserException e) {
            log.error("when extract content from reader ", e);
            throw new RuntimeException(e);
        }

    }

    //todo other methods 
}
