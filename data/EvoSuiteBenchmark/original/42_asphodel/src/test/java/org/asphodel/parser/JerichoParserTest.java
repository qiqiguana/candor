package org.asphodel.parser;

import au.id.jericho.lib.html.Source;
import static org.testng.AssertJUnit.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

/**
 * User: gjkv86
 * Date: Mar 23, 2007
 * Time: 3:46:45 PM
 */
public class JerichoParserTest {
    public static void main(String args[]) {
        JerichoParserTest parserTest = new JerichoParserTest();
        try {
            parserTest.testGetText2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testGetText() throws Exception {

        InputStream sourceStream = new FileInputStream("C:/TDDOWNLOAD/opensource/jericho-html-2.3/jericho-html-2.3/doc/index.html");
        Source contentSource = new Source(sourceStream);

        System.out.print(contentSource.extractText());


    }

//     @Test
     public void testGetText2() throws Exception {

         String htmlContent = "1 Copyright &copy; 2007 Sohu.com&nbsp;Inc. ";
        Source contentSource = new Source(htmlContent);

        String plainText = "1 Copyright © 2007 Sohu.com Inc.";
        String extracted = contentSource.extractText();
        this.writeToFile(extracted);
        System.out.println("=======:" + htmlContent);
        System.out.println("=======:" + plainText);
        System.out.println("=======:" + extracted);
        System.out.println("=======:" + new String(extracted.getBytes("utf-8")));
        assertEquals(plainText,new String(extracted.getBytes("utf-8")));
        Source contentSource2 = new Source(new StringReader("Copyright &copy; 2007 Sohu.com Inc. "));

        System.out.println("=======:" + contentSource2.extractText());


    }

    private void writeToFile(String content) throws Exception {
        OutputStream os = new FileOutputStream("d:/miracle/projects/aaa.txt");
        os.write(content.getBytes("utf-8"));
    }
}
