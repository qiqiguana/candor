/*
 * Created on 16.09.2004
 */
package ch.bluepenguin.email.client.tests;

import org.springframework.mail.javamail.JavaMailReader;

/**
 * @author Christian
 *
 */
public class AOPTest extends SpringTestBase {
    private JavaMailReader reader;
    
    public void setUp() {
    	super.setUp();
        reader = (JavaMailReader) factory.getBean("mailReaderAdvice");
    }
    
    public void testAdvicing() {
    	Object returnVal = reader.getAllFolders();
    	System.out.println("return value: " +  returnVal.toString());
    }

}
